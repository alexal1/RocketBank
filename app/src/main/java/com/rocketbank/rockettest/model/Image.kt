package com.rocketbank.rockettest.model

import com.rocketbank.rockettest.helpers.Pixel
import com.rocketbank.rockettest.helpers.Size
import java.util.*

/**
 * Data structure class that represents image as a matrix of pixels.
 */
class Image {

    enum class Color { WHITE, BLACK, REPLACEMENT }

    val size: Size
    private val matrix: Array<Array<Color>>

    constructor(size: Size) {
        this@Image.size = size
        this@Image.matrix = Array(size.rows) { _ -> Array(size.columns) { _ -> Color.WHITE } }
    }

    constructor(otherImage: Image) {
        this@Image.size = otherImage.size
        this@Image.matrix = Array(size.rows) { i -> Array(size.columns) { j -> otherImage.matrix[i][j] } }
    }

    private val random = Random()

    fun fillRandomly() {
        (0 until size.rows).forEach { i ->
            (0 until size.columns).forEach {j ->
                set(i, j, if (random.nextBoolean()) Color.WHITE else Color.BLACK)
            }
        }
    }

    operator fun set(pixel: Pixel, color: Color) {
        set(pixel.i, pixel.j, color)
    }

    operator fun get(pixel: Pixel): Color {
        return get(pixel.i, pixel.j)
    }

    @Synchronized fun set(i: Int, j: Int, color: Color) {
        if (color == Color.REPLACEMENT && matrix[i][j] == Color.BLACK) {
            throw Exception("You cannot replace black pixels!")
        }
        matrix[i][j] = color
    }

    @Synchronized fun get(i: Int, j: Int): Color {
        return matrix[i][j]
    }

}