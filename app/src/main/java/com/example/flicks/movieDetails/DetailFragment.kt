package com.example.flicks.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.flicks.YoutubeTrailer.YoutubeFragment
import com.example.flicks.adapters.MovieCommentAdapter
import com.example.flicks.adapters.MovieGenreAdapter
import com.example.flicks.adapters.MovieTrailerAdapter
import com.example.flicks.databinding.FragmentDetailBinding
import com.example.flicks.overview.OverviewFragmentDirections

class DetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(value = activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val resultProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = DetailViewModelFactory(resultProperty, application)

//        val viewModel: MovieDetailViewModel by lazy {
//            ViewModelProviders.of(
//                this, viewModelFactory).get(MovieDetailViewModel::class.java)
//        }

//        binding.viewModel = ViewModelProviders.of(
//            this, viewModelFactory).get(MovieDetailViewModel::class.java)



        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(MovieDetailViewModel::class.java)


        binding.photGrid.adapter =
            MovieTrailerAdapter(MovieTrailerAdapter.OnClickListener {
                ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java).displayMovieTrailer(it)
            })

        ViewModelProviders.of(
            this, viewModelFactory).get(MovieDetailViewModel::class.java).navigateToSelectedTrailer.observe(this, Observer {
            if (null != it){
                this.findNavController().navigate(DetailFragmentDirections.actionDetailFragment(it))
                ViewModelProviders.of(
                    this, viewModelFactory).get(MovieDetailViewModel::class.java).displayMovieTrailerComplete()
            }
        })


//        viewModel.navigateToSelectedTrailer.observe(this, Observer {
//            if (null != it){
//                this.findNavController().navigate(DetailFragmentDirections.actionDetailFragment(it))
//                viewModel.displayMovieTrailerComplete()
//            }
//        })
        binding.genre.adapter =
            MovieGenreAdapter(MovieGenreAdapter.OnClickListener {
                ViewModelProviders.of(this, viewModelFactory)
            })
        binding.comments.adapter =
            MovieCommentAdapter(MovieCommentAdapter.OnClickListener {
                ViewModelProviders.of(this, viewModelFactory)
            })
        return binding.root
    }
}