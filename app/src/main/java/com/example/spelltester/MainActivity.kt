package com.example.spelltester

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spelltester.data.db.AppDatabase
import com.example.spelltester.data.db.attempt.Attempt
import com.example.spelltester.data.db.quiz.Quiz
import com.example.spelltester.data.db.user.User
import com.example.spelltester.data.db.word.Word
import com.example.spelltester.data.repositories.AppRepository
import com.example.spelltester.databinding.ActivityMainBinding
import com.example.spelltester.ui.QuizAdapter

const val TAG = "MAIN_ACT"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //bind view
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set the content view to the root view of the binding

        Log.d(TAG, "onCreate: before build")
        AppDatabase.invoke(this)
        AppRepository.invoke(AppDatabase.getInstance())
        Log.d(TAG, "onCreate: after build")


        val live = AppRepository.getInstance().getAllQuiz()
        val adapter = QuizAdapter(live)
        live.observe(this) { quiz ->
            adapter.quizList = quiz
            adapter.notifyDataSetChanged()
        }
        // Now you can access the RecyclerView from the binding
        binding.mainRecycle.adapter = adapter
        binding.mainRecycle.layoutManager = LinearLayoutManager(this)

        initViews()
    }

    private fun createSimpleData() {
        val user1 = User("user1")
        val quiz1 = Quiz("Quiz 1")
        val word = Word("hello", "مرحبا", quiz1.quizId)
        val word2 = Word("goodbye", "مع السلامة", quiz1.quizId)


        val attempt = Attempt(
            userId = user1.userId,
            quizId = quiz1.quizId,
            wordId = word.wordId,
            points = 3f,
            lastAttempt = 0
        )
        AppRepository.getInstance().upsert(user1)
        AppRepository.getInstance().upsert(quiz1)
        AppRepository.getInstance().upsert(word)
        AppRepository.getInstance().upsert(word2)
        AppRepository.getInstance().upsert(attempt)
    }

    private fun initViews() {
        binding.mainRecycle.adapter?.notifyDataSetChanged()
    }
}