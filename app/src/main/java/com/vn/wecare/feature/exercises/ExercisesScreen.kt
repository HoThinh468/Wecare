package com.vn.wecare.feature.exercises

import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Forum
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mapbox.maps.extension.style.expressions.dsl.generated.color
import com.mapbox.maps.extension.style.style
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Black900
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.CustomButton


@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    ExercisesScreen(
        userName = "",
        onNavigateToReport = {},
        onNavigateToEndurance = {},
        onNavigateToStrength ={},
        onNavigateToFlexibility = {},
        onNavigateToBalance = {},
        onNavigateToFullBody = {},
        onNavigateToChatBox = {}
    )
}

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    userName: String,
    onNavigateToReport: () -> Unit,
    onNavigateToEndurance: () -> Unit,
    onNavigateToStrength: () -> Unit,
    onNavigateToBalance: () -> Unit,
    onNavigateToFlexibility: () -> Unit,
    onNavigateToFullBody: () -> Unit,
    onNavigateToChatBox: () -> Unit,
    onNavigationBack: () -> Unit = {},
    auth: FirebaseAuth = Firebase.auth
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { onNavigationBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Text("")
                },
                backgroundColor = Color.White,
                actions =  {
                    IconButton(
                        modifier = modifier
                            .width(32.dp)
                            .height(40.dp)
                            .padding(top = normalPadding),
                        onClick = {
                            onNavigateToReport()
                        }
                    ) {
                        Icon(Icons.Default.Assessment, "", tint = Color.Black)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues),
        ) {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = modifier.height(mediumPadding))
                WorkoutType(
                    textColor = Green500, text = "30 days",
                    textExercise = "full body",
                    image1 = R.drawable.ellipse_2,
                    image2 = R.drawable.img_1,
                    onStart = { onNavigateToFullBody() }
                )
                Spacer(modifier = modifier.height(midPadding))
                WorkoutType(
                    textColor = Red400, text = "Strength",
                    image1 = R.drawable.rectangle_24,
                    image2 = R.drawable.img_2,
                    onStart = { onNavigateToStrength() }
                )
                Spacer(modifier = modifier.height(midPadding))
                WorkoutType(
                    textColor = Blue, text = "Balance",
                    image1 = R.drawable.polygon_1,
                    image2 = R.drawable.img_3,
                    onStart = { onNavigateToBalance() }
                )
                Spacer(modifier = modifier.height(midPadding))
                WorkoutType(
                    textColor = Yellow, text = "Flexibility",
                    image1 = R.drawable.star_1,
                    image2 = R.drawable.img_4,
                    onStart = { onNavigateToFlexibility() }
                )
                Spacer(modifier = modifier.height(mediumPadding))
                WorkoutType(
                    textColor = Green500, text = "Endurance",
                    image1 = R.drawable.ellipse_2,
                    image2 = R.drawable.img_1,
                    onStart = { onNavigateToEndurance() }
                )
                Spacer(modifier = modifier.height(80.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewType() {
    WorkoutType(
        textColor = Green500, text = "trung",
        image1 = R.drawable.ellipse_2,
        image2 = R.drawable.img_1
    )
}

@Composable
fun WorkoutType(
    modifier: Modifier = Modifier,
    textColor: Color,
    text: String,
    onStart: () -> Unit = {},
    image1: Int,
    image2: Int,
    textExercise: String = "exercises",
    extraCompose: @Composable() () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .height(140.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(normalPadding),
        elevation = smallPadding
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = modifier
                        .wrapContentSize()
                        .padding(start = midPadding, bottom = 20.dp)
                ) {
                    Text(
                        modifier = modifier
                            .padding(top = 10.dp, bottom = 26.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = OpenSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = textColor
                                )
                            ) {
                                append(text)
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = OpenSans,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Black900
                                )
                            ) {
                                append("\n$textExercise")
                            }
                        }
                    )
                    Box(
                        modifier = modifier
                            .height(30.dp)
                            .width(70.dp)
                    ) {
                        CustomButton(
                            text = "Start",
                            onClick = { onStart() },
                            padding = 0.dp,
                            backgroundColor = Black900,
                            textColor = Color.White,
                            modifier = modifier.clip(RoundedCornerShape(40.dp))
                        )
                    }
                }
                CustomImage(
                    modifier = modifier
                        .padding(top = 8.dp, end = 16.dp)
                        .width(150.dp)
                        .fillMaxHeight(),
                    image1 = image1,
                    image2 = image2
                )
            }
            extraCompose()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewImage() {
    CustomImage(
        image1 = R.drawable.ellipse_2,
        image2 = R.drawable.img_1
    )
}

@Composable
fun CustomImage(
    modifier: Modifier = Modifier,
    image1: Int,
    image2: Int,
) {
    Box(
        modifier = modifier
            .padding(top = 8.dp, end = 16.dp)
            .width(140.dp)
            .fillMaxHeight()
    ) {
        Image(
            modifier = modifier
                .width(140.dp)
                .fillMaxHeight(),
            painter = painterResource(id = image1),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Image(
            modifier = modifier
                .width(140.dp)
                .fillMaxHeight(),
            painter = painterResource(id = image2),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

    }
}