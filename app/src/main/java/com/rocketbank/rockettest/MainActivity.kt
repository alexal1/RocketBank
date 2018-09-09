package com.rocketbank.rockettest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java).apply {
            onCreate()
            updatesA.observe(this@MainActivity, Observer { pixel ->
                this@MainActivity.imageA.invalidate()
            })
            updatesB.observe(this@MainActivity, Observer { pixel ->
                this@MainActivity.imageB.invalidate()
            })
        }
        setListeners()
    }

    override fun onResume() {
        super.onResume()

        setImages()
    }

    private fun setImages() {
        mainViewModel.imageA?.let { imageA.drawImage(it) }
        mainViewModel.imageB?.let { imageB.drawImage(it) }
        editRows?.setText(mainViewModel.size.rows.toString())
        editColumns?.setText(mainViewModel.size.columns.toString())
    }

    private fun setListeners() {
        buttonGenerate.setOnClickListener {
            mainViewModel.generateNew()
            setImages()
        }

        imageA.onPixelTouch = mainViewModel::startFromPixel
        imageB.onPixelTouch = mainViewModel::startFromPixel
    }

}