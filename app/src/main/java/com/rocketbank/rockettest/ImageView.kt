package com.rocketbank.rockettest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View

/**
 * Custom widget that draws given monochromatic matrix of pixels.
 */
class ImageView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private var currentImage: Image? = null
    private var fullRedrawFlag = false
    private var lastI = -1
    private var lastJ = -1
    private var pixelSize = 0f
    private val rectangle = RectF()
    private val paintWhite = Paint().apply { color = Color.WHITE }
    private val paintBlack = Paint().apply { color = Color.BLACK }
    private val colorGrey = ContextCompat.getColor(context, R.color.grey)

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
        // Calculate rectangle
        val canvasRatio = canvas.width.toFloat() / canvas.height.toFloat()
        val imageRatio = image.columns.toFloat() / image.rows.toFloat()
        if (canvasRatio > imageRatio) {
            rectangle.top = 0f
            rectangle.bottom = canvas.height.toFloat()
            val imageWidth = canvas.height * imageRatio
            rectangle.left = (canvas.width - imageWidth) / 2f
            rectangle.right = rectangle.left + imageWidth

            pixelSize = imageWidth / image.columns
        }
        else {
            rectangle.left = 0f
            rectangle.right = canvas.width.toFloat()
            val imageHeight = canvas.width / imageRatio
            rectangle.top = (canvas.height - imageHeight) / 2f
            rectangle.bottom = rectangle.top + imageHeight

            pixelSize = imageHeight / image.rows
        }

        // Clear canvas
        canvas.drawColor(colorGrey)

        // Actually draw
        canvas.drawRect(rectangle, paintWhite)
        (0 until image.rows).forEach { i ->
            (0 until image.columns).forEach { j ->
                if (image.matrix[i][j]) {
                    drawPixelOnCanvas(canvas, i, j)
                }
            }
        }
    }

    private fun drawPixelOnCanvas(canvas: Canvas, i: Int, j: Int) {
        if (i < 0 || j < 0) {
            return
        }
        val left = rectangle.left + j * pixelSize
        val top = rectangle.top + i * pixelSize
        canvas.drawRect(left, top, left + pixelSize, top + pixelSize, paintBlack)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        currentImage?.let { image ->
            if (fullRedrawFlag) {
                drawImageOnCanvas(canvas, image)
                fullRedrawFlag = false
            }
            else {
                drawPixelOnCanvas(canvas, lastI, lastJ)
            }
        }
    }

}