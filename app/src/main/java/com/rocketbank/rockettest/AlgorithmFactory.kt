package com.rocketbank.rockettest

object AlgorithmFactory {

    fun createAlgorithm(type: Algorithm.Type): Algorithm {
        return when(type) {
            Algorithm.Type.FLOOD_FILL -> FloodFillAlgorithm()
        }
    }

}