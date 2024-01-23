package com.example.spelltester.data.db.user

import androidx.room.*

@Entity(tableName = "users")
data class User(
    val username: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="user_id")
    var userId: Int=1,
)