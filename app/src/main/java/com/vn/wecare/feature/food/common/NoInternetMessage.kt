package com.vn.wecare.feature.food.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.vn.wecare.R
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.xxExtraPadding

@Composable
fun NoInternetMessage(modifier: Modifier) {
    Image(
        modifier = modifier.padding(top = xxExtraPadding, bottom = midPadding),
        painter = painterResource(id = R.drawable.img_no_internet),
        contentDescription = null
    )
    Text(
        text = "No internet connection, please connect to the internet to view records",
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center
    )
}