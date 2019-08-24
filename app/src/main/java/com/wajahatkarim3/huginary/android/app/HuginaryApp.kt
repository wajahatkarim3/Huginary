package com.wajahatkarim3.huginary.android.app

import android.app.Application
import com.cloudinary.android.MediaManager
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker
import com.wajahatkarim3.huginary.android.BuildConfig

class HuginaryApp : Application()
{

    override fun onCreate() {
        super.onCreate()

        instance = this

        // Initializing Cloudinary
        MediaManager.init(this)

        // Initializing Unsplash
        UnsplashPhotoPicker.init(
            this,
            BuildConfig.UNSPLASH_ACCESS_KEY,
            BuildConfig.UNSPLASH_SECRET_KEY
        )
    }

    companion object {
        lateinit var instance : HuginaryApp
    }
}