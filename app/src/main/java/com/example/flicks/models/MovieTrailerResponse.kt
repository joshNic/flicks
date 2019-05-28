package com.example.flicks.models

data class MovieTrailerResponse(
    val id: Int,
    val results: List<MovieTrailerResult>
)