package com.rocketbank.rockettest

import android.app.Application

class RocketApp : Application() {

    companion object {

        lateinit var objectGraph: ObjectGraph

    }

    override fun onCreate() {
        super.onCreate()

        objectGraph = ObjectGraph()
    }

}