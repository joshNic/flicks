package com.example.flicks.movieDetails

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.paging.toLiveData
import com.example.flicks.R
import com.example.flicks.YoutubeTrailer.YoutubeFragment
import com.example.flicks.adapters.MovieCommentAdapter
import com.example.flicks.adapters.MovieGenreAdapter
import com.example.flicks.adapters.MovieTrailerAdapter
import com.example.flicks.database.MovieDatabase
import com.example.flicks.databinding.FragmentDetailBinding
import com.example.flicks.network.MovieApiFilter
import com.example.flicks.overview.OverviewFragmentDirections

class DetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(value = activity).application
        val binding = FragmentDetailBinding.inflate(inflater)

        val resultProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao()
        val viewModelFactory = DetailViewModelFactory(resultProperty, application,dataSource)

//        val viewModel: MovieDetailViewModel by lazy {
//            ViewModelProviders.of(
//                this, viewModelFactory).get(MovieDetailViewModel::class.java)
//        }

//        binding.viewModel = ViewModelProviders.of(
//            this, viewModelFactory).get(MovieDetailViewModel::class.java)



        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(MovieDetailViewModel::class.java)

//        ViewModelProviders.of(
//            this, viewModelFactory).get(MovieDetailViewModel::class.java).onStartTrack()
        binding.photGrid.adapter =
            MovieTrailerAdapter(MovieTrailerAdapter.OnClickListener {
                ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java).displayMovieTrailer(it)
            })

        ViewModelProviders.of(
            this, viewModelFactory).get(MovieDetailViewModel::class.java).dbData.toLiveData(50).observe(this, Observer {
                ViewModelProviders.of(
                    this, viewModelFactory).get(MovieDetailViewModel::class.java).getAllDatabaseMovies()
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
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.detail_screen_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        viewModel.updateFi
//            when (item.itemId) {
//                R.id.favourite_movies -> {
//                    Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show()
//                    Log.i("Clicked","Clicked Image")
//                }
////                R.id.top_rated_menu -> MovieApiFilter.TOP_RATED
////                R.id.now_playing_menu -> MovieApiFilter.NOW_PLAYING
//                else -> true
//            }
////        )
//        return true
//    }
}