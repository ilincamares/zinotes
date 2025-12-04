package com.example.zinotes

import android.app.Application
import com.example.zinotes.data.HanziRepository
import com.example.zinotes.data.OfflineHanziRepository
import com.example.zinotes.data.network.HanziApiService
import com.example.zinotes.room.HanziRoomDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ZiNotesApplication: Application() {
    private val database: HanziRoomDatabase by lazy { HanziRoomDatabase.getDatabase(this)}

    val okHttpClient: OkHttpClient by lazy{
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val apiService: HanziApiService by lazy{
        retrofit.create(HanziApiService::class.java)
    }

    val repository: HanziRepository by lazy {
        OfflineHanziRepository(
            database.hanziDao(),
            apiService = apiService,
            okHttpClient = okHttpClient
        )
    }
}