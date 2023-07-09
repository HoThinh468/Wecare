package com.vn.wecare.feature.home.bmi.ui

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.feature.home.bmi.viewmodel.BMIViewModel
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun UpdateWeightDialog(
    modifier: Modifier, onCloseClick: () -> Unit, viewModel: BMIViewModel
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Dialog(onDismissRequest = onCloseClick) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = midPadding, vertical = mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter your weight", style = MaterialTheme.typography.h3)
            Spacer(modifier = modifier.height(midPadding))
            OutlinedTextField(value = text,
                onValueChange = {
                    text = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                label = {
                    Text(text = "Weight")
                },
                placeholder = {
                    Text(text = "Enter your weight")
                }
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
                        viewModel.updateUserWeight(text.text, true)
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