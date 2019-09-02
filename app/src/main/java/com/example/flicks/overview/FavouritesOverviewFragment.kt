package com.example.flicks.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.flicks.database.MovieDatabase
import com.example.flicks.databinding.FragmentFavouritesBinding

class FavouritesOverviewFragment : Fragment() {
    lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(value = activity).application
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao()
        val viewModelFactory = OverviewViewModelFactory(application, dataSource)
        val binding = FragmentFavouritesBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(OverviewViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        viewModel.navigateToSelectedMovie.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(FavouritesOverviewFragmentDirections.actionFavouritesOverviewFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        viewModel.dbData.observe(this, Observer {
            if (null != it) {
                viewModel.getAllMovies()
            }
        })
        return binding.root
    }
}