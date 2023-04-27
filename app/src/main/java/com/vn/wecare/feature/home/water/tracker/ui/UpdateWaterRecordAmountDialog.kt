package com.vn.wecare.feature.home.water.tracker.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.feature.home.water.tracker.WaterViewModel
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.LightBlue
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun UpdateWaterRecordAmountDialog(
    modifier: Modifier,
    onCloseClick: () -> Unit,
    record: WaterRecordEntity,
    viewModel: WaterViewModel
) {

    val uiState = viewModel.uiState.collectAsState()

    Dialog(onDismissRequest = onCloseClick) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(LightBlue)
                .padding(horizontal = midPadding, vertical = mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Choose amount", style = MaterialTheme.typography.h3)
            WaterOpacityPicker(
                modifier = modifier.padding(vertical = halfMidPadding),
                numberOfPage = viewModel.getWaterOpacityNumber(),
                desiredAmount = viewModel.getWaterDrinkingAmount(),
                currentIndex = uiState.value.desiredDrinkingAmountPageIndex,
                onNextClick = viewModel::onNextAmountClick,
                onPreviousClick = viewModel::onPreviousAmountClick,
            )
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    modifier = modifier
                        .weight(1f)
                        .padding(end = smallPadding)
                        .height(40.dp),
                    border = BorderStroke(2.dp, Blue),
                    onClick = { onCloseClick() },
                    shape = RoundedCornerShape(mediumRadius),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Blue, backgroundColor = LightBlue
                    )
                ) {
                    Text(text = stringResource(id = R.string.close_dialog_title))
                }
                Button(
                    modifier = modifier
                        .weight(1f)
                        .padding(start = smallPadding)
                        .height(40.dp),
                    onClick = {
                        onCloseClick()
                        viewModel.updateRecordAmountWithId(
                            viewModel.getWaterDrinkingAmount(), record
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Blue, contentColor = MaterialTheme.colors.onPrimary
                    ),
                    shape = RoundedCornerShape(mediumRadius)
                ) {
                    Text(text = stringResource(id = R.string.okay_dialog_title))
                }
            }
        }
    }
}