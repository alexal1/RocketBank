package com.rocketbank.rockettest

/**
 * Business logic of the application
 */
class Repository {

    private var rows = 32
    private var columns = 32
    lateinit var imageA: Image
    lateinit var imageB: Image

    init {
        generateImages()
    }

    fun generateImages() {
        val newImageA = Image(rows, columns).apply { fillRandomly() }
        val newImageB = Image(newImageA)
        imageA = newImageA
        imageB = newImageB
    }

}