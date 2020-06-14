package dev.sasikanth.colorpicker

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.core.clip
import androidx.ui.core.drawShadow
import androidx.ui.core.gesture.tapGestureFilter
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.drawBorder
import androidx.ui.foundation.gestures.DragDirection
import androidx.ui.foundation.gestures.draggable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.HorizontalGradient
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.height
import androidx.ui.layout.offset
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.Dp
import androidx.ui.unit.Px
import androidx.ui.unit.dp
import dev.sasikanth.colorpicker.ui.ColorPickerTheme
import dev.sasikanth.colorpicker.ui.colors
import android.graphics.Color as AndroidColor

/**
 * Gradient color picker
 *
 * @sample dev.sasikanth.colorpicker.ColorPickerPreview
 */
@Composable
fun ColorPicker(
  pickerHeight: Dp = 24.dp,
  colorSelected: (Color) -> Unit
) {
  WithConstraints {
    // Since we start from hue 0 setting the default selected color
    // to red, but need to find a better way of getting initial color.
    var selectedColor by mutableStateOf(Color.Red)
    var position by state { 0f }

    val squareSizePx = with(DensityAmbient.current) {
      pickerHeight.toPx()
    }
    val pickerWidthPx = with(DensityAmbient.current) {
      maxWidth.toPx()
    }
    val pickerMaxWidth = pickerWidthPx.value - squareSizePx.value

    val horizontalGradient = HorizontalGradient(
      colors = colors(),
      startX = Px.Zero.value,
      endX = pickerWidthPx.value
    )
    val roundedCornerShape = RoundedCornerShape(pickerHeight / 2)

    val drag = Modifier.draggable(
      dragDirection = DragDirection.Horizontal,
      onDragStarted = {
        position = it.x.value
      },
      onDragDeltaConsumptionRequested = { delta ->
        val old = position
        // Making sure the sum of delta and position is within the 0 and
        // max width of picker view
        position = (delta + position).coerceIn(0f, pickerMaxWidth)

        val hsvColor = getHsvColor(position, pickerMaxWidth)
        selectedColor = Color(hsvColor)
        colorSelected(selectedColor)

        position - old
      }
    )

    // Color Picker View
    Box(
      modifier = drag
        .tapGestureFilter {
          position = it.x.value

          val hsvColor = getHsvColor(position, pickerMaxWidth)
          selectedColor = Color(hsvColor)
          colorSelected(selectedColor)
        }
        .height(pickerHeight)
        .width(maxWidth)
        .clip(shape = roundedCornerShape)
        .drawBackground(brush = horizontalGradient, shape = roundedCornerShape)
    ) {

      val xOffset = with(DensityAmbient.current) { position.toDp() }
      val squareSize = with(DensityAmbient.current) { squareSizePx.toDp() }

      // Square box to show the preview of selected color
      Box(
        Modifier
          .offset(x = xOffset, y = 0.dp)
          .width(squareSize)
          .height(squareSize)
          .drawBorder(size = 2.dp, color = Color.White, shape = roundedCornerShape)
          .drawShadow(elevation = 2.dp, shape = roundedCornerShape),
        shape = roundedCornerShape,
        backgroundColor = selectedColor
      )

    }
  }
}

private fun getHsvColor(position: Float, maxWidth: Float): Int {
  // dividing the position of drag by max width of the picker view
  // and then we multiply it by 359 which is the final HUE value
  val hue = (position / maxWidth) * 359f
  return AndroidColor.HSVToColor(floatArrayOf(hue, 1f, 1f))
}

@Preview(
  name = "ColorPicker",
  widthDp = 320,
  heightDp = 48
)
@Composable
fun ColorPickerPreview() {
  ColorPickerTheme {
    Box(
      gravity = ContentGravity.Center,
      modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp)
    ) {
      ColorPicker {}
    }
  }
}
