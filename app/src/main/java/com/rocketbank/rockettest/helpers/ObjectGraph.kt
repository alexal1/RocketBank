package com.rocketbank.rockettest.helpers

import com.rocketbank.rockettest.model.Repository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Helper class for simple Dependency Injection implementation.
 */
class ObjectGraph {

    private val dependencies = HashMap<Class<*>, Any>()

    init {
        val algorithmExecutor = Executors.newFixedThreadPool(4)
        val drawingExecutor = Executors.newFixedThreadPool(2)
        val repository = Repository(algorithmExecutor)

        dependencies[ExecutorService::class.java] = drawingExecutor
        dependencies[Repository::class.java] = repository
    }

    fun <T> get(clazz: Class<T>): T {
        return clazz.cast(dependencies[clazz]) ?: throw NoSuchElementException()
    }

}