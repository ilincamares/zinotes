package com.example.zinotes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HanziDao {
    @Query("SELECT * FROM hanzi WHERE isDeleted = 0 ORDER BY id ASC")
    fun getAll(): Flow<List<Hanzi>>

    @Query("SELECT * FROM hanzi WHERE id = :id AND isDeleted = 0")
    suspend fun getById(id: Long): Hanzi?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hanzi: Hanzi): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(hanzi: Hanzi)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hanzi: List<Hanzi>)

    @Query("SELECT * FROM hanzi WHERE isSynced = 0")
    suspend fun getUnsynced(): List<Hanzi>

    @Query("UPDATE hanzi SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)

    @Query("UPDATE hanzi SET isDeleted = 1, isSynced = 0 WHERE id = :id")
    suspend fun softDelete(id: Long)

    @Query("DELETE FROM hanzi WHERE id = :id")
    suspend fun hardDelete(id: Long)
}
