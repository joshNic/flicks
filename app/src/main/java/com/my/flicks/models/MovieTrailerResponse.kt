package com.my.flicks.models

data class MovieTrailerResponse(
    val id: Int,
    val results: List<MovieTrailerResult>
)