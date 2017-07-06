package com.example.qsuo.kotlinapp.commons

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.qsuo.kotlinapp.R
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String) {
  if (imageUrl.isEmpty()) {
    Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
  } else {
    Picasso.with(context).load(imageUrl).into(this)
  }
}

inline fun <reified T : Parcelable> createParcel(

  crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
  object : Parcelable.Creator<T> {
    override fun createFromParcel(source: Parcel?): T = createFromParcel(source)

    override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
  }