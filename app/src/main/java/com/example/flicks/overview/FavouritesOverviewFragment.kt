package com.example.flicks.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.flicks.R
import com.example.flicks.database.MovieDatabase
import com.example.flicks.databinding.FragmentMoviesBinding
import com.example.flicks.network.MovieApiFilter

class FavouritesOverviewFragment : Fragment() {
    lateinit var viewModel: OverviewViewModel

//    private val viewModel: OverviewViewModel by lazy {
//        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(value = activity).application
//        val binding = FragmentDetailBinding.inflate(inflater)

//        val resultProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao()
        val viewModelFactory = OverviewViewModelFactory(application,dataSource)
        val binding = FragmentMoviesBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener{
            viewModel.displayPropertyDetails(it)
        })
        viewModel.navigateToSelectedMovie.observe(this, Observer {
            if (null != it){
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        viewModel.dbData.observe(this, Observer {
            if (null != it){
                viewModel.getAllMovies()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
}