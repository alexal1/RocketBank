package com.rocketbank.rockettest

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
        }
        setListeners()
    }

    override fun onResume() {
        super.onResume()

        setImages()
    }

    private fun setImages() {
        imageA.drawImage(mainViewModel.imageA)
        imageB.drawImage(mainViewModel.imageB)
        editRows?.setText(mainViewModel.imageA.rows.toString())
        editColumns?.setText(mainViewModel.imageA.columns.toString())
    }

    private fun setListeners() {
        buttonGenerate.setOnClickListener {
            mainViewModel.generateNew()
            setImages()
        }
    }

}