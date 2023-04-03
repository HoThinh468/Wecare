package com.vn.wecare.feature.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier
) {
    Scaffold {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(176.dp)
                    .height(100.dp)
                    .padding(top = 32.dp),
                painter = painterResource(R.drawable.logo2),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            
        }
    }
}