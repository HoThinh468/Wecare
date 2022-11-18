package com.vn.wecare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.vn.wecare.feature.training.walking.ui.WalkingFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //setting nav host
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        //set start destination
//        val inflater = navController.navInflater
//        val graph = inflater.inflate(R.navigation.nav_graph)
//        graph.setStartDestination(R.id.homeFragment)

        //set up map sdk
        val fragment = WalkingFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
