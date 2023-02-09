package com.pranay.musicwiki.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pranay.musicwiki.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: MusicServices = getRetrofit().create(MusicServices::class.java)
}