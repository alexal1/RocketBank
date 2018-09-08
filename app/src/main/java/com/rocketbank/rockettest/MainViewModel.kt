package com.rocketbank.rockettest

import android.arch.lifecycle.ViewModel

/**
 * ViewModel class for the MainActivity.
 *
 * Caution: A ViewModel must never reference a view, Lifecycle, or any class that may hold a
 * reference to the activity context.
 */
class MainViewModel : ViewModel() {

    private lateinit var repository: Repository
    val imageA: Image get() = repository.imageA
    val imageB: Image get() = repository.imageB

    fun onCreate() {
        repository = RocketApp.objectGraph.get(Repository::class.java)
    }

    fun generateNew() {
        repository.generateImages()
    }

}