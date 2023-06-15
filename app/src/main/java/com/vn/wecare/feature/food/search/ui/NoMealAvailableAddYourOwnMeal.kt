package com.vn.wecare.feature.food.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding

@Composable
fun NoMealAvailableAddYourOwnMeal(
    modifier: Modifier, moveToAddYourOwnMealScreen: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.size(150.dp),
            painter = painterResource(id = R.drawable.img_no_search_result),
            contentDescription = null
        )
        Spacer(modifier = modifier.height(midPadding))
        Text(
            text = "No meal available with the given keyword. Try another keyword or \nAdd your own meal",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(midPadding))
        Button(
            modifier = modifier
                .padding(xxxExtraPadding)
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                moveToAddYourOwnMealScreen()
            },
            shape = Shapes.large,
        ) {
            Icon(
                modifier = modifier
                    .padding(end = smallPadding)
                    .size(16.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
            Spacer(modifier = modifier.height(midPadding))
            Text(text = "Add meal", style = MaterialTheme.typography.button)
        }
    }
}