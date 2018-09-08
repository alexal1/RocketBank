package com.rocketbank.rockettest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val modelA = Image(30, 20).apply { fillRandomly() }
        val modelB = Image(modelA)
        imageA.drawImage(modelA)
        imageB.drawImage(modelB)
    }

}