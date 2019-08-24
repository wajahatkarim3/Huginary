package com.wajahatkarim3.huginary.android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.cloudinary.Url
import com.cloudinary.android.MediaManager
import com.cloudinary.android.ResponsiveUrl
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import com.wajahatkarim3.huginary.android.databinding.ActivityMainBinding
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.content.Context.CLIPBOARD_SERVICE
import android.text.ClipboardManager
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.UnsplashPickerActivity


class MainActivity : AppCompatActivity() {

    val RC_UNSPLASH_IMAGES = 1023
    private var currentUrl: String? = null
    private var thumnailUrl: String? = null
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

    fun onMarkdownClick()
    {
        currentUrl?.let {
            var str = getString(R.string.markdown_image_str, it)
            str = str.replace("https://", "//")
            str = str.replace("http://", "//")
            val cm = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = str

            Toast.makeText(this@MainActivity, "Copied to Clipboard.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onHugoClick()
    {
        currentUrl?.let {
            var str = it
            str = str.replace("https://", "//")
            str = str.replace("http://", "//")

            thumnailUrl = thumnailUrl?.replace("https://", "//") ?: str
            thumnailUrl = thumnailUrl?.replace("http://", "//") ?: str

            var template = "{{< image classes=\"clear fancybox  fig-100\" src=\"$str\" thumbnail=\"$thumnailUrl\" title=\"\" >}}"

            val cm = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = template

            Toast.makeText(this@MainActivity, "Copied to Clipboard.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onUnsplashClick()
    {
        startActivityForResult(
            UnsplashPickerActivity.getStartingIntent(
                this, // context
                false
            ), RC_UNSPLASH_IMAGES
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == Activity.RESULT_OK && data != null)
        {
            val images = data.getParcelableArrayListExtra<Image>(Config.EXTRA_IMAGES)

            bi.progress.visibility = View.VISIBLE
            bi.imgPreview.visibility = View.GONE

            CloudinaryRepository.uploadImageCallback(images[0].path, CloudinaryUploadCallback {
                onSuccessCallback { requestId, resultData ->

                    bi.progress.visibility = View.GONE
                    bi.imgPreview.visibility = View.VISIBLE

                    currentUrl = resultData["url"] as String
                    thumnailUrl = currentUrl
                    Glide.with(this@MainActivity).load(currentUrl).into(bi.imgPreview)
                }
                onErrorCallback { requestId, error ->

                    bi.progress.visibility = View.GONE
                    bi.imgPreview.visibility = View.VISIBLE

                    currentUrl = null
                    thumnailUrl = null

                    Toast.makeText(this@MainActivity, error?.description, Toast.LENGTH_LONG).show()
                }
            })
        }
        else if (requestCode == RC_UNSPLASH_IMAGES && resultCode == Activity.RESULT_OK)
        {
            val photos: ArrayList<UnsplashPhoto>? = data?.getParcelableArrayListExtra(UnsplashPickerActivity.EXTRA_PHOTOS)
            photos?.let {

                bi.progress.visibility = View.GONE
                bi.imgPreview.visibility = View.VISIBLE

                currentUrl = it[0].urls.regular
                thumnailUrl = it[0].urls.thumb
                Glide.with(this@MainActivity).load(currentUrl).into(bi.imgPreview)
            }
        }
    }
}
