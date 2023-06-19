package com.vn.wecare.feature.food.addmeal.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.mealdetail.NutrientSubInfoItem
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun MealDetailInformationBottomSheet(
    modifier: Modifier = Modifier,
    mealByNutrients: MealByNutrients,
    onCloseBottomSheet: () -> Unit,
    onAddMealClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding, vertical = normalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .width(60.dp)
                .height(6.dp)
                .background(MaterialTheme.colors.primary)
                .clip(Shapes.large)
        )
        Spacer(modifier = modifier.height(mediumPadding))
        Row(
            modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = modifier
                    .weight(1.5f)
                    .height(152.dp)
                    .clip(Shapes.medium),
                model = mealByNutrients.imgUrl,
                contentDescription = mealByNutrients.title,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier
                    .weight(3f)
                    .height(IntrinsicSize.Max)
                    .padding(start = normalPadding), verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = mealByNutrients.title,
                    style = MaterialTheme.typography.h3,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = modifier.height(smallPadding))
                NutrientSubInfoItem(
                    modifier = modifier,
                    icon = Icons.Default.LocalFireDepartment,
                    color = Red400,
                    index = "${mealByNutrients.calories} cal"
                )
                Spacer(modifier = modifier.height(smallPadding))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    NutrientIndexItem(
                        modifier = modifier,
                        color = Red400,
                        title = "Protein",
                        index = mealByNutrients.protein
                    )
                    NutrientIndexItem(
                        modifier = modifier,
                        color = Yellow,
                        title = "Fat",
                        index = mealByNutrients.fat
                    )
                    NutrientIndexItem(
                        modifier = modifier,
                        color = Blue,
                        title = "Carbs",
                        index = mealByNutrients.carbs
                    )
                }
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = midPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                modifier = modifier
                    .weight(1f)
                    .padding(end = smallPadding)
                    .height(40.dp),
                onClick = { onCloseBottomSheet() },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = stringResource(id = R.string.close_dialog_title))
            }
            Button(
                modifier = modifier
                    .weight(1f)
                    .padding(start = smallPadding)
                    .height(40.dp),
                onClick = {
                    onCloseBottomSheet()
                    onAddMealClick()
                },
                shape = RoundedCornerShape(mediumRadius)
            ) {
                Text(text = "Add")
            }
        }
    }
}

@Composable
fun NutrientIndexItem(modifier: Modifier, color: Color, title: String, index: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = modifier
                .size(12.dp)
                .background(color = color, shape = CircleShape)
        )
        Column(modifier = modifier.padding(start = smallPadding)) {
            Text(text = title, style = MaterialTheme.typography.body2)
            Text(
                text = index, style = MaterialTheme.typography.caption
            )
        }
    }
}