package com.tonholo.photos.core.ui.foundation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.theme.Theme
import kotlin.math.abs

/**
 * Adds a visual scroll indicator to a [LazyColumn].
 *
 * @param state The state of the [LazyColumn].
 * @param width The width of the scroll indicator.
 * @param background The background color of the scroll indicator.
 * @param thumbColor The color of the scroll thumb.
 * @param thumbPadding The padding around the scroll thumb.
 * @param thumbRoundCorner The corner radius of the scroll thumb.
 * @return A [Modifier] with the visual scroll indicator applied.
 */
@Composable
fun Modifier.visualScroll(
    state: LazyListState,
    width: Dp = 8.dp,
    background: Color = Theme.colorScheme.surfaceContainer.copy(alpha = 0.8f),
    thumbColor: Color = Theme.colorScheme.onSurface,
    thumbPadding: PaddingValues = PaddingValues(4.dp),
    thumbRoundCorner: Dp = 16.dp,
): Modifier {
    val density = LocalDensity.current
    val totalCount by remember(state) {
        derivedStateOf {
            state.layoutInfo.totalItemsCount
        }
    }
    val thumbSize by remember(state) {
        derivedStateOf {
            Size(
                width = with(density) { width.toPx() },
                height = state.layoutInfo.viewportEndOffset / totalCount.toFloat(),
            )
        }
    }
    val layoutDirection = LocalLayoutDirection.current
    val horizontalPadding = remember(layoutDirection) {
        thumbPadding.calculateStartPadding(layoutDirection) +
                thumbPadding.calculateEndPadding(layoutDirection)
    }

    val measuredItemsSize by remember(totalCount) {
        mutableStateOf(
            IntArray(size = totalCount) { index ->
                state.layoutInfo.visibleItemsInfo.map { it.size }.getOrElse(index) { -1 }
            }
        )
    }

    LaunchedEffect(state.layoutInfo.visibleItemsInfo) {
        if (measuredItemsSize.isNotEmpty()) {
            val firstVisibleItemIndex = state.firstVisibleItemIndex
            state.layoutInfo.visibleItemsInfo.forEachIndexed { index, item ->
                val targetIndex = (firstVisibleItemIndex + index).coerceAtMost(totalCount - 1)
                if (measuredItemsSize[targetIndex] == -1) {
                    measuredItemsSize[targetIndex] = item.size
                }
            }
        }
    }

    val thumbYPosition by remember(
        measuredItemsSize,
        state.canScrollBackward,
        state.canScrollForward
    ) {
        derivedStateOf {
            when {
                measuredItemsSize.isEmpty() || !state.canScrollBackward ->
                    calculateThumbYStartPosition(density, thumbPadding)

                !state.canScrollForward ->
                    calculateThumbYEndPosition(state, thumbSize, density, thumbPadding)

                else -> calculateThumbYPosition(state, thumbSize, measuredItemsSize)
            }
        }
    }

    val thumbYAnimatedPosition by animateFloatAsState(targetValue = thumbYPosition)

    return this then Modifier.drawWithContent {
        val startX = size.width - width.toPx() - horizontalPadding.toPx()
        drawContent()
        drawRect(
            color = background,
            topLeft = Offset(startX, 0f),
            size = Size(width = width.toPx() + horizontalPadding.toPx(), height = size.height),
        )
        drawRoundRect(
            color = thumbColor,
            topLeft = Offset(
                x = startX + thumbPadding.calculateStartPadding(layoutDirection).toPx(),
                y = thumbYAnimatedPosition,
            ),
            size = thumbSize,
            cornerRadius = CornerRadius(x = thumbRoundCorner.toPx(), y = thumbRoundCorner.toPx()),
        )
    }
}

private fun calculateThumbYStartPosition(
    density: Density,
    thumbPadding: PaddingValues,
): Float = with(density) {
    thumbPadding.calculateTopPadding().toPx()
}

private fun calculateThumbYPosition(
    state: LazyListState,
    thumbSize: Size,
    measuredItemsSize: IntArray,
): Float {
    val initialYPosition = state.firstVisibleItemIndex * thumbSize.height
    val itemScrollPercentage =
        abs(state.firstVisibleItemScrollOffset) / measuredItemsSize[
            state.firstVisibleItemIndex].toFloat()
    val relativeYPosition = thumbSize.height * itemScrollPercentage
    return initialYPosition + relativeYPosition
}

private fun calculateThumbYEndPosition(
    state: LazyListState,
    thumbSize: Size,
    density: Density,
    thumbPadding: PaddingValues,
): Float {
    val viewportEndOffset = state.layoutInfo.viewportEndOffset.toFloat()
    val thumbSizeEndOffset = thumbSize.height / 2 + with(density) {
        (thumbPadding.calculateTopPadding() + thumbPadding.calculateBottomPadding())
            .toPx() * 2
    }
    return viewportEndOffset - thumbSizeEndOffset
}
