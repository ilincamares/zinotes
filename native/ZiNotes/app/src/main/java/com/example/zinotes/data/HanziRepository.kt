package com.example.zinotes.data

import com.example.zinotes.room.Hanzi
import kotlinx.coroutines.flow.Flow

interface HanziRepository {
    fun getHanziStream(): Flow<List<Hanzi>>
    suspend fun getHanzi(id: Long): Hanzi?
    suspend fun addHanzi(hanzi: Hanzi)
    suspend fun updateHanzi(hanzi: Hanzi)
    suspend fun deleteHanziById(id: Long)
}