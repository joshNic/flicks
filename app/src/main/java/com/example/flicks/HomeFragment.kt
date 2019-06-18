package com.example.flicks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)

//        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
//
//        view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//            .setupWithNavController(navHostFragment.navController)

//        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
//
//        // Custom navigation listener allows me to change the title
//        navHostFragment.navController.addOnNavigatedListener { _, destination ->
//            toolbar.title = destination.label
//        }

        return rootView
    }
}