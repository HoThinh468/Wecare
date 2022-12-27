package com.vn.wecare

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.lifecycle.requireMapboxNavigation
import com.vn.wecare.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appContext = applicationContext
    }

    companion object {
        lateinit  var appContext: Context
    }
}