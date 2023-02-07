package com.pranay.musicwiki.model

data class TrackApiResponse(var tracks:TrackDataResponse)

class TrackDataResponse(
    var track:MutableList<TrackListResponse>
)

class TrackListResponse (

    var name :String,
    var artist:TrackArtistData,
    var duration :String,
    var mbid :String,
    var image:MutableList<ImageData>


)

class TrackArtistData(
    var name :String,
    var mbid :String,
    var url :String
)
