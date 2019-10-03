package com.my.flicks.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.my.flicks.R
import com.my.flicks.database.MovieDatabase
import com.my.flicks.databinding.FragmentMoviesBinding
import com.my.flicks.network.MovieApiFilter
import kotlinx.android.synthetic.main.fragment_movies.*

class OverviewFragment : Fragment() {
    lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(value = activity).application
        val dataSource = MovieDatabase.getInstance(application).movieDatabaseDao()
        val viewModelFactory = OverviewViewModelFactory(application, dataSource)
        val binding = FragmentMoviesBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(OverviewViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        var adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(OverviewViewModel::class.java).displayPropertyDetails(it)
        })
        binding.photosGrid.adapter = adapter
        viewModel.navigateToSelectedMovie.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
        viewModel.start().observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.status().observe(this, Observer {
            progress_bar.visibility = if (it) View.VISIBLE else View.GONE
        })

        //        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

//        view?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//            ?.setupWithNavController(navHostFragment.navController)

//        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)

        // Custom navigation listener allows me to change the title
//        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->  toolbar?.title = destination.label}
//        navHostFragment.navController.addOnDestinationChangedListener{ _, destination ->
//            toolbar?.title = destination.label
//        }

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