package dev.sasikanth.colorpicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import androidx.ui.core.Modifier
import androidx.ui.core.drawShadow
import androidx.ui.core.setContent
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.unit.dp
import dev.sasikanth.colorpicker.ui.ColorPickerTheme

class MainActivity : AppCompatActivity() {

  private var selectedColor by mutableStateOf(Color.White)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ColorPickerTheme() {
        val colorPicker = "ColorPicker"
        val card = "Card"
        val constraintSet = ConstraintSet {
          tag(card).apply {
            right constrainTo parent.right
            left constrainTo parent.left
            top constrainTo parent.top
            bottom constrainTo tag(colorPicker).top
          }

          tag(colorPicker).apply {
            right constrainTo parent.right
            left constrainTo parent.left
            bottom constrainTo parent.bottom
          }
        }

        ConstraintLayout(constraintSet = constraintSet, modifier = Modifier.fillMaxHeight()) {
          Box(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = selectedColor,
            modifier = Modifier
              .tag(card)
              .drawShadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
              .width(200.dp)
              .height(200.dp)
          )

          Box(
            modifier = Modifier
              .tag(colorPicker)
              .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
          ) {
            ColorPicker(colorSelected = { color ->
              selectedColor = color
            })
          }
        }
      }
    }
  }
}
