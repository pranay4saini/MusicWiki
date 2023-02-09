package com.pranay.musicwiki

import com.pranay.musicwiki.api.RetrofitHelper


class Repository(private val retrofitHelper: RetrofitHelper) {

    suspend fun getGenre() = retrofitHelper.getGenre()
    suspend fun getGenreDetails(tag: String) = retrofitHelper.getGenreDetails(tag)
    suspend fun getTopAlbums(tag: String) = retrofitHelper.getTopAlbums(tag)
    suspend fun getTopArtists(tag: String) = retrofitHelper.getTopArtists(tag)
    suspend fun getTopTracks(tag: String) = retrofitHelper.getTopTracks(tag)
    suspend fun getAlbumDetails(artist: String, album: String) =
        retrofitHelper.getAlbumDetails(artist, album)

    suspend fun getTopGenres(artist: String, album: String) = retrofitHelper.getTopGenres(artist, album)
    suspend fun getArtistDetails(artist: String) =
        retrofitHelper.getArtistDetails(artist)

    suspend fun getArtistTags(mbid: String) =
        retrofitHelper.getArtistTags(mbid)

    suspend fun getArtistAlbums(mbid: String) =
        retrofitHelper.getArtistAlbums(mbid)

    suspend fun getArtistTracks(mbid: String) =
        retrofitHelper.getArtistTracks(mbid)

    suspend fun getArtistInfo(mbid: String) =
        retrofitHelper.getArtistInfo(mbid)

}