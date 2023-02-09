package com.pranay.musicwiki.model.artists

data class Artist(
    val bio: Bio,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val stats: Stats,
    val url: String
)
