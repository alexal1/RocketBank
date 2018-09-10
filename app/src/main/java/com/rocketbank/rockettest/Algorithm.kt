package com.rocketbank.rockettest

import java.util.concurrent.Future

interface Algorithm : Runnable {

    enum class Type(val stringRes: Int) {
        FLOOD_FILL(R.string.flood_fill_algorithm),
        SCAN_LINE(R.string.scan_line_algorithm)
    }

    var image: Image
    var startPixel: Pixel
    var onPixelFilled: (Pixel) -> Unit
    var future: Future<*>

    override fun run()

    fun stop()

}