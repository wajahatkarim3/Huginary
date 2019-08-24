package com.wajahatkarim3.huginary.android

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.policy.TimeWindow
import com.wajahatkarim3.huginary.android.app.HuginaryApp

object CloudinaryRepository
{
    fun uploadImage(uri: Uri) : String
    {
        var request = MediaManager.get().upload(uri)
            .unsigned("huginary")
            .constrain(TimeWindow.getDefault())
            .option("resource_type", "auto")
            .maxFileSize(10 * 1024 * 1024)      // max 10mb
            .policy(MediaManager.get().globalUploadPolicy.newBuilder().maxRetries(2).build())


        return request.dispatch(HuginaryApp.instance.applicationContext)
    }

    fun uploadImage(filePath: String) : String
    {
        var request = MediaManager.get().upload(filePath)
            .unsigned("huginary")
            .constrain(TimeWindow.getDefault())
            .option("resource_type", "auto")
            .maxFileSize(10 * 1024 * 1024)      // max 10mb
            .policy(MediaManager.get().globalUploadPolicy.newBuilder().maxRetries(2).build())

        return request.dispatch(HuginaryApp.instance.applicationContext)
    }

    fun uploadImageCallback(filePath: String, callback: CloudinaryUploadCallback)
    {
        var request = MediaManager.get().upload(filePath)
            .unsigned("huginary")
            .constrain(TimeWindow.getDefault())
            .option("resource_type", "auto")
            .maxFileSize(10 * 1024 * 1024)
            .policy(MediaManager.get().globalUploadPolicy.newBuilder().maxRetries(2).build())

        request.callback(callback).dispatch(HuginaryApp.instance.applicationContext)
    }
}