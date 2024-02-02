package com.example.spelltester

import android.os.*
import android.util.*
import androidx.appcompat.app.*
import androidx.recyclerview.widget.*
import com.example.spelltester.data.db.*
import com.example.spelltester.data.repositories.*
import com.example.spelltester.databinding.*
import com.example.spelltester.ui.*

const val TAG = "MAIN_ACT"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
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
        binding.mainRecycle.adapter = adapter
        binding.mainRecycle.layoutManager = LinearLayoutManager(this)
        initViews()
    }

    private fun initViews() {
        binding.mainRecycle.adapter?.notifyDataSetChanged()
    }
}