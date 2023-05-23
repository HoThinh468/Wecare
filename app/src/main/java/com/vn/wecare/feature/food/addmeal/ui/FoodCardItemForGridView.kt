package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FoodCardItemForGridView(
    modifier: Modifier,
    meal: MealByNutrients,
    viewModel: AddMealViewModel,
    mealTypeKey: MealTypeKey,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = normalPadding),
        shape = Shapes.medium,
        backgroundColor = MaterialTheme.colors.background,
        elevation = smallElevation,
        onClick = onCardClick
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = modifier
                        .width(maxWidth)
                        .height(maxWidth.times(0.8f))
                        .clip(shape = Shapes.medium),
                    model = meal.imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = halfMidPadding),
                text = meal.title,
                style = MaterialTheme.typography.h5,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = halfMidPadding, end = halfMidPadding, bottom = smallPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_fire_calo),
                        contentDescription = null,
                        tint = Red400
                    )
                    Text(
                        text = "${meal.calories} cal", style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onSecondary.copy(0.5f)
                        )
                    )
                }
                OutlinedButton(
                    onClick = {
                        viewModel.insertMealRecord(
                            dateTime = Calendar.getInstance(),
                            meal = meal,
                            mealTypeKey = mealTypeKey
                        )
                    },
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Icon(
                        Icons.Default.Add,
                        modifier = Modifier.size(16.dp),
                        contentDescription = "content description",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}