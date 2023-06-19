package com.vn.wecare.feature.food.yourownmeal.addyourownmeal.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.search.ui.getBgColorFromMealTypeKey
import com.vn.wecare.feature.food.search.ui.getContentColorFromMealTypeKey
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun CategoryAndImageSection(
    modifier: Modifier,
    chosenMealTypeKey: MealTypeKey,
    onItemClick: (key: MealTypeKey) -> Unit,
    imageUri: Uri? = null,
    onImagePick: (uri: Uri?) -> Unit,
    isImageUploaded: Boolean
) {
    CategorySection(modifier, chosenMealTypeKey, onItemClick)
    Spacer(modifier = modifier.height(normalPadding))
    ChooseImage(
        modifier = modifier,
        imageUri = imageUri,
        onImagePick = onImagePick,
        isImageUploaded = isImageUploaded
    )
}

@Composable
private fun CategorySection(
    modifier: Modifier, chosenMealTypeKey: MealTypeKey, onClick: (key: MealTypeKey) -> Unit
) {
    Text("Category", style = MaterialTheme.typography.body1)
    Spacer(modifier = modifier.height(halfMidPadding))
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MealTypeItem(modifier = modifier,
            icon = Icons.Default.BreakfastDining,
            text = "Breakfast",
            bgColor = getBgColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.BREAKFAST
            ),
            contentColor = getContentColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.BREAKFAST
            ),
            onClick = {
                onClick(MealTypeKey.BREAKFAST)
            })
        Spacer(modifier = modifier.height(normalPadding))
        MealTypeItem(modifier = modifier,
            icon = Icons.Default.LunchDining,
            text = "Lunch",
            bgColor = getBgColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.LUNCH
            ),
            contentColor = getContentColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.LUNCH
            ),
            onClick = {
                onClick(MealTypeKey.LUNCH)
            })
    }
    Spacer(modifier = modifier.height(smallPadding))
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        MealTypeItem(
            modifier = modifier,
            icon = Icons.Default.BrunchDining,
            text = "Snack",
            bgColor = getBgColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.SNACK
            ),
            contentColor = getContentColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.SNACK
            ),
            onClick = {
                onClick(MealTypeKey.SNACK)
            },
        )
        Spacer(modifier = modifier.height(normalPadding))
        MealTypeItem(
            modifier = modifier,
            icon = Icons.Default.DinnerDining,
            text = "Dinner",
            bgColor = getBgColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.DINNER
            ),
            contentColor = getContentColorFromMealTypeKey(
                chosen = chosenMealTypeKey, key = MealTypeKey.DINNER
            ),
            onClick = {
                onClick(MealTypeKey.DINNER)
            },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ChooseImage(
    modifier: Modifier,
    imageUri: Uri? = null,
    onImagePick: (uri: Uri?) -> Unit,
    isImageUploaded: Boolean
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> onImagePick(uri) },
    )

    Text("Choose image", style = MaterialTheme.typography.body1)
    Spacer(modifier = modifier.height(smallPadding))
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = Shapes.large,
        onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
    ) {
        if (imageUri == null) {
            Image(
                modifier = modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.img_add_image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                modifier = modifier.fillMaxWidth(),
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
    if (!isImageUploaded) {
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = "*Please upload a picture of the meal",
            style = MaterialTheme.typography.caption.copy(color = Red400)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MealTypeItem(
    modifier: Modifier,
    icon: ImageVector,
    bgColor: Color = MaterialTheme.colors.onPrimary,
    contentColor: Color = MaterialTheme.colors.primary,
    text: String,
    onClick: () -> Unit = {}
) {
    Card(modifier = modifier
        .width(120.dp)
        .height(36.dp),
        shape = Shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        backgroundColor = bgColor,
        onClick = {
            onClick()
        }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = modifier.size(16.dp),
                imageVector = icon,
                contentDescription = null,
                tint = contentColor
            )
            Spacer(modifier = modifier.width(6.dp))
            Text(
                text = text, style = MaterialTheme.typography.button.copy(
                    contentColor
                )
            )
        }
    }
}