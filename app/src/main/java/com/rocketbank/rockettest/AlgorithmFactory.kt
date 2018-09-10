package com.rocketbank.rockettest

object AlgorithmFactory {

    fun createAlgorithm(type: Algorithm.Type): Algorithm {
        return when(type) {
            Algorithm.Type.FLOOD_FILL_BFS -> FloodFillBFSAlgorithm()
            Algorithm.Type.FLOOD_FILL_DFS -> FloodFillDFSAlgorithm()
            Algorithm.Type.SCAN_LINE -> ScanLineAlgorithm()
        }
    }

}