package com.chilik1020.hw42

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*
import kotlin.math.sqrt

class ColorMixerCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val RADIUS_OUTER = 400.0F
        const val RADIUS_INNER = 100.0F
    }

    private var centerX: Int = 0
    private var centerY: Int = 0

    private val rectOuter: RectF = RectF()
    private val paint: Paint
    private lateinit var listener: ColorMixerTouchListener

    private var colorArc0: Int = 0
    private var colorArc1: Int = 0
    private var colorArc2: Int = 0
    private var colorArc3: Int = 0
    private var colorCircle: Int = 0

    init {
        initAttrs(attrs)
        isClickable = true
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL_AND_STROKE
        shuffleAllColor()
    }

    fun setListener(touchListener: ColorMixerTouchListener) {
        listener = touchListener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerX = MeasureSpec.getSize(widthMeasureSpec) / 2
        centerY = MeasureSpec.getSize(heightMeasureSpec) / 2

        rectOuter.set(
            centerX - RADIUS_OUTER,
            centerY - RADIUS_OUTER,
            centerX + RADIUS_OUTER,
            centerY + RADIUS_OUTER
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = colorArc0
        canvas?.drawArc(rectOuter, 0f, 90f, true, paint)
        paint.color = colorArc1
        canvas?.drawArc(rectOuter, 90f, 90f, true, paint)
        paint.color = colorArc2
        canvas?.drawArc(rectOuter, 180f, 90f, true, paint)
        paint.color = colorArc3
        canvas?.drawArc(rectOuter, 270f, 90f, true, paint)

        paint.color = colorCircle
        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), RADIUS_INNER, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            checkWhereWasEventTouch(event.x, event.y)
        }
        return super.onTouchEvent(event)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorMixerCustomView)
        colorCircle = typedArray.getColor(R.styleable.ColorMixerCustomView_cmcv_centerColor, 0)
        typedArray.recycle()
    }

    private fun shuffleAllColor() {
        colorArc0 = getRandomColor()
        colorArc1 = getRandomColor()
        colorArc2 = getRandomColor()
        colorArc3 = getRandomColor()
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private fun checkWhereWasEventTouch(x: Float, y: Float) {
        if (checkIfPointInCircle(x, y, centerX.toFloat(), centerY.toFloat(), RADIUS_INNER)) {
            shuffleAllColor()
            listener.onTouchColorMixer(x,y,null)
            invalidate()
        } else if (checkIfPointInCircle(x, y, centerX.toFloat(), centerY.toFloat(), RADIUS_OUTER)) {
            var color: Int = 0
            val aboveCenter = y < centerY
            val leftToCenter = x < centerX
            if (aboveCenter && leftToCenter) {
                color = colorArc2
                colorArc2 = getRandomColor()
            } else if (aboveCenter && !leftToCenter) {
                color = colorArc3
                colorArc3 = getRandomColor()
            } else if (!aboveCenter && leftToCenter) {
                color = colorArc1
                colorArc1 = getRandomColor()
            } else if (!aboveCenter && !leftToCenter) {
                color = colorArc0
                colorArc0 = getRandomColor()
            }
            listener.onTouchColorMixer(x,y,color)
            invalidate()
        }
    }

    private fun checkIfPointInCircle(
        xP: Float,
        yP: Float,
        xC: Float,
        yC: Float,
        radius: Float
    ): Boolean {
        return radius > sqrt((xP - xC) * (xP - xC) + (yP - yC) * (yP - yC))
    }
}