package com.vn.wecare.feature.food.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.vn.wecare.R
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.xxExtraPadding

@Composable
fun FoodErrorMessage(
    modifier: Modifier, errorMessage: String = "Something wrong happened, please try again later!!"
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = modifier.padding(top = xxExtraPadding, bottom = midPadding),
            painter = painterResource(id = R.drawable.img_oops),
            contentDescription = null
        )
        Text(
            text = errorMessage, style = MaterialTheme.typography.body2, textAlign = TextAlign.Center
        )
    }
}