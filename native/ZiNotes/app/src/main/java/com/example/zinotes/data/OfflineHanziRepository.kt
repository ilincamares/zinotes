package com.example.zinotes.data

import com.example.zinotes.room.Hanzi
import com.example.zinotes.room.HanziDao
import kotlinx.coroutines.flow.Flow

class OfflineHanziRepository(private val hanziDao: HanziDao) : HanziRepository {

    override fun getHanziStream(): Flow<List<Hanzi>> = hanziDao.getAll()

    override suspend fun getHanzi(id: Long): Hanzi? = hanziDao.getById(id)

    override suspend fun addHanzi(hanzi: Hanzi) = hanziDao.insert(hanzi)

    override suspend fun updateHanzi(hanzi: Hanzi) = hanziDao.update(hanzi)

    override suspend fun deleteHanziById(id: Long) = hanziDao.deleteById(id)

}