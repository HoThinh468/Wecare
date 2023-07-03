package com.vn.wecare.feature.home.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardHomeCard(
    modifier: Modifier, onCardClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onCardClick
    ) {
        Column(
            modifier = modifier
                .heightIn()
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Dashboard",
                style = MaterialTheme.typography.h5,
            )
        }
    }
}