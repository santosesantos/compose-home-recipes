package com.raktufin.composehomerecipes.features.fab

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.raktufin.composehomerecipes.R
import kotlin.math.roundToInt


@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ExpandableFAB(
    onAddPrepareMode: () -> Unit,
    onAddIngredient: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFabClicked = remember { mutableStateOf(false) }
    val animationSpec: AnimationSpec<Float> = spring(
        stiffness = Spring.StiffnessLow,
        dampingRatio = Spring.DampingRatioLowBouncy
    )
    val density = LocalDensity.current.density
    val offsetDeleteY = animateFloatAsState(
        targetValue = if (isFabClicked.value) -250f * density / 2.75f else 0f,
        animationSpec = animationSpec, label = ""
    )
    val offsetAddNewY = animateFloatAsState(
        targetValue = if (isFabClicked.value) -500f * density / 2.75f else 0f,
        animationSpec = animationSpec, label = ""
    )
    val rotationAngle = animateFloatAsState(
        targetValue = if (isFabClicked.value) 90f else 0f,
        animationSpec = animationSpec, label = ""
    )

    val plusFabBackgroundColor = animateColorAsState(
        animationSpec = tween(durationMillis = 200),
        targetValue = if (isFabClicked.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
        label = ""
    )

    val plusFabIconColor = animateColorAsState(
        animationSpec = tween(durationMillis = 200),
        targetValue = if (isFabClicked.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        label = ""
    )
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = isFabClicked.value
    ) {
        Box(modifier = Modifier
            .pointerInteropFilter {
                isFabClicked.value = !isFabClicked.value
                false
            }
            .alpha(0.8f)
            .background(Color.Black)
            .fillMaxSize())
    }

    Box(modifier = modifier) {

        FABItem(
            offset = IntOffset(19, offsetAddNewY.value.roundToInt()),
            onClick = {
                isFabClicked.value = !isFabClicked.value
                onAddIngredient()
            },
            size = 33.dp,
            resId = R.drawable.ic_ingredient,
            backgroundColor = White,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        FABItem(
            offset = IntOffset(19, offsetDeleteY.value.roundToInt()),
            onClick = {
                isFabClicked.value = !isFabClicked.value
                onAddPrepareMode()
            },
            size = 33.dp,
            resId = R.drawable.ic_prepare_mode,
            backgroundColor = White,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        FABItem(
            modifier = Modifier.rotate(rotationAngle.value),
            onClick = {
                isFabClicked.value = !isFabClicked.value
            },
            size = 25.dp,
            imgVector = Icons.Filled.Add,
            backgroundColor = plusFabBackgroundColor.value,
            colorFilter = ColorFilter.tint(plusFabIconColor.value)
        )
    }
}

@Composable
fun FABItem(
    modifier: Modifier = Modifier,
    offset: IntOffset = IntOffset(0, 0),
    onClick: () -> Unit,
    size: Dp,
    @DrawableRes resId: Int = 0,
    imgVector: ImageVector? = null,
    backgroundColor: Color,
    colorFilter : ColorFilter,
) {
    Box(
        modifier = modifier
            .offset {
                offset
            }
            .size((if (imgVector != null) 68.dp else 54.dp))
            .clip(CircleShape)
            .noRippleClickable {
                onClick()
            }
            .drawColoredShadow(
                alpha = 0.15f,
                shadowRadius = 5.dp,
                color = MaterialTheme.colorScheme.primary,
                borderRadius = 34.dp
            )
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            colorFilter = colorFilter,
            modifier = Modifier.size(size),
            painter = (if (imgVector != null) rememberVectorPainter(image = imgVector) else painterResource(resId)),
            contentDescription = "FAB"
        )
    }
}