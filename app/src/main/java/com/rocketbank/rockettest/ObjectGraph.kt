package com.rocketbank.rockettest

/**
 * Helper class for simple Dependency Injection implementation.
 */
class ObjectGraph {

    private val dependencies = HashMap<Class<*>, Any>()

    init {
        val repository = Repository()
        dependencies[Repository::class.java] = repository
    }

    fun <T> get(clazz: Class<T>): T {
        return clazz.cast(dependencies[clazz]) ?: throw NoSuchElementException()
    }

}