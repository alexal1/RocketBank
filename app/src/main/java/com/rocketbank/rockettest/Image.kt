package com.rocketbank.rockettest

import java.util.*

/**
 * Class that represents image as a matrix of pixels and all needed operations with it.
 */
class Image {

    enum class Color { WHITE, BLACK, REPLACEMENT }

    val rows: Int
    val columns: Int
    val matrix: Array<Array<Color>>

    constructor(rows: Int, columns: Int) {
        this@Image.rows = rows
        this@Image.columns = columns
        this@Image.matrix = Array(rows) { _ -> Array(columns) { _ -> Color.WHITE } }
    }

    constructor(otherImage: Image) {
        this@Image.rows = otherImage.rows
        this@Image.columns = otherImage.columns
        this@Image.matrix = Array(rows) { i -> Array(columns) { j -> otherImage.matrix[i][j] } }
    }

    private val random = Random()

    fun fillRandomly() {
        (0 until rows).forEach { i ->
            (0 until columns).forEach {j ->
                matrix[i][j] = if (random.nextBoolean()) Color.WHITE else Color.BLACK
            }
        }
    }

    fun fill(i: Int, j: Int) {
        if (matrix[i][j] == Color.BLACK) {
            throw Exception("Cannot fill this point as it is black")
        }
        matrix[i][j] = Color.REPLACEMENT
    }

}