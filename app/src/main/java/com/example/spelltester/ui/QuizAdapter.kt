package com.example.spelltester.ui

import android.content.*
import android.view.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.example.spelltester.*
import com.example.spelltester.data.db.*
import com.example.spelltester.data.db.quiz.*
import com.example.spelltester.databinding.*

const val TAG = "QUIZ_ADAPTER"

internal const val QUIZ_ID_KEY = "quizId"

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
        val progress = quiz.calculateProgress()
        holder.itemView.context.apply   {
        binding.cardNameTv.text = quiz.name
          binding.cardInfoTv.text=  "${getString(R.string.known_words)}${progress.correct}\n${getString(R.string.unknown_words)}${progress.incorrect}\n${
                getString(R.string.unanswered_words)
            }${progress.unanswered}"
        }
        binding.cardStart.setOnClickListener {
            val intent = Intent(it.context, SpellTestingActivity::class.java)
            intent.putExtra(QUIZ_ID_KEY, quiz.quizId)
            it.context.startActivity(intent)
        }
        binding.cardProgress.apply {
            max = progress.correct + progress.incorrect + progress.unanswered
            setProgress(progress.correct)
            secondaryProgress = progress.incorrect
        }
    }

}