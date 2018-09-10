package com.rocketbank.rockettest

import java.util.concurrent.Future

interface Algorithm : Runnable {

    enum class Type(val stringRes: Int) {
        FLOOD_FILL_BFS(R.string.flood_fill_bfs_algorithm),
        FLOOD_FILL_DFS(R.string.flood_fill_dfs_algorithm),
        SCAN_LINE(R.string.scan_line_algorithm)
    }

    var image: Image
    var startPixel: Pixel
    var onPixelFilled: (Pixel) -> Unit
    var future: Future<*>

    override fun run()

    fun stop()

}