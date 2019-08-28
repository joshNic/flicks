package com.example.flicks.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.flicks.Constants.IMAGE_URL
import com.example.flicks.R
import com.example.flicks.models.Comment
import com.example.flicks.models.Genre
import com.example.flicks.models.MovieTrailerResult
import com.example.flicks.models.Result
import com.example.flicks.overview.MoviesApiStatus
import com.example.flicks.overview.PhotoGridAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: PagedList<Result>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("trailerData")
fun bindTrailerRecyclerView(recyclerView: RecyclerView, dataa: List<MovieTrailerResult>?) {
    val adapter = recyclerView.adapter as MovieTrailerAdapter
    adapter.submitList(dataa)
}

@BindingAdapter("genreData")
fun bindGenreRecyclerView(recyclerView: RecyclerView, data: List<Genre>?) {
    val adapter = recyclerView.adapter as MovieGenreAdapter
    adapter.submitList(data)
}

@BindingAdapter("commentsData")
fun bindCommentsRecyclerView(recyclerView: RecyclerView, data: List<Comment>?) {
    val adapter = recyclerView.adapter as MovieCommentAdapter
    adapter.submitList(data)
}


@BindingAdapter("trailerName")
fun bindTextView(textView: TextView, name: String) {
    name.let { textView.text = name }
}

@BindingAdapter("genreName")
fun bindGenreTextView(textView: TextView, name: String) {
    name.let { textView.text = name }
}

@BindingAdapter("posterPath")
fun bindImage(imgView: ImageView, posterPath: String?) {
    posterPath?.let {
        val img = IMAGE_URL + posterPath

        val imgUri = img.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("moviesApiStatus")
fun bindStatus(statusImageView: ImageView, status: MoviesApiStatus?) {
    when (status) {
        MoviesApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MoviesApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MoviesApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("commentImage")
fun bindCommentImage(imgView: ImageView, posterPath: String?) {
    posterPath?.let {
        Glide.with(imgView.context)
            .load(R.drawable.ic_chat)
            .apply(
                RequestOptions.circleCropTransform()
            )
            .into(imgView)
    }
}

@BindingAdapter("author")
fun bindAuthorTextView(textView: TextView, author: String) {
    author.let { textView.text = author }
}

@BindingAdapter("cont")
fun bindContentTextView(textView: TextView, name: String) {
    name.let { textView.text = name }
}

