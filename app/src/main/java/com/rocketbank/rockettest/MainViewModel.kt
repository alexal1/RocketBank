package com.rocketbank.rockettest

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils

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
    val imagesGenerated = MutableLiveData<Image>()
    val updatesA = MutableLiveData<Pixel>()
    val updatesB = MutableLiveData<Pixel>()
    val errors = MutableLiveData<Throwable>()
    var algorithmTypeA = Algorithm.Type.FLOOD_FILL_BFS
        set(value) {
            field = value
            repository.algorithmTypeA = value
        }
    var algorithmTypeB = Algorithm.Type.SCAN_LINE
        set(value) {
            field = value
            repository.algorithmTypeB = value
        }
    var speed = 0.5f
        set(value) {
            field = value
            repository.speed = speed
        }

    fun onCreate() {
        repository = RocketApp.objectGraph.get(Repository::class.java)

        repository.onImagesGenerated = { image ->
            imagesGenerated.postValue(image)
        }
        repository.onPixelFilledA = { pixel ->
            updatesA.postValue(pixel)
        }
        repository.onPixelFilledB = { pixel ->
            updatesB.postValue(pixel)
        }
        repository.onOutOfMemoryError = { error ->
            errors.postValue(error)
        }

        repository.algorithmTypeA = algorithmTypeA
        repository.algorithmTypeB = algorithmTypeB
        repository.speed = speed
    }

    fun generateNew() {
        repository.generateImages(fill = true)
    }

    fun startFromPixel(pixel: Pixel) {
        repository.startFromPixel(pixel)
    }

    fun setSize(rows: String, columns: String) {
        if (rows.validate() && columns.validate()) {
            repository.size = Size(rows = rows.toInt(), columns = columns.toInt())
        }
    }

    private fun String.validate(): Boolean {
        if (this@validate.isEmpty()) return false
        if (!TextUtils.isDigitsOnly(this@validate)) return false
        try {
            if (this@validate.toInt() == 0) return false
        }
        catch (e: NumberFormatException) {
            errors.postValue(e)
            return false
        }

        return true
    }

    override fun onCleared() {
        super.onCleared()

        repository.stopAllHandlers()
    }

}