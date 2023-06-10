package com.vn.wecare.feature.food.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vn.wecare.R
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.xxxExtraPadding

@Composable
fun MealRecordItem(
    modifier: Modifier,
    mealRecord: MealRecordModel,
    navigateToDetailScreen: () -> Unit,
    isEditEnable: Boolean,
    deleteMealRecord: (meal: MealRecordModel) -> Unit,
    onMinusClick: (meal: MealRecordModel) -> Unit,
    onAddClick: (meal: MealRecordModel) -> Unit
) {
    val openDeleteDialog = remember { mutableStateOf(false) }

    if (openDeleteDialog.value) {

        AlertDialog(onDismissRequest = {
            openDeleteDialog.value = false
        }, title = {
            Text(text = "Delete record")
        }, text = {
            Text("Are you sure you want to delete this record?")
        }, confirmButton = {
            TextButton(onClick = {
                openDeleteDialog.value = false
                deleteMealRecord(mealRecord)
            }) {
                Text("Delete")
            }
        }, dismissButton = {
            TextButton(onClick = {
                openDeleteDialog.value = false
            }) {
                Text("Close")
            }
        })
    }

    Column {
        Box(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = normalPadding)
            .clickable {
                navigateToDetailScreen()
            }) {
            Row(
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .padding(end = xxxExtraPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = modifier
                        .size(48.dp)
                        .clip(shape = Shapes.medium),
                    model = mealRecord.imgUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(modifier = modifier.padding(start = normalPadding)) {
                    Text(
                        modifier = modifier.width(200.dp),
                        text = mealRecord.title,
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = modifier.height(4.dp))
                    Text(
                        text = "${mealRecord.calories} cal",
                        style = MaterialTheme.typography.button.copy(
                            color = MaterialTheme.colors.onSecondary.copy(
                                0.5f
                            )
                        )
                    )
                }
            }
            Row(
                modifier = modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (mealRecord.quantity > 1) {
                        onMinusClick(mealRecord)
                    } else {
                        openDeleteDialog.value = true
                    }
                }, enabled = isEditEnable) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(text = "${mealRecord.quantity}", style = MaterialTheme.typography.body1)
                IconButton(onClick = {
                    onAddClick(mealRecord)
                }, enabled = isEditEnable) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
        Divider()
    }
}