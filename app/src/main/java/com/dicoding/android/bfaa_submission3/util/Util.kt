package com.dicoding.android.bfaa_submission3.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dicoding.android.bfaa_submission3.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.avatar_load)
        .into(this)
}
