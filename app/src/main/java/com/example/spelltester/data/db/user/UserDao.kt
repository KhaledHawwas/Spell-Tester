package com.example.spelltester.data.db.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spelltester.data.db.attempt.Attempt

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(users: User)

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): User

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM attempt WHERE userId = :userId")
    fun getAttemptsByUserId(userId: Int): List<Attempt>
}