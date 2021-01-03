package com.mv.colorpicker

import android.graphics.*
import android.view.MotionEvent

fun Canvas.drawCircle(centerPoint: Point, radius: Float, paint: Paint) =
    drawCircle(centerPoint.x.toFloat(), centerPoint.y.toFloat(), radius, paint)

fun Point.isPointWithinPath(path: Path): Boolean {
    val rectF = RectF()
    path.computeBounds(rectF, true)
    val boundsRegion = Region().apply {
        setPath(
            path,
            Region(
                rectF.left.toInt(),
                rectF.top.toInt(),
                rectF.right.toInt(),
                rectF.bottom.toInt()
            )
        )
    }
    return boundsRegion.contains(x, y)
}

fun MotionEvent.actionPoint(): PointF = PointF(x, y)