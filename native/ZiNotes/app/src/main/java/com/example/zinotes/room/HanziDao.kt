package com.example.zinotes.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HanziDao {
    @Query("SELECT * FROM hanzi")
    fun getAll(): Flow<List<Hanzi>>

    @Query("SELECT * FROM hanzi WHERE id = :id")
    suspend fun getById(id: Long): Hanzi?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hanzi: Hanzi)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(hanzi: Hanzi)

    @Query("DELETE FROM hanzi WHERE id = :id")
    suspend fun deleteById(id: Long)
}
