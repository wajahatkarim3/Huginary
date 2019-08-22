package com.wajahatkarim3.huginary.android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.cloudinary.Url
import com.cloudinary.android.MediaManager
import com.cloudinary.android.ResponsiveUrl
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import com.wajahatkarim3.huginary.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bi: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bi.view = this
    }

    fun onUploadClick()
    {
        ImagePicker.with(this)
            .setMultipleMode(false)
            .setFolderMode(true)
            .setShowCamera(false)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == Activity.RESULT_OK && data != null)
        {
            val images = data.getParcelableArrayListExtra<Image>(Config.EXTRA_IMAGES)

            var publicId = CloudinaryRepository.uploadImage(images[0].path)

            val url = MediaManager.get().url().publicId(publicId).generate(publicId)
            Glide.with(this).load(url).into(bi.imgPreview)


        }
    }
}
