package com.wajahatkarim3.huginary.android

import com.cloudinary.android.UploadRequest
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class CloudinaryUploadCallback(callback: CloudinaryUploadCallback.() -> Unit) : UploadCallback
{
    private var _onSuccess: ((requestId: String?, resultData: MutableMap<Any?, Any?>) -> Unit)? = null
    private var _onError: ((requestId: String?, error: ErrorInfo?) -> Unit)? = null

    override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>) {
        _onSuccess?.invoke(requestId, resultData)
    }

    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

    }

    override fun onReschedule(requestId: String?, error: ErrorInfo?) {

    }

    override fun onError(requestId: String?, error: ErrorInfo?) {
        _onError?.invoke(requestId, error)
    }

    override fun onStart(requestId: String?) {

    }

    fun onSuccessCallback(method: ((requestId: String?, resultData: MutableMap<Any?, Any?>) -> Unit))
    {
        _onSuccess = method
    }

    fun onErrorCallback(method: (requestId: String?, error: ErrorInfo?) -> Unit)
    {
        _onError = method
    }

    init {
        callback()
    }
}