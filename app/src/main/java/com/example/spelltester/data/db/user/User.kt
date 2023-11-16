package com.example.spelltester.data.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    val username: String,

    @PrimaryKey(autoGenerate = true)
    var userId: Int=0,
){

}