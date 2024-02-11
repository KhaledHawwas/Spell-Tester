package com.example.spelltester.data.db.attempt

import androidx.room.*
import kotlinx.coroutines.flow.*

@Dao
interface AttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsert(wordShells: Attempt)

    @Query("SELECT * FROM attempts WHERE wordId = :wordId")
    fun getAttemptByWordId(wordId: Int): Attempt?

    @Query("SELECT * FROM attempts")
     fun getAttempt(): Flow<List<Attempt>>

    @Delete
     fun deleteAttempt(wordShells: Attempt)
}