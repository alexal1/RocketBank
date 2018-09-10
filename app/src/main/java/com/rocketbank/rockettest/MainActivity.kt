package com.rocketbank.rockettest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java).apply {
            onCreate()

            imagesGenerated.observe(this@MainActivity, Observer {
                setImages()
            })
            updatesA.observe(this@MainActivity, Observer {
                this@MainActivity.imageA.drawImage()
            })
            updatesB.observe(this@MainActivity, Observer {
                this@MainActivity.imageB.drawImage()
            })
            errors.observe(this@MainActivity, Observer { error ->
                Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_LONG).show()
            })
        }

        setListeners()
        setSpinner()
        setEditTexts()
    }

    private fun setImages() {
        mainViewModel.imageA?.let { imageA.drawImage(it) }
        mainViewModel.imageB?.let { imageB.drawImage(it) }
    }

    private fun setEditTexts() {
        editRows?.setText(mainViewModel.size.rows.toString())
        editColumns?.setText(mainViewModel.size.columns.toString())
    }

    private fun setListeners() {
        buttonGenerate.setOnClickListener {
            mainViewModel.generateNew()
        }
        buttonSize?.setOnClickListener {
            val transaction = supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(SizeChooseFragment.TAG)
            SizeChooseFragment().show(transaction, SizeChooseFragment.TAG)
        }

        imageA.onPixelTouch = mainViewModel::startFromPixel
        imageB.onPixelTouch = mainViewModel::startFromPixel

        seekBarSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mainViewModel.speed = progress / 100f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                mainViewModel.setSize(
                        editRows?.text?.toString() ?: "",
                        editColumns?.text?.toString() ?: ""
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        }
        editRows?.addTextChangedListener(textWatcher)
        editColumns?.addTextChangedListener(textWatcher)
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

    override fun onBackPressed() {
        super.onBackPressed()

        setEditTexts()
    }

}