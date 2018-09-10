package com.rocketbank.rockettest.algorithms

import com.rocketbank.rockettest.model.Image
import com.rocketbank.rockettest.helpers.Pixel
import java.util.*
import java.util.concurrent.Future

class ScanLineAlgorithm : Algorithm {

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

        stack.add(startPixel)

        isRunning = true
        while(isRunning && stack.isNotEmpty()) {
            var (i, j) = stack.pop()
            if (image.get(i, j) != Image.Color.WHITE) {
                continue
            }

            // Find left boundary
            while (j > 0 && image.get(i, j - 1) == Image.Color.WHITE) {
                j--
            }

            // Scan line
            while (j < image.size.columns && image.get(i, j) == Image.Color.WHITE) {
                val pixel = Pixel(i, j)
                image[pixel] = Image.Color.REPLACEMENT
                onPixelFilled(pixel)

                // Detect color change above
                if (i > 0) {
                    if (image.get(i - 1, j) == Image.Color.WHITE) {
                        stack.add(Pixel(i - 1, j))
                    }
                }

                // Detect color change below
                if (i < image.size.rows - 1) {
                    if (image.get(i + 1, j) == Image.Color.WHITE) {
                        stack.add(Pixel(i + 1, j))
                    }
                }

                j++
            }
        }
    }

    override fun stop() {
        isRunning = false
        future.cancel(true)
    }

}