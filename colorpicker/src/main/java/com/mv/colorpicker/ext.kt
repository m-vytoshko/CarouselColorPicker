package com.mv.colorpicker

import android.graphics.*
import android.view.MotionEvent

fun Canvas.drawCircle(centerPoint: PointF, radius: Float, paint: Paint) =
    drawCircle(centerPoint.x, centerPoint.y, radius, paint)

fun PointF.isPointWithinPath(path: Path): Boolean {
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
    return boundsRegion.contains(x.toInt(), y.toInt())
}

fun MotionEvent.actionPoint(): PointF = PointF(x, y)