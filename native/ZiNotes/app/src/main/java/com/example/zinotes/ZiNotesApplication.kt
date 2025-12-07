package com.example.zinotes

import android.app.Application
import com.example.zinotes.data.HanziRepository
import com.example.zinotes.data.network.HanziApiService
import com.example.zinotes.room.HanziRoomDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ZiNotesApplication: Application() {
    private val database: HanziRoomDatabase by lazy { HanziRoomDatabase.getDatabase(this)}

    val okHttpClient: OkHttpClient by lazy{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)


        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()

                val requestWithAuth = original.newBuilder()
                    .header("X-API-KEY", BuildConfig.APP_API_KEY)
                    .build()

                chain.proceed(requestWithAuth)
            }
            .addInterceptor(logging)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://zinotes-deploy.onrender.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val apiService: HanziApiService by lazy{
        retrofit.create(HanziApiService::class.java)
    }

    val repository: HanziRepository by lazy {
        HanziRepository(
            database.hanziDao(),
            apiService = apiService,
            okHttpClient = okHttpClient
        )
    }
}