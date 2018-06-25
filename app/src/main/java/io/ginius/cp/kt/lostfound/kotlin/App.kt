package io.ginius.cp.kt.lostfound.kotlin

import android.app.Application



class App : Application() {


    override fun onCreate() {
        super.onCreate()

    }

    companion object {

        var API = "http://18.216.65.139:3000/"
    }


}