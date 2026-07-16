package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM dzikir_history ORDER BY dateTimestamp DESC")
    fun getAllHistory(): Flow<List<DzikirHistory>>

    @Query("SELECT * FROM dzikir_history WHERE dateTimestamp = :date AND readingId = :readingId LIMIT 1")
    suspend fun getHistoryByDateAndReading(date: Long, readingId: String): DzikirHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: DzikirHistory)

    @Update
    suspend fun updateHistory(history: DzikirHistory)

    @Query("DELETE FROM dzikir_history")
    suspend fun clearHistory()
}
