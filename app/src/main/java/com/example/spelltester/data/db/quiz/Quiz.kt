package com.example.spelltester.data.db.quiz

import androidx.room.*
import com.example.spelltester.data.db.word.*
import com.example.spelltester.data.repositories.*

@Entity(tableName = "quiz")
data class Quiz(
    val name: String,
    @TypeConverters(IntArrayConverter::class)
    val wordsId: IntArray,
    @PrimaryKey(autoGenerate = true)
    var quizId: Int = 1,
) {
    fun fetchWords(repository: Repository = AppRepository.getInstance()): List<Word> {
        return repository.getWordsByWordsId(wordsId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Quiz

        if (name != other.name) return false
        if (!wordsId.contentEquals(other.wordsId)) return false
        if (quizId != other.quizId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + wordsId.contentHashCode()
        result = 31 * result + quizId
        return result
    }

}

