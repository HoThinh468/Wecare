package com.vn.wecare.feature.food.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddYourOwnMealSection(
    modifier: Modifier, navigateToAddYourOwnMealScreen: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.primary,
        onClick = navigateToAddYourOwnMealScreen
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = midPadding, vertical = normalPadding)
        ) {
            Column(modifier = modifier.weight(3f)) {
                Text(
                    "Add your own meal",
                    style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onPrimary)
                )
                Text(
                    "Fun and easy",
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onPrimary)
                )
                Spacer(modifier = modifier.height(normalPadding))
                Button(
                    modifier = modifier.height(40.dp),
                    onClick = { navigateToAddYourOwnMealScreen() },
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
                ) {
                    Text(text = "Why not?", style = MaterialTheme.typography.button)
                }
            }
            Image(
                modifier = modifier.weight(1f),
                painter = painterResource(id = R.drawable.img_add_your_own_meal),
                contentDescription = null
            )
        }
    }
}