package com.vn.wecare

import android.os.Bundle
<<<<<<< Updated upstream
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
=======
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
>>>>>>> Stashed changes


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

<<<<<<< Updated upstream
        //set up bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNav, navController)

        //set start destination

        //set start destination
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(R.id.homeFragment)

        //set visibility for bottom navigation
        navController.addOnDestinationChangedListener { _: NavController?, navDestination: NavDestination, _: Bundle? ->
            // show bottom navigation if current screen is in these 3 screens
            if (navDestination.id == R.id.homeFragment ||
                navDestination.id == R.id.accountFragment ||
                navDestination.id == R.id.trainingFragment
            ) {
                bottomNav.visibility = View.VISIBLE
            } // else hide it
            else {
                bottomNav.visibility = View.GONE
            }
        }
=======
        //setting nav host

        // Setup Navigation components for main activity
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        //set start destination
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(R.id.homeFragment)

        //set up bottom navigation
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
//        bottomNav.setupWithNavController(navController)


//        //set visibility for bottom navigation
//        navController.addOnDestinationChangedListener { _: NavController?, navDestination: NavDestination, _: Bundle? ->
//            // show bottom navigation if current screen is in these 3 screens
//            if (navDestination.id == R.id.homeFragment ||
//                navDestination.id == R.id.accountFragment ||
//                navDestination.id == R.id.trainingFragment
//            ) {
//                bottomNav.visibility = View.VISIBLE
//            } // else hide it
//            else {
//                bottomNav.visibility = View.GONE
//            }
//        }


>>>>>>> Stashed changes
    }
}
