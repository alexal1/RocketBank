package com.rocketbank.rockettest

import java.util.*
import java.util.concurrent.ExecutorService

/**
 * Class that does the work of image filling with given algorithm and provides methods to start
 * and stop filling process.
 */
class ImageHandler(val image: Image,
                   var algorithmType: Algorithm.Type,
                   @Volatile var speed: Float,
                   private val executorService: ExecutorService,
                   private var onPixelFilled: (Pixel) -> Unit) {

    companion object {

        const val MIN_DELAY = 0
        const val MAX_DELAY = 500

    }

    private var algorithmList: Queue<Algorithm> = LinkedList()

    fun startFromPixel(pixel: Pixel) {
        AlgorithmFactory.createAlgorithm(algorithmType).let { algorithm ->
            algorithm.image = image
            algorithm.startPixel = pixel
            algorithm.onPixelFilled = { pixel ->
                onPixelFilled(pixel)

                val delay = delayBySpeed()
                if (delay > 0) {
                    Thread.sleep(delay)
                }
            }
            algorithm.future = executorService.submit(algorithm)
            algorithmList.add(algorithm)
        }
    }

    private fun delayBySpeed(): Long {
        return (MAX_DELAY + speed * (MIN_DELAY - MAX_DELAY)).toLong()
    }

    fun stop() {
        var algorithm: Algorithm?
        do {
            algorithm = algorithmList.poll()
            algorithm?.stop()
        } while (algorithm != null)
    }

}