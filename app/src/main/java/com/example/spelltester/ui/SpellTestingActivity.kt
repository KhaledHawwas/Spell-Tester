package com.example.spelltester.ui

import android.os.*
import androidx.appcompat.app.*
import com.example.spelltester.*
import com.example.spelltester.data.db.attempt.*
import com.example.spelltester.data.repositories.*
import com.example.spelltester.databinding.*
import com.example.spelltester.ui.SpellTestingViewModel.*


class SpellTestingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySpellTestingBinding
    var quizId: Int? = null
    private val repository = AppRepository.getInstance()
    private lateinit var viewModel: SpellTestingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpellTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        quizId = intent.getIntExtra(QUIZ_ID_KEY, -1)
        viewModel = SpellTestingViewModel(repository, quizId ?: -1)
        binding.btnNext.setOnClickListener {
         val message=   viewModel.processClicking(binding.txtSpell.text.toString()!!)
            when (message) {
                SpellTestingViewModel.Message.FINISH -> {
                    finish()
                }
                SpellTestingViewModel.Message.EMPTY -> {
                    binding.txtInfo.text = getString(R.string.please_enter_a_word)
                }
                else -> refreshUI()
            }
        }
        refreshUI()
    }

    private fun refreshUI() {
        val gainedPoints = viewModel.gainedPoints
        when (viewModel.status) {
            Status.ANSWERING -> {
                val attempt = viewModel.attempt!!
                binding.txtHead.text = attempt.word?.arabicWord ?: getString(R.string.error_finding_word)
                binding.btnNext.setText(R.string.next)
                //TODO: set color to default
                binding.txtInfo.text =
                    "${getString(R.string.remain)} :${viewModel.attempts.filter { it.points != Attempt.MAX_POINT }.size}"
            }
            Status.SHOWING -> {
                if (gainedPoints != 2f) {
                    binding.txtInfo.setTextColor(getColor(R.color.red))
                    binding.txtInfo.text =
                        getString(R.string.wrong_answer_points) + (if (gainedPoints > 0) "+" else "") + gainedPoints
                } else {
                    binding.txtInfo.setTextColor(getColor(R.color.green))
                    binding.txtInfo.text =
                        getString(R.string.right_answer_points) + (if (gainedPoints > 0) "+" else "") + gainedPoints
                }
                var attempt = viewModel.attempt!!
                binding.btnNext.setText(R.string.answer)
                binding.txtHead.text = attempt.word?.englishWord + "\n" + attempt.word?.arabicWord
            }
            Status.DONE -> {
                binding.txtInfo.text = ""
                binding.txtHead.text = getString(R.string.congratulation)
                binding.btnNext.setText(R.string.done)
            }
            Status.ERROR -> {
                binding.txtHead.text = getString(viewModel.errorMessage)
                binding.btnNext.setText(R.string.done)
            }
        }


    }


}