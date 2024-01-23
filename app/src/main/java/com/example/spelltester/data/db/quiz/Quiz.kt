package com.example.spelltester.data.db.quiz

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spelltester.data.db.word.Word
import com.example.spelltester.data.repositories.AppRepository
import com.example.spelltester.data.repositories.Repository

@Entity(tableName = "quiz")
data class Quiz(
    val name: String,

    @PrimaryKey(autoGenerate = true)
    var quizId: Int = 1,
){
    fun fetchWords(repository: Repository = AppRepository.getInstance()): List<Word> {
        return repository.getWordByQuizId(quizId)
    }
}