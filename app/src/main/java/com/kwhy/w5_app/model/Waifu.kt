package com.kwhy.w5_app.model

data class Waifu(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val status: String,
    val affiliation: String,
    val game: String,
    var isFavorite: Boolean = false
)
