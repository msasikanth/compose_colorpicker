package dev.sasikanth.colorpicker.ui

import androidx.compose.Composable
import androidx.ui.material.MaterialTheme
import androidx.ui.material.lightColorPalette

@Composable
fun ColorPickerTheme(content: @Composable() () -> Unit) {
  val colors = lightColorPalette(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200
  )

  MaterialTheme(
    colors = colors,
    typography = typography,
    shapes = shapes,
    content = content
  )
}