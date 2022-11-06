package com.vn.wecare.utils.common_composable

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun <T> Picker(
    list: List<T>,
    modifier: Modifier,
    showAmount: Int = 3,
    onValueChanged: (T) -> Unit = {}
) {
    val listCount by remember {
        mutableStateOf(list.size)
    }

    val correctValue by remember {
        if (list.size % 2 == 0) {
            mutableStateOf(1)
        } else mutableStateOf(0)
    }

    var dragStartedX by remember {
        mutableStateOf(0f)
    }

    var currentDragX by remember {
        mutableStateOf(0f)
    }

    var oldX by remember {
        mutableStateOf(0f)
    }

    Canvas(
        modifier = modifier.pointerInput
            (true) {
            detectDragGestures(
                onDragStart = {
                    dragStartedX = it.x
                },
                onDragEnd = {
                    val spacePerItem = size.width / showAmount
                    val rest = currentDragX % spacePerItem
                    val roundUp = abs(rest / spacePerItem).roundToInt() == 1

                    val newX = if (roundUp) {
                        if (rest < 0) {
                            currentDragX + abs(rest) - spacePerItem
                        } else {
                            currentDragX - rest + spacePerItem
                        }
                    } else {
                        if (rest < 0) {
                            currentDragX + abs(rest)
                        } else {
                            currentDragX - rest
                        }
                    }
                    currentDragX = newX.coerceIn(
                        minimumValue = -(listCount / 2f) * spacePerItem,
                        maximumValue = (listCount / 2f - correctValue) * spacePerItem
                    )
                    val index = (listCount / 2) + (currentDragX / spacePerItem).toInt()
                    onValueChanged(list[index])
                    oldX = currentDragX
                },
                onDrag = { change, _ ->
                    val changeX = change.position.x
                    val newX = oldX + (dragStartedX - changeX)
                    val spacePerItem = size.width / showAmount
                    currentDragX = newX.coerceIn(
                        minimumValue = -(listCount / 2f) * spacePerItem,
                        maximumValue = (listCount / 2f - correctValue) * spacePerItem
                    )
                    val index = (listCount / 2) + (currentDragX / spacePerItem).toInt()
                    onValueChanged(list[index])
                }
            )
        }
    ) {

        val top = 0f
        val bot = size.height

        drawContext.canvas.nativeCanvas.apply {
            drawRect(
                Rect(-2000,top.toInt(),size.width.toInt()+2000,bot.toInt()),
                Paint().apply {
                    color = Color.White.copy(alpha = 0.8f).toArgb()
                    setShadowLayer(
                        30f,
                        0f,
                        0f,
                        android.graphics.Color.argb(50,0,0,0)
                    )
                }
            )
        }

        val spaceForEachItem = size.width / showAmount
        for (i in 0 until listCount) {
            val currentX = i * spaceForEachItem - currentDragX -
                    ((listCount - 1 + correctValue - showAmount) / 2 * spaceForEachItem)

            val lineStart = Offset(
                x = currentX,
                y = 0f
            )

            val lineEnd = Offset(
                x = currentX,
                y = 45f
            )

            drawLine(
                color = Color.DarkGray,
                strokeWidth = 1.5.dp.toPx(),
                start = lineStart,
                end = lineEnd
            )

            drawContext.canvas.nativeCanvas.apply {
                val y = 45f + 5.dp.toPx() + 16.sp.toPx()

                drawText(
                    list[i].toString(),
                    currentX,
                    y,
                    Paint().apply {
                        textSize = 16.sp.toPx()
                        textAlign = Paint.Align.CENTER
                        isFakeBoldText = true
                    }
                )
            }

        }
    }
}