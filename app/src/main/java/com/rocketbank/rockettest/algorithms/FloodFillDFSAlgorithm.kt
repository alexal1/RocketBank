package com.rocketbank.rockettest.algorithms

import com.rocketbank.rockettest.model.Image
import com.rocketbank.rockettest.helpers.Pixel
import java.util.*
import java.util.concurrent.Future

class FloodFillDFSAlgorithm : Algorithm {

    override lateinit var image: Image
    override lateinit var startPixel: Pixel
    override lateinit var onPixelFilled: (Pixel) -> Unit
    override lateinit var future: Future<*>

    @Volatile private var isRunning = false
    private val stack = Stack<Pixel>()

    override fun run() {
        if (image[startPixel] != Image.Color.WHITE) {
            return
        }

        image[startPixel] = Image.Color.REPLACEMENT
        stack.push(startPixel)
        onPixelFilled(startPixel)

        isRunning = true
        while (isRunning && stack.isNotEmpty()) {
            val pixel = stack.pop()

            // West
            if (pixel.j > 0) {
                val westPixel = Pixel(pixel.i, pixel.j - 1)
                if (image[westPixel] == Image.Color.WHITE) {
                    image[westPixel] = Image.Color.REPLACEMENT
                    stack.push(westPixel)
                    onPixelFilled(westPixel)
                }
            }

            // East
            if (pixel.j < image.size.columns - 1) {
                val eastPixel = Pixel(pixel.i, pixel.j + 1)
                if (image[eastPixel] == Image.Color.WHITE) {
                    image[eastPixel] = Image.Color.REPLACEMENT
                    stack.push(eastPixel)
                    onPixelFilled(eastPixel)
                }
            }

            // North
            if (pixel.i > 0) {
                val northPixel = Pixel(pixel.i - 1, pixel.j)
                if (image[northPixel] == Image.Color.WHITE) {
                    image[northPixel] = Image.Color.REPLACEMENT
                    stack.push(northPixel)
                    onPixelFilled(northPixel)
                }
            }

            // South
            if (pixel.i < image.size.rows - 1) {
                val southPixel = Pixel(pixel.i + 1, pixel.j)
                if (image[southPixel] == Image.Color.WHITE) {
                    image[southPixel] = Image.Color.REPLACEMENT
                    stack.push(southPixel)
                    onPixelFilled(southPixel)
                }
            }
        }
    }

    override fun stop() {
        isRunning = false
        future.cancel(true)
    }

}