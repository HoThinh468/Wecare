package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vn.wecare.R
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun PageNotFound(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = modifier.padding(vertical = normalPadding, horizontal = mediumPadding),
            painter = painterResource(id = R.drawable.ill_page_not_found),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.page_not_found_message),
            style = MaterialTheme.typography.body1
        )
    }
}