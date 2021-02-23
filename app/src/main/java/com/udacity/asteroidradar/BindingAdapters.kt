package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.main.AsteroidAdapter
import com.udacity.asteroidradar.main.AsteroidStatus
import java.lang.reflect.Array.get

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("asteroidContentDescription")
fun bindAsteroidContentDescription(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageUrl")
fun bindImageOfTheDay(imageView: ImageView, imgUrl: PictureOfDay?) {
    imgUrl?.let {
//        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Picasso.with(imageView.context)
                .load(imgUrl.url)
                .placeholder(R.drawable.placeholder_picture_of_day)
                .into(imageView)
    }
}

@BindingAdapter("listData")
fun recyclerBinding(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(data)
}

@BindingAdapter("asteroidApiStatus")
fun goneIfNotNull(view: ImageView, status: AsteroidStatus?) {
    when (status) {
        AsteroidStatus.LOADING -> {
            view.visibility = View.VISIBLE
            view.setImageResource(R.drawable.loading_animation)
        }
        AsteroidStatus.ERROR -> {
            view.visibility = View.GONE
            view.setImageResource(R.drawable.ic_connection_error)
        }
        AsteroidStatus.DONE -> {
            view.visibility = View.GONE
        }
    }
}
