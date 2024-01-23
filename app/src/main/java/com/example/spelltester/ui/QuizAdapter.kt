package com.example.spelltester.ui

import android.view.*
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.example.spelltester.data.db.*
import com.example.spelltester.data.db.quiz.*
import com.example.spelltester.databinding.*

const val TAG = "QUIZ_ADAPTER"

class QuizAdapter(
    var liveData: LiveData<List<Quiz>>
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    val size
        get() = quizList.size

    var quizList: List<Quiz> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class QuizViewHolder(val binding: QuizCardViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding =
            QuizCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.cardStart.setOnClickListener {

        }
        return QuizViewHolder(binding)
    }

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val binding = holder.binding
        val quiz = quizList[position]

        binding.cardTv.text = quiz.name
        binding.cardProgress.apply {
            val progress = quiz.calculateProgress()
            max = progress.correct + progress.incorrect + progress.unanswered
            setProgress(progress.correct)
            secondaryProgress = progress.incorrect
        }
    }

}