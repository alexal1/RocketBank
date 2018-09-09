package com.rocketbank.rockettest

import java.util.concurrent.ExecutorService

/**
 * Business logic of the application.
 */
class Repository(private val executorService: ExecutorService) {

    private var handlerA: ImageHandler? = null
    private var handlerB: ImageHandler? = null
    val imageA get() = handlerA?.image
    val imageB get() = handlerB?.image
    var size = Size(16, 16)
    var speed = 0.5f
    var algorithmType = Algorithm.Type.FLOOD_FILL
    var onPixelFilledA: (Pixel) -> Unit = {}
    var onPixelFilledB: (Pixel) -> Unit = {}

    fun generateImages() {
        stopAllHandlers()
        val newImageA = Image(size).apply { fillRandomly() }
        val newImageB = Image(newImageA)
        handlerA = ImageHandler(newImageA, algorithmType, speed, executorService) { pixel ->
            onPixelFilledA(pixel)
        }
        handlerB = ImageHandler(newImageB, algorithmType, speed, executorService) { pixel ->
            onPixelFilledB(pixel)
        }
    }

    fun startFromPixel(pixel: Pixel) {
        handlerA?.startFromPixel(pixel)
        handlerB?.startFromPixel(pixel)
    }

    fun stopAllHandlers() {
        handlerA?.stop()
        handlerB?.stop()
    }

}