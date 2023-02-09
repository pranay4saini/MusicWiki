package com.pranay.musicwiki.model.topArtist

data class Artist(
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val url: String
)
