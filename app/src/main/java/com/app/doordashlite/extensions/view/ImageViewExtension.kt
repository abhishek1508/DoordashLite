package com.app.doordashlite.extensions.view

import android.widget.ImageView
import com.app.doordashlite.di.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(url: String?) {
    val reqOptions = RequestOptions().centerCrop()
    GlideApp.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(reqOptions)
            .into(this)
}

fun ImageView.loadCircularImage(url: String?) {
    val reqOptions = RequestOptions().circleCrop()
    GlideApp.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(reqOptions)
            .into(this)
}

