package com.rocketbank.rockettest

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * ViewModel class for the MainActivity.
 *
 * Caution: A ViewModel must never reference a view, Lifecycle, or any class that may hold a
 * reference to the activity context.
 */
class MainViewModel : ViewModel() {

    private lateinit var repository: Repository
    val imageA: Image? get() = repository.imageA
    val imageB: Image? get() = repository.imageB
    val size: Size get() = repository.size
    val updatesA = MutableLiveData<Pixel>()
    val updatesB = MutableLiveData<Pixel>()

    fun onCreate() {
        repository = RocketApp.objectGraph.get(Repository::class.java)
        repository.onPixelFilledA = { pixel ->
            updatesA.postValue(pixel)
        }
        repository.onPixelFilledB = { pixel ->
            updatesB.postValue(pixel)
        }
    }

    fun generateNew() {
        repository.generateImages()
    }

    fun startFromPixel(pixel: Pixel) {
        repository.startFromPixel(pixel)
    }

    override fun onCleared() {
        super.onCleared()

        repository.stopAllHandlers()
    }

}