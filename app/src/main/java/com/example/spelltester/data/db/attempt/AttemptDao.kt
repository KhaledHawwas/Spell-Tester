package com.example.spelltester.data.db.attempt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsert(wordShells: Attempt)

    @Query("SELECT * FROM attempt WHERE wordId = :wordId")
     fun getAttemptByWordId(wordId: Int):Attempt


    @Query("SELECT * FROM attempt")
     fun getAttempt(): List<Attempt>

    @Delete
     fun deleteAttempt(wordShells: Attempt)
}