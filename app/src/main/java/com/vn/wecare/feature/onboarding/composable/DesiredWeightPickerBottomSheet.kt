package com.vn.wecare.feature.onboarding.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.iconSize
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.WecareUserConstantValues.MAX_DIFFERENCE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_DIFFERENCE_WEIGHT

@Composable
fun DesiredWeightPickerBottomSheet(
    modifier: Modifier = Modifier,
    onDesiredWeightDifferencePickScrolled: (weight: Int) -> Unit,
    desiredWeightDifference: Int,
) {

    val openAnswerDialog = remember {
        mutableStateOf(false)
    }

    if (openAnswerDialog.value) {
        ResponseWeightDialog(modifier = modifier) {
            openAnswerDialog.value = false
        }
    }

    Column(
        modifier = modifier
            .padding(midPadding)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnboardingWeightPicker(modifier = modifier,
            weightPicked = desiredWeightDifference,
            onPickWeightScrolled = {
                onDesiredWeightDifferencePickScrolled(it)
            },
            customContent = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(text = "$desiredWeightDifference", style = MaterialTheme.typography.h1)
                        Text(text = "kg", style = MaterialTheme.typography.body2)
                    }
                }
            },
            minWeight = MIN_DIFFERENCE_WEIGHT,
            maxWeight = MAX_DIFFERENCE_WEIGHT
        )
        Spacer(modifier = modifier.height(normalPadding))
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                openAnswerDialog.value = true
            }) {
                Icon(
                    modifier = modifier.size(iconSize),
                    imageVector = Icons.Default.Help,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
            Text(text = "Why can I pick more weight?", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
private fun ResponseWeightDialog(modifier: Modifier, onDismissDialog: () -> Unit) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        Card(
            shape = Shapes.large
        ) {
            Column(
                modifier = modifier.padding(midPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hey pal, don't be hurry",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
                Image(
                    modifier = modifier
                        .size(150.dp)
                        .padding(vertical = midPadding),
                    painter = painterResource(id = R.drawable.img_friends),
                    contentDescription = null
                )
                Text(
                    text = "We beleive that gain/loose weight is a hard journey and that's why we should start slow but sure. It may take time but don't worry, no matter how tough it is, we will be there with you ^^",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(midPadding))
                Button(modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                    onClick = { onDismissDialog() }) {
                    Text("I UNDERSTAND", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}