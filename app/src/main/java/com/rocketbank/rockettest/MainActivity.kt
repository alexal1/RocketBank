package com.rocketbank.rockettest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        setSpinner()
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

    private fun setSpinner() {
        val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                Algorithm.Type.values().map { getString(it.stringRes) })

        spinnerA.adapter = adapter
        spinnerA.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainViewModel.algorithmTypeA = Algorithm.Type.values()[id.toInt()]
            }

        }
        spinnerA.setSelection(Algorithm.Type.values().indexOf(mainViewModel.algorithmTypeA))

        spinnerB.adapter = adapter
        spinnerB.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainViewModel.algorithmTypeB = Algorithm.Type.values()[id.toInt()]
            }

        }
        spinnerB.setSelection(Algorithm.Type.values().indexOf(mainViewModel.algorithmTypeB))
    }

}