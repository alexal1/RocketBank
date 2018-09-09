package com.rocketbank.rockettest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.truncate

/**
 * Custom widget that draws given Image.
 */
class ImageView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    var onPixelTouch: (pixel: Pixel) -> Unit = {}
    private var currentImage: Image? = null
    private var pixelWidth = 0f
    private var pixelHeight = 0f
    private var touchDown: Pixel? = null
    private val colorWhite = ContextCompat.getColor(context, R.color.white)
    private val colorBlack = ContextCompat.getColor(context, R.color.black)
    private val colorRed = ContextCompat.getColor(context, R.color.red)
    private val paintWhite = Paint().apply { color = colorWhite }
    private val paintBlack = Paint().apply { color = colorBlack }
    private val paintReplacement = Paint().apply { color = colorRed }

    fun drawImage(image: Image) {
        currentImage = image
        invalidate()
    }

    private fun drawImageOnCanvas(canvas: Canvas, image: Image) {
        pixelWidth = canvas.width.toFloat() / image.size.columns.toFloat()
        pixelHeight = canvas.height.toFloat() / image.size.rows.toFloat()

        (0 until image.size.rows).forEach { i ->
            (0 until image.size.columns).forEach { j ->
                drawPixelOnCanvas(canvas, i, j, image.get(i, j))
            }
        }
    }

    private fun drawPixelOnCanvas(canvas: Canvas, i: Int, j: Int, color: Image.Color) {
        if (i < 0 || j < 0) {
            return
        }
        val left = j * pixelWidth
        val top = i * pixelHeight
        val paint = when(color) {
            Image.Color.BLACK -> paintBlack
            Image.Color.WHITE -> paintWhite
            Image.Color.REPLACEMENT -> paintReplacement
        }
        canvas.drawRect(left, top, left + pixelWidth, top + pixelHeight, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        currentImage?.let { image ->
            drawImageOnCanvas(canvas, image)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchDown = PointF(event.x, event.y).toPixel()
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (touchDown != null) {
                    performClick()
                    return true
                }
                return false
            }

            MotionEvent.ACTION_CANCEL -> {
                touchDown = null
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun PointF.toPixel(): Pixel {
        return Pixel(
                truncate(this.y / pixelHeight).toInt(),
                truncate(this.x / pixelWidth).toInt()
        )
    }

    override fun performClick(): Boolean {
        touchDown?.let(onPixelTouch)
        return super.performClick()
    }

}