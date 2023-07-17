package com.vn.wecare.feature.food.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JustForYouSection(
    modifier: Modifier, navigateToAddYourOwnMealScreen: () -> Unit
) {
    Text("Just for you", style = MaterialTheme.typography.h4)
    Spacer(modifier = modifier.height(halfMidPadding))
    Row(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = modifier.weight(1f),
            shape = Shapes.large,
            backgroundColor = MaterialTheme.colors.primary,
            onClick = navigateToAddYourOwnMealScreen
        ) {
            Column(
                modifier = modifier.fillMaxWidth().padding(horizontal = midPadding, vertical = normalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Add your own meal",
                    modifier = modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary)
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                Image(
                    modifier = modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.img_cooking),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = modifier.width(normalPadding))
        Card(modifier = modifier.weight(1f),
            shape = Shapes.large,
            backgroundColor = Blue,
            onClick = {}) {
            Column(
                modifier = modifier.fillMaxWidth().padding(horizontal = midPadding, vertical = normalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Weekly meal plan",
                    modifier = modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary)
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                Image(
                    modifier = modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.img_chief),
                    contentDescription = null
                )
            }
        }
    }
}