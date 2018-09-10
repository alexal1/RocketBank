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
        set(value) {
            field = value
            handlerA?.speed = value
            handlerB?.speed = value
        }
    var algorithmTypeA = Algorithm.Type.FLOOD_FILL_BFS
        set(value) {
            field = value
            handlerA?.algorithmType = value
        }
    var algorithmTypeB = Algorithm.Type.SCAN_LINE
        set(value) {
            field = value
            handlerB?.algorithmType = value
        }
    var onPixelFilledA: (Pixel) -> Unit = {}
    var onPixelFilledB: (Pixel) -> Unit = {}
    var onOutOfMemoryError: (OutOfMemoryError) -> Unit = {}

    init {
        generateImages(fill = false)
    }

    fun generateImages(fill: Boolean) {
        stopAllHandlers()
        try {
            val newImageA = Image(size).apply { if (fill) fillRandomly() }
            val newImageB = Image(newImageA)

            handlerA = ImageHandler(newImageA, algorithmTypeA, speed, executorService) { pixel ->
                onPixelFilledA(pixel)
            }
            handlerB = ImageHandler(newImageB, algorithmTypeB, speed, executorService) { pixel ->
                onPixelFilledB(pixel)
            }
        }
        catch (e: OutOfMemoryError) {
            onOutOfMemoryError(e)
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