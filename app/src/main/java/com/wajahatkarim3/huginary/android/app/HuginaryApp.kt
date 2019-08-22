package com.wajahatkarim3.huginary.android.app

import android.app.Application
import com.cloudinary.android.MediaManager

class HuginaryApp : Application()
{

    override fun onCreate() {
        super.onCreate()

        HuginaryApp.instance = this
        // Initializing Cloudinary
        MediaManager.init(this)
    }

    companion object {
        lateinit var instance : HuginaryApp
    }
}