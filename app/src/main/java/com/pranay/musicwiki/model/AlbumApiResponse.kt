package com.pranay.musicwiki.model

import com.squareup.moshi.Json

data class AlbumApiResponse(var albums:AlbumDataResponse)

class AlbumDataResponse(
    var album:MutableList<AlbumListResponse>
)

class AlbumListResponse (
    val  name :String,
    val mbid:String,
    var artist:AlbumArtistData,
    var image:MutableList<ImageData>


)
class AlbumArtistData(
    var name :String,
    var mbid :String,
    var url :String
)
class ImageData (

    @field:Json(name = "#text")  val text:String,
    var size :String

)

