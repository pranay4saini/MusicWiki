package com.pranay.musicwiki.model

data class ArtistApiResponse(var topartists:ArtistDataResponse)

class ArtistDataResponse(
    var artist:MutableList<ArtistListResponse>
)

class ArtistListResponse (

    var name :String,
    var mbid:String,
    var url:String,
    var image:MutableList<ImageData>


)