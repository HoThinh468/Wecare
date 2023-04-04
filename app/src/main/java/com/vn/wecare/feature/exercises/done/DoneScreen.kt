package com.vn.wecare.feature.exercises.done

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.mapbox.maps.extension.style.style
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CustomButton

@Preview
@Composable
fun a() {
    DoneScreen()
}

@Composable
fun DoneScreen(
    modifier: Modifier = Modifier,
    onDone: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                .fillMaxSize()
                .weight(4f)
                .padding(vertical = halfMidPadding),
            painter = rememberAsyncImagePainter(
                R.drawable.congratulation,
                imageLoader
            ),
            contentDescription = "",
        )
        Text(
           "Congratulation!",
            style = WeCareTypography.h3
        )
        Text(
            "You did it!",
            style = WeCareTypography.h3
        )
        Box(
           modifier = modifier.weight(2f),
            contentAlignment = Alignment.Center
        ) {
            CustomButton(
                text = "DONE",
                onClick = {  },
                backgroundColor = Green500,
                textColor = Color.White,
                padding = 0.dp,
                modifier = modifier
                    .padding(midPadding)
                    .height(50.dp)
            )
        }
    }
}