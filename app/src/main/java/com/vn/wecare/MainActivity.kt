package com.vn.wecare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.vn.wecare.feature.WecareApp
import com.vn.wecare.ui.theme.WecareTheme

class MainActivity : ComponentActivity() {
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