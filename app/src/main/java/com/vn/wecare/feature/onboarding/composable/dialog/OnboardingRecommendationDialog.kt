package com.vn.wecare.feature.onboarding.composable.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding

@Composable
fun OnboardingRecommendationDialog(
    modifier: Modifier,
    onDismissDialog: () -> Unit,
    onWishToProcessClick: () -> Unit,
    title: String,
    message: String,
) {
    Dialog(onDismissRequest = { onDismissDialog() }) {
        Card(
            shape = Shapes.large
        ) {
            Column(
                modifier = modifier.padding(midPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.h5, textAlign = TextAlign.Center
                )
                Image(
                    modifier = modifier.padding(vertical = midPadding),
                    painter = painterResource(id = R.drawable.img_recommendation),
                    contentDescription = null
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(midPadding))
                Button(modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp), onClick = {
                    onDismissDialog()
                }) {
                    Text(
                        "Let me re-pick my goal", style = MaterialTheme.typography.button
                    )
                }
                TextButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = {
                        onDismissDialog()
                        onWishToProcessClick()
                    },
                ) {
                    Text(
                        "I understand and wish to process", style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}