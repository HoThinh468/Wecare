package com.vn.wecare.feature.home.bmi.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.core.ext.fromStringToGender
import com.vn.wecare.core.ext.toGender
import com.vn.wecare.feature.home.bmi.model.BMIHistoryEntity
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding

@Preview
@Composable
fun test() {
    UpdateBMIHistoryDialog(
        uiState = BMIHistoryEntity(1, 1, 1, true, 1),
        updateHistory = { BMIHistoryEntity(1, 1, 1, true, 1) }
    )
}

@Composable
fun UpdateBMIHistoryDialog(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    updateHistory: (BMIHistoryEntity) -> Unit,
    uiState: BMIHistoryEntity
) {
    var height by remember { mutableStateOf(TextFieldValue(uiState.height.toString())) }
    var weight by remember { mutableStateOf(TextFieldValue(uiState.weight.toString())) }
    var age by remember { mutableStateOf(TextFieldValue(uiState.age.toString())) }
    var gender by remember { mutableStateOf(TextFieldValue(uiState.gender.toGender())) }

    Dialog(onDismissRequest = onCloseClick) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = midPadding, vertical = mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter your history", style = MaterialTheme.typography.h3)
            Spacer(modifier = modifier.height(midPadding))
            OutlinedTextField(value = height,
                onValueChange = {
                    height = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                label = {
                    Text(text = "Height")
                },
                placeholder = {
                    Text(text = "Update your height")
                })
            OutlinedTextField(value = weight,
                onValueChange = {
                    weight = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                label = {
                    Text(text = "Weight")
                },
                placeholder = {
                    Text(text = "Update your weight")
                })
            OutlinedTextField(value = age,
                onValueChange = {
                    age = it
                },
                singleLine = true,
                label = {
                    Text(text = "Age")
                },
                readOnly = true
            )
            OutlinedTextField(value = gender,
                onValueChange = {
                    gender = it
                },
                singleLine = true,
                label = {
                    Text(text = "Gender")
                },
                readOnly = true
            )
            Spacer(modifier = modifier.height(midPadding))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    modifier = modifier
                        .weight(1f)
                        .padding(end = smallPadding)
                        .height(40.dp),
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    onClick = { onCloseClick() },
                    shape = RoundedCornerShape(mediumRadius),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.background
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
                        updateHistory(
                            BMIHistoryEntity(
                                height.text.toInt(),
                                weight.text.toInt(),
                                age.text.toInt(),
                                gender.text.fromStringToGender(),
                                System.currentTimeMillis()
                            )
                        )
                        onCloseClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
                    shape = RoundedCornerShape(mediumRadius)
                ) {
                    Text(text = stringResource(id = R.string.okay_dialog_title))
                }
            }
        }
    }
}