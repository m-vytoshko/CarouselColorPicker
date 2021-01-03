package com.mv.colorpicker

import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

interface IPalette {
    fun getPalette(): Array<Paint>
}

class DefPalette : IPalette {
    override fun getPalette(): Array<Paint> {
        return arrayOf(
            IosColor.Red1.color.toPalettePaint(),
            IosColor.Red2.color.toPalettePaint(),
            IosColor.Orange1.color.toPalettePaint(),
            IosColor.Orange2.color.toPalettePaint(),
            IosColor.Orange3.color.toPalettePaint(),
            IosColor.Orange4.color.toPalettePaint(),
            IosColor.Yellow1.color.toPalettePaint(),
            IosColor.Yellow2.color.toPalettePaint(),
            IosColor.Yellow3.color.toPalettePaint(),
            IosColor.Green1.color.toPalettePaint(),
            IosColor.Green2.color.toPalettePaint(),
            IosColor.Green3.color.toPalettePaint(),
            IosColor.Green4.color.toPalettePaint(),
            IosColor.Green5.color.toPalettePaint(),
            IosColor.Green6.color.toPalettePaint(),
            IosColor.Green7.color.toPalettePaint(),
            IosColor.Green8.color.toPalettePaint(),
            IosColor.Cyan1.color.toPalettePaint(),
            IosColor.Blue1.color.toPalettePaint(),
            IosColor.Blue2.color.toPalettePaint(),
            IosColor.Blue3.color.toPalettePaint(),
            IosColor.Purple1.color.toPalettePaint(),
            IosColor.Purple2.color.toPalettePaint(),
            IosColor.Purple3.color.toPalettePaint(),
            IosColor.Purple4.color.toPalettePaint(),
            //different?
            IosColor.Gray1.color.toPalettePaint(),
            //
            IosColor.Pink1.color.toPalettePaint(),
            IosColor.Pink2.color.toPalettePaint(),
            IosColor.Pink3.color.toPalettePaint(),
            IosColor.Pink4.color.toPalettePaint(),
            IosColor.Pink5.color.toPalettePaint(),
            IosColor.Pink6.color.toPalettePaint(),
            IosColor.Pink7.color.toPalettePaint(),
            IosColor.Pink8.color.toPalettePaint()
        )
    }
}

fun Int.toPalettePaint(): Paint {
    return Paint().apply {
        color = this@toPalettePaint
        isAntiAlias = true
    }
}

@ColorInt
fun Int.darken(index: Int) = ColorUtils.blendARGB(this, Color.BLACK, 0.05f * index)

fun Int.toHexString(): String = Integer.toHexString(this)

sealed class IosColor(
    r: Double,
    g: Double,
    b: Double,
    val color: Int = Color.parseColor(
        "#${
            (r * 255).toInt().toHexString()
        }${(g * 255).toInt().toHexString()}${(b * 255).toInt().toHexString()}"
    )
) {
    object Red1 : IosColor(1.0, .84, .84)
    object Red2 : IosColor(.99, .85, .82)
    object Orange1 : IosColor(.98, .87, .82)
    object Orange2 : IosColor(.99, .89, .82)
    object Orange3 : IosColor(1.0, .91, .82)
    object Orange4 : IosColor(1.0, .93, .82)
    object Yellow1 : IosColor(1.0, .96, .84)
    object Yellow2 : IosColor(1.0, .97, .84)
    object Yellow3 : IosColor(1.0, .98, .83)
    object Green1 : IosColor(.96, .98, .84)
    object Green2 : IosColor(.93, .94, .83)
    object Green3 : IosColor(.84, .9, .79)
    object Green4 : IosColor(.78, .84, .75)
    object Green5 : IosColor(.73, .8, .72)
    object Green6 : IosColor(.71, .76, .71)
    object Green7 : IosColor(.75, .83, .78)
    object Green8 : IosColor(.78, .87, .84)
    object Cyan1 : IosColor(.77, .89, .89)
    object Blue1 : IosColor(.76, .87, .88)
    object Blue2 : IosColor(.75, .83, .87)
    object Blue3 : IosColor(.75, .8, .85)
    object Purple1 : IosColor(.72, .78, .84)
    object Purple2 : IosColor(.71, .76, .84)
    object Purple3 : IosColor(.73, .74, .84)
    object Purple4 : IosColor(.72, .72, .83)
    object Gray1 : IosColor(.96, .97, .97)
    object Pink1 : IosColor(.75, .76, .85)
    object Pink2 : IosColor(.8, .78, .88)
    object Pink3 : IosColor(.85, .81, .91)
    object Pink4 : IosColor(.88, .83, .93)
    object Pink5 : IosColor(.92, .85, .95)
    object Pink6 : IosColor(.96, .85, .96)
    object Pink7 : IosColor(.98, .87, .95)
    object Pink8 : IosColor(.98, .85, .87)
}