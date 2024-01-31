package com.example.spelltester.data.db.user

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spelltester.data.db.attempt.Attempt

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(users: User)

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM attempts WHERE userId = :userId")
    fun getAttemptsByUserId(userId: Int): List<Attempt>
}