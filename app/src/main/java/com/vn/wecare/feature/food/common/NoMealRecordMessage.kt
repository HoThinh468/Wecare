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
fun NoMealRecordMessage(modifier: Modifier) {
    Image(
        modifier = modifier.padding(top = xxExtraPadding, bottom = midPadding),
        painter = painterResource(id = R.drawable.img_empty_dish),
        contentDescription = null
    )
    Text(
        text = "No records yet, click +Add meal to add now!",
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center
    )
}