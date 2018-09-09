package com.rocketbank.rockettest

import java.util.concurrent.Future

interface Algorithm : Runnable {

    enum class Type { FLOOD_FILL }

    var image: Image
    var startPixel: Pixel
    var onPixelFilled: (Pixel) -> Unit
    var future: Future<*>

    override fun run()

    fun stop()

}