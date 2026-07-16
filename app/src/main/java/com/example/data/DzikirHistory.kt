package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dzikir_history")
data class DzikirHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateTimestamp: Long, // Start of the day timestamp
    val readingId: String,
    val totalCount: Int,
    val cyclesCompleted: Int
)
