package com.mv.colorpicker

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.*

class ColorPickerView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), GestureDetector.OnGestureListener {
    private val gestureDetector = GestureDetector(context, this)

    private val dataSet by lazy { ArrayList<Paint>() }
    private val center = PointF()

    var indicatorPainter: IndicatorPainter = CircleIndicatorPainter()
    var colorChangedListener: OnColorChangedListener? = null

    /**
     * [angle] - is associated with allowed angle to be drawn for each item
     * Should be equal to 360 / dataSet.size
     */
    private val angle: Float
        get() = 360f / dataSet.size

    private var angleRotationOffset = 0f

    private var shadeRotationOffset = 0f
    private var shadeRotationMax = 0f
    private var shadeRotationMin = 0f
    private val shadesCount = 15

    /**
     * Taking into account that center point is shifter to the bottom
     * @see [radiusMax] - is distance from the center point to top of the view
     */
    private var radiusMax = 0f
    private var radiusMin = 0f

    /**
     *
     */
    private var oneSegmentHeight = 0f

    private val selectedPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    init {
        populate(*DefPalette().getPalette())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(w, w / 5 * 2)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radiusMax = h + w / 4f
        radiusMin = width / 2 * 0.95f
        oneSegmentHeight = (radiusMax - radiusMin) / 2
        shadeRotationOffset = oneSegmentHeight / 2
        shadeRotationMin = -oneSegmentHeight / 2
        shadeRotationMax = oneSegmentHeight * shadesCount - oneSegmentHeight / 2

        center.set(w / 2f, radiusMax)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun populate(vararg newDataSet: Paint) {
        dataSet.addAll(newDataSet)
    }

    override fun onDraw(canvas: Canvas) {
        dataSet.withIndex().forEach {
            drawArcSegments(canvas, it.index * angle, it.value)
        }
    }

    /**
     * Draws full arc:
     *  ......******......
     *  .......****.......
     *  ........**........
     *  ........*.........
     */
    private fun drawArcSegments(canvas: Canvas, startAngle: Float, fill: Paint) {
        for (shadeIndex in 0..shadesCount) {
            val rOut = radiusMax - shadeIndex * oneSegmentHeight + shadeRotationOffset + 1
            val rInn =
                radiusMax - shadeIndex * oneSegmentHeight + shadeRotationOffset - oneSegmentHeight
            val paint = Paint(fill).apply { color = color.darken(shadeIndex) }
            drawArcSegment(canvas, rInn, rOut, startAngle, angle, paint)
        }

        indicatorPainter.drawIndicator(
            canvas,
            selectedPaint,
            PointF(center.x, oneSegmentHeight),
            oneSegmentHeight / 2
        )
    }

    /**
     * Draws part of the arc:
     *  ..................
     *  .......****.......
     *  ..................
     *  ..................
     *  @see drawArcSegments
     */
    private fun drawArcSegment(
        canvas: Canvas,
        rInn: Float,
        rOut: Float,
        defStartAngle: Float,
        sweepAngle: Float,
        fill: Paint
    ) {
        //If this segment is out of bounds - return
        if (rInn > radiusMax || rOut < radiusMin) {
            return
        }

        val radiusOut = min(rOut, radiusMax)
        val radiusInn = max(rInn, radiusMin)

        //make it start from left + rotation
        val startAngle = defStartAngle + 270 - angle / 2 + angleRotationOffset
        val outerRect = RectF(
            center.x - radiusOut,
            center.y - radiusOut,
            center.x + radiusOut,
            center.y + radiusOut
        )
        val innerRect = RectF(
            center.x - radiusInn,
            center.y - radiusInn,
            center.x + radiusInn,
            center.y + radiusInn
        )
        val segmentPath = Path()
        val start: Double = Math.toRadians(startAngle.toDouble())
        segmentPath.moveTo(
            (center.x + radiusInn * cos(start)).toFloat() - 1,
            (center.y + radiusInn * sin(start)).toFloat()
        )
        segmentPath.lineTo(
            (center.x + radiusOut * cos(start)).toFloat() - 1,
            (center.y + radiusOut * sin(start)).toFloat()
        )
        segmentPath.arcTo(outerRect, startAngle, sweepAngle)
        val end: Double = Math.toRadians(startAngle + sweepAngle.toDouble())
        segmentPath.lineTo(
            (center.x + radiusInn * cos(end)).toFloat(),
            (center.y + radiusInn * sin(end)).toFloat()
        )
        segmentPath.arcTo(innerRect, startAngle + sweepAngle, -sweepAngle)

        //Check if it is selected sement
        if (PointF(center.x, oneSegmentHeight).isPointWithinPath(segmentPath)) {
            if (selectedPaint.color != fill.color) {
                selectedPaint.apply {
                    color = fill.color
                }
                colorChangedListener?.onColorChanged(fill.color)
            }

        }

        canvas.drawPath(segmentPath, fill)
    }

    //region gesture handling

    override fun onDown(p0: MotionEvent?) = true
    override fun onShowPress(p0: MotionEvent?) {}
    override fun onLongPress(p0: MotionEvent?) {}
    override fun onSingleTapUp(p0: MotionEvent?) = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (event.action == MotionEvent.ACTION_MOVE || isGestureWithinColorPicker(event.actionPoint()))
            gestureDetector.onTouchEvent(event)
        else
            false
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float) =
        true

    override fun onScroll(
        p0: MotionEvent?,
        p1: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (abs(distanceX) > abs(distanceY)) {
            onSwipeHorizontal(distanceX / 4)
        } else {
            onSwipeVertical(distanceY / 2)
        }

        return false
    }

    private fun onSwipeHorizontal(amount: Float) {
        angleRotationOffset -= amount
        invalidate()
    }

    private fun onSwipeVertical(amount: Float) {
        shadeRotationOffset += amount

        if (shadeRotationOffset > shadeRotationMax) {
            shadeRotationOffset = shadeRotationMax
        } else if (shadeRotationOffset < shadeRotationMin) {
            shadeRotationOffset = shadeRotationMin
        }

        invalidate()
    }
    //endregion gesture handling

    interface OnColorChangedListener {
        fun onColorChanged(@ColorInt color: Int)
    }

    interface IndicatorPainter {
        fun drawIndicator(
            canvas: Canvas,
            selectedColorPaint: Paint,
            center: PointF,
            recommendedRadius: Float
        )
    }

    private fun isGestureWithinColorPicker(point: PointF): Boolean {
        val possibleRadius = sqrt((center.x - point.x).pow(2) + (center.y - point.y).pow(2))
        return possibleRadius in radiusMin..radiusMax
    }
}