package com.rocketbank.rockettest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View

/**
 * Custom widget that draws given three-color matrix of pixels.
 */
class ImageView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private var currentImage: Image? = null
    private var fullRedrawFlag = false
    private var lastI = -1
    private var lastJ = -1
    private var pixelWidth = 0f
    private var pixelHeight = 0f
    private val colorWhite = ContextCompat.getColor(context, R.color.white)
    private val colorBlack = ContextCompat.getColor(context, R.color.black)
    private val colorRed = ContextCompat.getColor(context, R.color.red)
    private val paintWhite = Paint().apply { color = colorWhite }
    private val paintBlack = Paint().apply { color = colorBlack }
    private val paintReplacement = Paint().apply { color = colorRed }

    fun drawImage(image: Image) {
        currentImage = image
        fullRedrawFlag = true
        lastI = -1
        lastJ = -1
        invalidate()
    }

    fun drawPixel(i: Int, j: Int) {
        lastI = i
        lastJ = j
        invalidate()
    }

    private fun drawImageOnCanvas(canvas: Canvas, image: Image) {
        pixelWidth = canvas.width.toFloat() / image.columns.toFloat()
        pixelHeight = canvas.height.toFloat() / image.rows.toFloat()

        (0 until image.rows).forEach { i ->
            (0 until image.columns).forEach { j ->
                drawPixelOnCanvas(canvas, i, j, image.matrix[i][j])
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
            if (fullRedrawFlag) {
                drawImageOnCanvas(canvas, image)
                fullRedrawFlag = false
            }
            else {
                drawPixelOnCanvas(canvas, lastI, lastJ, Image.Color.REPLACEMENT)
            }
        }
    }

}