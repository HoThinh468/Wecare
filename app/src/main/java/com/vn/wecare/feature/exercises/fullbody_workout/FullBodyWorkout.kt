package com.vn.wecare.feature.exercises.fullbody_workout

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.OfflineBolt
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.OfflineBolt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.exercises.widget.checkIsNull
import com.vn.wecare.feature.exercises.widget.slitWeek
import com.vn.wecare.feature.training.dashboard.widget.ProgressBar
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey100
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding

@Preview
@Composable
fun b() {
    FullBodyWorkout(
        onNavigateToDetail = { },
        onNavigateBack = {}
    )
}

@Composable
fun FullBodyWorkout(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: FullBodyViewModel = hiltViewModel()
) {

    Scaffold(
        backgroundColor = Grey100,
        modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = halfMidPadding,
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                title = { Text("Full Body Training") }
            )
        }

    ) { values ->
        Column(
            modifier = modifier
                .padding(values)
                .verticalScroll(rememberScrollState()),
        ) {

            when (val response = viewModel.getListDoneResponse) {
                is Response.Success -> {
                    val listDone = response.data?.listDone
                    WeekTitle(
                        number = 1,
                        isDone = !slitWeek(listDone, 1).contains(null)
                    )
                    WeekComponent(
                        onNavigateToDetail = { onNavigateToDetail(it) },
                        listDone = slitWeek(listDone, 1)
                    )
                    WeekTitle(
                        number = 2,
                        isDone = !slitWeek(listDone, 2).contains(null)
                    )
                    WeekComponent(
                        onNavigateToDetail = { onNavigateToDetail(it + 7) },
                        listDone = slitWeek(listDone, 2)
                    )
                    WeekTitle(
                        number = 3,
                        isDone = !slitWeek(listDone, 3).contains(null)
                    )
                    WeekComponent(
                        onNavigateToDetail = { onNavigateToDetail(it + 14) },
                        listDone = slitWeek(listDone, 3)
                    )
                    WeekTitle(
                        number = 4,
                        isDone = !slitWeek(listDone, 4).contains(null)
                    )
                    WeekComponent(
                        onNavigateToDetail = { onNavigateToDetail(it + 21) },
                        listDone = slitWeek(listDone, 4)
                    )
                }
                is Response.Error -> {
                    Log.e("get list done response", "error: ${response.e}")
                }
                else -> ProgressBar()
            }
        }
    }
}

@Composable
fun WeekTitle(
    number: Int,
    modifier: Modifier = Modifier,
    isDone: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(tinyPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (isDone) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                tint = Green500,
                contentDescription = ""
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.OfflineBolt,
                tint = Grey500,
                contentDescription = ""
            )
        }
        Text(
            text = "Week $number",
            modifier = modifier.padding(horizontal = smallPadding),
            style = WeCareTypography.caption
        )
    }
}

@Composable
fun WeekComponent(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Int) -> Unit,
    listDone: List<Int?>
) {
    Surface(
        modifier = modifier
            .padding(halfMidPadding)
//            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = halfMidPadding, vertical = smallPadding),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NumberCircleWithLogic(
                    number = 1,
                    isDone = checkIsNull(listDone[0]),
                    onNavigateToDetail = { onNavigateToDetail(1) }
                )
                Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "")
                NumberCircleWithLogic(
                    number = 2,
                    isDone = checkIsNull(listDone[1]),
                    onNavigateToDetail = { onNavigateToDetail(2) }
                )
                Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "")
                NumberCircleWithLogic(
                    number = 3,
                    isDone = checkIsNull(listDone[2]),
                    onNavigateToDetail = { onNavigateToDetail(3) }
                )
                Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "")
                NumberCircleWithLogic(
                    number = 4,
                    isDone = checkIsNull(listDone[3]),
                    onNavigateToDetail = { onNavigateToDetail(4) }
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = halfMidPadding, vertical = smallPadding),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NumberCircleWithLogic(
                    number = 5,
                    isDone = checkIsNull(listDone[4]),
                    onNavigateToDetail = { onNavigateToDetail(5) }
                )
                Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "")
                NumberCircleWithLogic(
                    number = 6,
                    isDone = checkIsNull(listDone[5]),
                    onNavigateToDetail = { onNavigateToDetail(6) }
                )
                Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "")
                NumberCircleWithLogic(
                    number = 7,
                    isDone = checkIsNull(listDone[6]),
                    onNavigateToDetail = { onNavigateToDetail(7) }
                )
                Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "")
                NumberCircleWithLogic(
                    number = 8,
                    isDone = !listDone.contains(null),
                    onNavigateToDetail = { onNavigateToDetail(0) }
                )
            }
        }
    }
}

@Preview
@Composable
fun a() {
    NumberCircle(
        color = Color.White,
        borderColor = Green500,
        number = 1,
        isDone = false,
        onNavigateToDetail = { }
    )
}

@Composable
fun NumberCircleWithLogic(
    isDone: Boolean,
    number: Int,
    onNavigateToDetail: () -> Unit
) {
    if (isDone)
        NumberCircle(
            number = number,
            color = Green500,
            isDone = isDone,
            onNavigateToDetail = { onNavigateToDetail() }
        )
    else
        NumberCircle(
            number = number,
            isDone = isDone,
            onNavigateToDetail = { onNavigateToDetail() }
        )
}

@Composable
fun NumberCircle(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    borderColor: Color = Green500,
    number: Int,
    isDone: Boolean,
    onNavigateToDetail: () -> Unit
) {
    if (number == 8 && isDone) {
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
                .size(42.dp),
            painter = rememberAsyncImagePainter(
                R.drawable.champion_cup,
                imageLoader
            ),
            contentDescription = "",
        )
    } else if (number == 8 && !isDone) {
        Image(
            modifier = modifier.size(42.dp),
            painter = painterResource(id = R.drawable.cup),
            contentDescription = "",
//            contentScale = ContentScale.FillBounds
        )
    } else {
        var stroke: Stroke =
            if (isDone) {
                Stroke(width = 5f)
            } else {
                Stroke(
                    width = 5f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                )
            }
        Box(
            modifier = modifier
                .size(42.dp, 42.dp)
                .drawBehind {
                    drawRoundRect(
                        colorFilter = ColorFilter.tint(
                            color,
                            BlendMode.ColorBurn
                        ),
                        color = borderColor,
                        style = stroke,
                        cornerRadius = CornerRadius(180.0f)
                    )
                }
                .clickable { onNavigateToDetail() },
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Box(
                    modifier = modifier
                        .clip(RoundedCornerShape(CornerSize(180.dp)))
                        .background(Green500)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
            } else {
                Text(text = number.toString(), style = WeCareTypography.body1)
            }
        }
    }
}