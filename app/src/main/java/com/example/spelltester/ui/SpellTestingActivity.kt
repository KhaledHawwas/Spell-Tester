package com.example.spelltester.ui

import android.os.*
import androidx.appcompat.app.*
import com.example.spelltester.*
import com.example.spelltester.data.db.*
import com.example.spelltester.data.db.attempt.*
import com.example.spelltester.data.db.word.*
import com.example.spelltester.data.repositories.*
import com.example.spelltester.databinding.*
import java.util.*
import kotlin.math.*
import kotlin.random.Random


class SpellTestingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpellTestingBinding
    var quizId: Int? = null
    var attempts: List<Attempt> = mutableListOf()
    private val repository = AppRepository.getInstance()
    private var isAnswering = true
    private var attempt: Attempt? = null
    private var gainedPoints = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpellTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        quizId = intent.getIntExtra(  QUIZ_ID_KEY, -1)
        if (quizId == -1) {
            binding.txtSpell.setText(R.string.no_quiz_selected)
            return
        }
        quizId?.let { id ->
            val words = repository.getWordsByWordsId(
                repository.getQuizByQuizId(id)?.wordsId ?: IntArray(0)
            )
            words.forEach { word ->
                Attempt.addIfNotExist(word.wordId, quizId ?: 0)
            }
            attempts = repository.getAttemptsByQuizId(id)
        }
        attempt = getNextAttempt()
        binding.btnNext.setOnClickListener {
            if (isAnswering) {
                attempt?.let {
                    if (binding.txtSpell.text?.isBlank() != false) {
                        binding.txtInfo.setText(R.string.please_enter_a_word)
                        return@setOnClickListener
                    }
                    gainedPoints = answer(binding.txtSpell.text.toString())
                    if (gainedPoints == -1f) {
                        binding.txtHead.setText(R.string.error_finding_word)
                        return@setOnClickListener
                    }
                    it.points = min(Attempt.MAX_POINT, it.points + gainedPoints)
                    it.points = max(Attempt.MIN_POINT, it.points)
                    it.lastAttempt = System.currentTimeMillis()
                    repository.upsert(it)
                }
            } else {
                attempt = getNextAttempt()
            }
            isAnswering = !isAnswering
            refreshUI()
        }
        refreshUI()
    }

    private fun formatWord(word: String): String {
        return word.trim().lowercase(Locale.ROOT).replace(" ", "")
    }

    fun answer(userInput: String?): Float {
        val userAnswer: String = formatWord(userInput ?: "")
        val thisWord: Word = attempt?.word ?: return -1f
        val correctAnswer: String = thisWord.englishWord

        val fault: Int = minDistance(correctAnswer, userAnswer)
        var point = when {
            fault == 0 -> 2
            correctAnswer.length / 3 >= fault -> 1
            correctAnswer.length / 2 >= fault -> -1
            else -> -2
        }
        //   thisWord.answer(point)
        return point.toFloat()
    }

    private fun refreshUI() {
        if (quizId == null) {
            binding.txtHead.setText(R.string.no_quiz_selected)
            return
        }
        val repository = AppRepository.getInstance()
        val quiz = quizId?.let { repository.getQuizByQuizId(it) }
        if (quiz == null) {
            binding.txtHead.setText(R.string.error_finding_quiz)
            return
        }
        if (attempts.isEmpty()) {
            binding.txtHead.text =
                getString(R.string.no_words_in_quiz)+" "+quizId
            return
        }
        if (attempt == null) {
            binding.txtHead.setText (R.string.you_are_done)
            binding.btnNext.setText (R.string.done)
            return
        }
        var attempt = attempt!!
        if (isAnswering) {
            binding.txtHead.text = attempt.word?.arabicWord ?: getString(R.string.error_finding_word)
            binding.btnNext.setText(R.string.next)
            //TODO: set color to default

            binding.txtInfo.text =
                "remaining words: ${attempts.filter { it.points != Attempt.MAX_POINT }.size}"
            //TODO: info text

        } else {

            if (gainedPoints != 2f) {
                binding.txtInfo.setTextColor(getColor(R.color.red))
                binding.txtInfo.text =
                    getString(R.string.wrong_answer_points) + (if (gainedPoints > 0) "+" else "") + gainedPoints
            } else {
                binding.txtInfo.setTextColor(getColor(R.color.green))
                binding.txtInfo.text =
                    getString(R.string.right_answer_points) + (if (gainedPoints > 0) "+" else "") + gainedPoints
            }
            binding.btnNext.setText(R.string.answer)
            binding.txtHead.text = attempt.word?.englishWord + "\n" + attempt.word?.arabicWord
        }

    }

    private fun getNextAttempt(): Attempt? {
        // choose word in words with ratio
        var filteredAttempts = attempts.filter { it.points != Attempt.MAX_POINT }
        if (filteredAttempts.isEmpty()) return null
        val totalWeight = filteredAttempts.sumOf { (it.points +4).toDouble()}
        return attempts.asSequence().filter { it.points != Attempt.MAX_POINT }
            .find() { Random.nextInt(100) < 30 + -(8 * it.points).toInt() }


    }
}