package com.vn.wecare.feature.home.water

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CircularProgressAnimated

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WaterScreen(
    modifier: Modifier = Modifier, onNavigateUp: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            WaterAppBar(modifier = modifier) { onNavigateUp() }
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(smallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WaterOverView(modifier = modifier)
        }
    }
}

@Composable
fun WaterAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = tinyPadding)
            .background(color = MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = navigateUp) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = null
            )
        }
        Text(text = "Oct 12, 2022", style = MaterialTheme.typography.h4)
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_calendar),
                contentDescription = null
            )
        }
    }
}

@Composable
fun WaterOverView(
    modifier: Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = largePadding)
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = modifier.padding(bottom = normalPadding)
            ) {
                CircularProgressAnimated(
                    size = 200.dp,
                    currentValue = 75f,
                    indicatorThickness = 20.dp,
                    color = colorResource(id = R.color.Blue400)
                )
                Row {
                    Text(
                        text = "75%",
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = "/1000ml",
                        style = MaterialTheme.typography.h2,
                        color = colorResource(id = R.color.Blue400)
                    )
                }
            }
            Spacer(modifier = modifier.height(normalPadding))
            Row {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.Blue400)),
                    shape = CutCornerShape(smallRadius)
                ) {
                    Text(text = "Add")
                }
                Button(
                    onClick = {},
                    border = BorderStroke(1.dp, color = colorResource(id = R.color.Blue400)),
                    shape = CutCornerShape(smallRadius),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(id = R.color.Blue400))
                ) {
                    Text(text = "Remove")
                }
            }
        }
    }
}