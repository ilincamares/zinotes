package com.example.zinotes

import android.app.Application
import com.example.zinotes.data.HanziRepository
import com.example.zinotes.data.OfflineHanziRepository
import com.example.zinotes.room.HanziRoomDatabase

class ZiNotesApplication: Application() {
    private val database: HanziRoomDatabase by lazy { HanziRoomDatabase.getDatabase(this)}

    val repository: HanziRepository by lazy {
        OfflineHanziRepository(database.hanziDao())
    }
}