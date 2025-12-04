package com.example.zinotes.data.network

import com.example.zinotes.room.Hanzi
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HanziApiService {
    @GET("api/hanzi")
    suspend fun getHanzi(): List<Hanzi>

    @POST("api/hanzi")
    suspend fun addHanzi(@Body hanzi: Hanzi): Hanzi

    @PUT("api/hanzi/{id}")
    suspend fun updateHanzi(@Path("id") id: Long, @Body hanzi: Hanzi): Hanzi

    @DELETE("api/hanzi/{id}")
    suspend fun deleteHanzi(@Path("id") id: Long)
}