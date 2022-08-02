package app.expense.tracker.ui.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

class ColorGenerator private constructor(private val mColors: List<Color>) {
    companion object {
        var MATERIAL: ColorGenerator = create(
            listOf(
                Color.White,
                Color.Blue,
                Color.Cyan,
                Color.Green,
                Color.Red,
                Color.Magenta,
                Color.Yellow,
                Color.LightGray
            )
        )

        private fun create(colorList: List<Color>): ColorGenerator {
            return ColorGenerator(colorList)
        }
    }


    fun getColor(key: Any): Color {
        return mColors[abs(key.hashCode()) % mColors.size]
    }

}