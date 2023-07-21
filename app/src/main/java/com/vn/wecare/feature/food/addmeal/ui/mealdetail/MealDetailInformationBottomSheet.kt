package com.vn.wecare.feature.food.addmeal.ui.mealdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.feature.food.addmeal.ui.NutrientIndexItem
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.mealdetail.NutrientSubInfoItem
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import kotlin.math.abs

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MealDetailInformationBottomSheet(
    modifier: Modifier = Modifier,
    mealRecipe: MealRecipe,
    onCloseBottomSheet: () -> Unit,
    onAddMealClick: () -> Unit
) {

    Scaffold(modifier = modifier.fillMaxSize(), bottomBar = {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding),
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
    }, topBar = {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(Shapes.medium),
            model = mealRecipe.imgUrl,
            contentDescription = mealRecipe.title,
            contentScale = ContentScale.Crop
        )
    }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = midPadding,
                    bottom = xxxExtraPadding + midPadding,
                    end = midPadding,
                    top = midPadding
                )
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = mealRecipe.title,
                style = MaterialTheme.typography.h2,
                maxLines = 2,
                minLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = smallPadding, top = 4.dp)
            ) {
                Text(
                    text = "Serving: ${abs(mealRecipe.servings)}  -  Price: $${abs(mealRecipe.pricePerServing.toInt())}/serving",
                    style = MaterialTheme.typography.caption
                )
            }
            NutrientSubInfoItem(
                modifier = modifier,
                icon = Icons.Default.LocalFireDepartment,
                color = Red400,
                index = "${mealRecipe.calories} cal"
            )
            Spacer(modifier = modifier.height(smallPadding))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()
            ) {
                NutrientIndexItem(
                    modifier = modifier,
                    color = Red400,
                    title = "Protein",
                    index = mealRecipe.protein
                )
                NutrientIndexItem(
                    modifier = modifier, color = Yellow, title = "Fat", index = mealRecipe.fat
                )
                NutrientIndexItem(
                    modifier = modifier, color = Blue, title = "Carbs", index = mealRecipe.carbs
                )
            }
            Spacer(modifier.height(midPadding))
            RecipeInstruction(modifier = modifier, instructions = mealRecipe.instructions)
        }
    }
}