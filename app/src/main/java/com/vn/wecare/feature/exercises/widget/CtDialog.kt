package com.vn.wecare.feature.exercises.widget

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.halfMidPadding

@Composable
@Preview
fun a() {
    CtDialog(
        title = "Warning",
        message = "You should try to finish your workout today.",
        onDismiss = {},
        onAgreeText = "Agree",
        onAgree = {}
    )
}


@Composable
fun CtDialog(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.warning,
    title: String,
    message: String? = null,
    onAgreeText: String? = null,
    onAgree: () -> Unit = {},
    onDismiss: () -> Unit,
    height: Dp = 280.dp
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier
            .height(height)
            .padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //.......................................................................
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
                .build()
            Image(
                modifier = modifier
                    .height(86.dp)
                    .padding(top = halfMidPadding),
                painter = rememberAsyncImagePainter(
                    icon, imageLoader
                ),
                contentDescription = "",
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = WeCareTypography.h3
                )
                if (message != null) {
                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        style = WeCareTypography.body1,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                    )
                }
            }
            //.......................................................................
            if (onAgreeText != null) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(Color.White)
                        .weight(3f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onAgree,
                        modifier = modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Green500)
                    ) {
                        Text(
                            onAgreeText,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    Button(
                        onClick = onDismiss,
                        modifier = modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Green500)
                    ) {
                        Text(
                            "Cancel",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            } else {
                Button(
                    onClick = onDismiss,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(start = 8.dp, bottom = 8.dp, end = 8.dp)
                ) {
                    Text(
                        "OK",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}