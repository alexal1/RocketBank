package com.rocketbank.rockettest

import java.util.*
import java.util.concurrent.ExecutorService

/**
 * Class that does the work of image filling with given algorithm and provides methods to start
 * and stop filling process.
 */
class ImageHandler(val image: Image,
                   var algorithmType: Algorithm.Type,
                   private var speed: Float,
                   private val executorService: ExecutorService,
                   private var onPixelFilled: (Pixel) -> Unit) {

    private var algorithmList: Queue<Algorithm> = LinkedList()

    fun startFromPixel(pixel: Pixel) {
        AlgorithmFactory.createAlgorithm(algorithmType).let { algorithm ->
            algorithm.image = image
            algorithm.startPixel = pixel
            algorithm.onPixelFilled = { pixel ->
                onPixelFilled(pixel)
                Thread.sleep(100)
            }
            algorithm.future = executorService.submit(algorithm)
            algorithmList.add(algorithm)
        }
    }

    fun stop() {
        var algorithm: Algorithm?
        do {
            algorithm = algorithmList.poll()
            algorithm?.stop()
        } while (algorithm != null)
    }

}