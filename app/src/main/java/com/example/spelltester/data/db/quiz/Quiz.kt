package com.example.spelltester.data.db.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class Quiz(
    val name: String,

    @PrimaryKey(autoGenerate = true)
    var quizId: Int = 0,
)