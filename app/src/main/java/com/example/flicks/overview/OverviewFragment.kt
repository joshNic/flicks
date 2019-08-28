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

class OverviewFragment : Fragment() {
    lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(value = activity).application
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao()
        val viewModelFactory = OverviewViewModelFactory(application,dataSource)
        val binding = FragmentMoviesBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        var adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener{
            ViewModelProviders.of(
                this, viewModelFactory).get(OverviewViewModel::class.java).displayPropertyDetails(it)
        })
        binding.photosGrid.adapter = adapter
        viewModel.navigateToSelectedMovie.observe(this, Observer {
            if (null != it){
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        viewModel.start().observe(this, Observer {
            adapter.submitList(it)
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.over_flow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.upcoming_menu -> MovieApiFilter.UPCOMING
                R.id.top_rated_menu -> MovieApiFilter.TOP_RATED
                R.id.now_playing_menu -> MovieApiFilter.NOW_PLAYING
                else -> MovieApiFilter.MOST_POPULAR
            }
        )
        return true
    }
}