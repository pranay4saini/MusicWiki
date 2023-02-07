package com.pranay.musicwiki.api

import com.pranay.musicwiki.model.AlbumApiResponse
import com.pranay.musicwiki.model.AlbumDetailsApiResponse
import com.pranay.musicwiki.model.ArtistApiResponse
import com.pranay.musicwiki.model.ArtistDetailsApiResponse
import com.pranay.musicwiki.model.ArtistTopAlbumsApiResponse
import com.pranay.musicwiki.model.ArtistTopTracksApiResponse
import com.pranay.musicwiki.model.TagDetailsApiResponse
import com.pranay.musicwiki.model.TagsApiResponse
import com.pranay.musicwiki.model.TrackApiResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicServices {

    @GET("2.0")
   suspend fun getTagsApi(@Query("api_key") apikey: String,
                   @Query("method") method: String,
                   @Query("format") format: String
    ): Response<TagsApiResponse>


    @GET("2.0")
    suspend fun getTagsDetailsApi(@Query("api_key") apikey: String,
                          @Query("method") method: String,
                          @Query("format") format: String,
                          @Query("tag") tag: String

    ): Response<TagDetailsApiResponse>

    @GET("2.0")
    suspend fun getAlbumApi(@Query("api_key") apikey: String,
                    @Query("method") method: String,
                    @Query("format") format: String,
                    @Query("tag") tag: String

    ): Response<AlbumApiResponse>

    @GET("2.0")
    suspend fun getArtistApi(@Query("api_key") apikey: String,
                     @Query("method") method: String,
                     @Query("format") format: String,
                     @Query("tag") tag: String

    ): Response<ArtistApiResponse>

    @GET("2.0")
    suspend fun getTracksApi(@Query("api_key") apikey: String,
                     @Query("method") method: String,
                     @Query("format") format: String,
                     @Query("tag") tag: String

    ): Response<TrackApiResponse>

    @GET("2.0")
    suspend fun getAlbumDetailsApi(@Query("api_key") apikey: String,
                           @Query("method") method: String,
                           @Query("format") format: String,
                           @Query("artist") artist: String,
                           @Query("album") album: String


    ): Response<AlbumDetailsApiResponse>

    @GET("2.0")
    suspend fun getArtistDetailsApi(@Query("api_key") apikey: String,
                            @Query("method") method: String,
                            @Query("format") format: String,
                            @Query("artist") artist: String

    ): Response<ArtistDetailsApiResponse>

    @GET("2.0")
    suspend fun getArtistTopAlbums(@Query("api_key") apikey: String,
                           @Query("method") method: String,
                           @Query("format") format: String,
                           @Query("artist") artist: String

    ): Response<ArtistTopAlbumsApiResponse>

    @GET("2.0")
    suspend fun getArtistTopTracks(@Query("api_key") apikey: String,
                           @Query("method") method: String,
                           @Query("format") format: String,
                           @Query("artist") artist: String

    ): Response<ArtistTopTracksApiResponse>




}