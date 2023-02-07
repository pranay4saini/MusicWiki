package com.pranay.musicwiki.api

import com.pranay.musicwiki.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitHelper {

    private val api: MusicServices

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        api = retrofit.create(MusicServices::class.java)
    }


    private fun getUnsafeOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
        return builder.build()
    }

}