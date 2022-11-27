package com.vn.wecare.feature.training.walking.widget

import android.os.Build
import android.widget.NumberPicker
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.vn.wecare.R

@Composable
fun NumberPickerSpinner(
    modifier: Modifier,
    max: Int,
    min: Int
) {
    AndroidView(
        modifier = modifier.width(54.dp),
        factory = { context ->
            NumberPicker(context).apply {
                setOnValueChangedListener { numberPicker, i, i2 -> }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    textSize = 66f
                    textColor = resources.getColor(R.color.Green500)
                }
                minValue = min
                maxValue = max
            }
        }
    )
}