package com.vn.wecare.feature.training.widget

import android.os.Build
import android.widget.NumberPicker
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.vn.wecare.R

@Composable
fun NumberPickerSpinner(
    modifier: Modifier,
    max: Int = 10,
    min: Int = 0,
    pickerTextSize: Float = 66f,
    displayedList: Array<String>? = null,
    onValChange: (Int) -> Unit = {},
): Int {
    var newValue by remember { mutableStateOf(0) }
    AndroidView(modifier = modifier.width(54.dp), factory = { context ->
        NumberPicker(context).apply {
            setOnValueChangedListener { _, _, i ->
                newValue = i
                onValChange(i)
            }
            wrapSelectorWheel = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                textSize = pickerTextSize
                textColor = resources.getColor(R.color.Green500)
            }
            if (displayedList == null) {
                minValue = min
                maxValue = max
            } else {
                displayedValues = displayedList
            }
        }
    })
    return newValue
}