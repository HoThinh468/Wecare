package com.vn.wecare.feature.food.mealdetail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.ui.mealdetail.MealDetailInformationBottomSheet
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxExtraPadding
import com.vn.wecare.utils.common_composable.LoadingDialog
import com.vn.wecare.utils.common_composable.WecareAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MealDetailScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    mealType: MealTypeKey,
    record: MealRecordModel,
    viewModel: MealDetailViewModel
) {

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    val uiState = viewModel.uiState.collectAsState().value

    uiState.getMealResponse.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                coroutineScope.launch {
                    sheetState.show()
                }
                viewModel.resetGetMealResponse()
            }

            is Response.Error -> {
                Toast.makeText(
                    LocalContext.current, "Error! Cannot show meal detail!", Toast.LENGTH_SHORT
                ).show()
                viewModel.resetGetMealResponse()
            }

            else -> { /* do nothing */
            }
        }
    }

    ModalBottomSheetLayout(sheetContent = {
        MealDetailInformationBottomSheet(mealRecipe = uiState.mealRecipe, onCloseBottomSheet = {
            coroutineScope.launch { sheetState.hide() }
        }, onAddMealClick = {}, showAddMealButton = false)
    }, sheetState = sheetState) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                WecareAppBar(
                    modifier = modifier,
                    title = "",
                    onLeadingIconPress = navigateUp,
                    trailingIconRes = R.drawable.ic_info,
                    onTrailingIconPress = {
                        viewModel.getMealRecipeWithId(record.id)
                    },
                )
            },
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = midPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                MealOverview(modifier = modifier, mealType = mealType, record = record)
                Spacer(modifier = modifier.height(xxExtraPadding))
                NutrientsIndexInformation(modifier = modifier, record = record)
                Spacer(modifier = modifier.height(xxExtraPadding))
            }
        }
    }
}

@Composable
private fun MealOverview(
    modifier: Modifier, mealType: MealTypeKey, record: MealRecordModel
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = mediumPadding),
        text = record.title,
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = midPadding, bottom = xxExtraPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NutrientSubInfoItem(
            modifier = modifier,
            icon = Icons.Default.LocalFireDepartment,
            color = Red400,
            index = "${record.calories} cal"
        )
        Spacer(modifier = modifier.width(normalPadding))
        NutrientSubInfoItem(
            modifier = modifier,
            icon = Icons.Default.BreakfastDining,
            color = MaterialTheme.colors.primary,
            index = mealType.value
        )
    }
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = record.imgUrl,
            contentDescription = null,
            modifier = modifier
                .width(this.maxWidth)
                .height(this.maxWidth * 0.9f)
                .clip(Shapes.large),
            contentScale = ContentScale.Crop,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NutrientSubInfoItem(
    modifier: Modifier,
    icon: ImageVector,
    color: Color = MaterialTheme.colors.primary,
    bgColor: Color = MaterialTheme.colors.background,
    contentColor: Color? = null,
    index: String,
    onClick: () -> Unit = {}
) {
    Card(shape = Shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        backgroundColor = bgColor,
        onClick = {
            onClick()
        }) {
        Row(
            modifier = modifier.padding(horizontal = 12.dp, vertical = smallPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier.size(16.dp),
                imageVector = icon,
                contentDescription = null,
                tint = contentColor ?: color
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = index, style = MaterialTheme.typography.button.copy(
                    contentColor ?: MaterialTheme.colors.onSecondary.copy(0.5f)
                )
            )
        }
    }
}

@Composable
private fun NutrientsIndexInformation(modifier: Modifier, record: MealRecordModel) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NutrientIndexItem(
            modifier = modifier, title = "PROTEIN", index = record.protein, color = Red400
        )
        NutrientIndexItem(
            modifier = modifier, title = "FAT", index = record.fat, color = Yellow
        )
        NutrientIndexItem(
            modifier = modifier, title = "CARBS", index = record.carbs, color = Blue
        )
    }
}

@Composable
fun NutrientIndexItem(
    modifier: Modifier, title: String, index: String, color: Color
) {
    Box(
        modifier = modifier
            .size(84.dp)
            .background(color = color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onPrimary)
            )
            Text(
                text = index,
                style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onPrimary)
            )
        }
    }
}