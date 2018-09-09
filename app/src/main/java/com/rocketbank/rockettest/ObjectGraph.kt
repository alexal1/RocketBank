package com.rocketbank.rockettest

import java.util.concurrent.Executors

/**
 * Helper class for simple Dependency Injection implementation.
 */
class ObjectGraph {

    private val dependencies = HashMap<Class<*>, Any>()

    init {
        val threadPoolExecutor = Executors.newFixedThreadPool(4)
        val repository = Repository(threadPoolExecutor)
        dependencies[Repository::class.java] = repository
    }

    fun <T> get(clazz: Class<T>): T {
        return clazz.cast(dependencies[clazz]) ?: throw NoSuchElementException()
    }

}