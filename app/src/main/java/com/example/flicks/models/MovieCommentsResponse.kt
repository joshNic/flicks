package com.example.flicks.models

data class MovieCommentsResponse(
    val id: Int,
    val page: Int,
    val results: List<Comment>,
    val total_pages: Int,
    val total_results: Int
)