package com.mv.colorpicker

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point

open class CircleIndicatorPainter : ColorPickerView.IndicatorPainter {
    override fun drawIndicator(
        canvas: Canvas,
        selectedColorPaint: Paint,
        center: Point,
        recommendedRadius: Float
    ) {
        val borderPaint = Paint().apply {
            color = Color.WHITE
            strokeWidth = 2f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }

        canvas.drawCircle(
            Point(center.x, center.y),
            recommendedRadius,
            selectedColorPaint
        )
        canvas.drawCircle(
            Point(center.x, center.y),
            recommendedRadius,
            borderPaint
        )
    }
}