package com.vn.wecare

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.vn.wecare.feature.WecareApp
import com.vn.wecare.ui.theme.WecareTheme

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WecareTheme {
                // Create a navController with rememberNavController()
                WecareApp(navController = rememberNavController())
            }
        }
    }
}