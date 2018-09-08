package com.rocketbank.rockettest

import java.util.*

/**
 * Class that represents image as a matrix of pixels and all needed operations with it.
 */
class Image {

    val rows: Int
    val columns: Int
    val matrix: Array<Array<Boolean>>

    constructor(rows: Int, columns: Int) {
        this@Image.rows = rows
        this@Image.columns = columns
        this@Image.matrix = Array(rows) { _ -> Array(columns) { _ -> false } }
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
                matrix[i][j] = random.nextBoolean()
            }
        }
    }

    fun addPoint(i: Int, j: Int) {
        matrix[i][j] = true
    }

}