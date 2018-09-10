package com.rocketbank.rockettest

import android.app.Application
import com.rocketbank.rockettest.helpers.ObjectGraph

class RocketApp : Application() {

    companion object {

        lateinit var objectGraph: ObjectGraph

    }

    override fun onCreate() {
        super.onCreate()

        objectGraph = ObjectGraph()
    }

}