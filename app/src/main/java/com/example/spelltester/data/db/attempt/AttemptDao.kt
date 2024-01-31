package com.example.spelltester.data.db.attempt

import androidx.room.*

@Dao
interface AttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsert(wordShells: Attempt)

    @Query("SELECT * FROM attempts WHERE wordId = :wordId")
    fun getAttemptByWordId(wordId: Int): Attempt?


    @Query("SELECT * FROM attempts")
     fun getAttempt(): List<Attempt>

    @Delete
     fun deleteAttempt(wordShells: Attempt)
}