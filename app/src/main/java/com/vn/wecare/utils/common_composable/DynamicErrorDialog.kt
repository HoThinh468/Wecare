package com.vn.wecare.utils.common_composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding

@Composable
fun DynamicErrorDialog(
    modifier: Modifier = Modifier,
    errorMessage: String = "Something wrong happens, please try again later",
    isShowing: Boolean,
    onDismissRequest: () -> Unit,
    @DrawableRes imgRes: Int = R.drawable.img_oops,
    onButtonClick: () -> Unit = onDismissRequest,
    buttonText: String = "I understand"
) {
    if (isShowing) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(mediumPadding)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(midPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = modifier.size(100.dp),
                        painter = painterResource(id = imgRes),
                        contentDescription = null
                    )
                    Spacer(modifier = modifier.height(normalPadding))
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.height(normalPadding))
                    Button(onClick = { onButtonClick() }, shape = Shapes.large) {
                        Text(text = buttonText, style = MaterialTheme.typography.button)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DynamicErrorDialogPreview() {
    DynamicErrorDialog(isShowing = true, onDismissRequest = { /*TODO*/ })
}