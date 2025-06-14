package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.data.QuizRepository
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.models.Question
import com.example.quizapp.models.QuizResult
import com.google.android.material.snackbar.Snackbar

class QuizActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORY_ID = "extra_category_id"
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
        const val TIMER_DURATION = 30_000L // 30 seconds per question
        const val TIMER_INTERVAL = 1_000L // Update every second

        // Keys for saving state
        private const val KEY_CURRENT_QUESTION_INDEX = "current_question_index"
        private const val KEY_SCORE = "score"
        private const val KEY_CORRECT_ANSWERS = "correct_answers"
        private const val KEY_INCORRECT_ANSWERS = "incorrect_answers"
        private const val KEY_TOTAL_TIME_USED = "total_time_used"
        private const val KEY_TIME_REMAINING = "time_remaining"
        private const val KEY_SELECTED_OPTION = "selected_option"
        private const val KEY_USER_ANSWERS = "user_answers"
        private const val KEY_TIMER_RUNNING = "timer_running"
    }

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizRepository: QuizRepository
    private lateinit var questions: List<Question>
    private lateinit var countDownTimer: CountDownTimer

    private var currentQuestionIndex = 0
    private var score = 0
    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private var timeRemainingMillis = TIMER_DURATION
    private var totalTimeUsedSeconds = 0
    private var categoryId = ""
    private var categoryName = ""
    private var userAnswers = mutableMapOf<Int, Int>() // QuestionId to AnswerIndex
    private var selectedOptionIndex = -1 // Track selected option
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            showExitConfirmationDialog()
        }

        // Get category from intent
        categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID) ?: ""
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: ""
        supportActionBar?.title = categoryName

        quizRepository = QuizRepository(this)
        questions = quizRepository.getQuestionsByCategory(categoryId)

        if (questions.isEmpty()) {
            Snackbar.make(
                binding.root,
                "No questions found for this category",
                Snackbar.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        // Restore state if available
        if (savedInstanceState != null) {
            restoreQuizState(savedInstanceState)
        }

        setupQuiz()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Cancel timer before saving state
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }

        // Save quiz state
        outState.putInt(KEY_CURRENT_QUESTION_INDEX, currentQuestionIndex)
        outState.putInt(KEY_SCORE, score)
        outState.putInt(KEY_CORRECT_ANSWERS, correctAnswers)
        outState.putInt(KEY_INCORRECT_ANSWERS, incorrectAnswers)
        outState.putInt(KEY_TOTAL_TIME_USED, totalTimeUsedSeconds)
        outState.putLong(KEY_TIME_REMAINING, timeRemainingMillis)
        outState.putInt(KEY_SELECTED_OPTION, selectedOptionIndex)
        outState.putBoolean(KEY_TIMER_RUNNING, isTimerRunning)

        // Save user answers
        val answersArray = IntArray(userAnswers.size * 2)
        var index = 0
        for ((questionId, answerIndex) in userAnswers) {
            answersArray[index++] = questionId
            answersArray[index++] = answerIndex
        }
        outState.putIntArray(KEY_USER_ANSWERS, answersArray)
    }

    private fun restoreQuizState(savedInstanceState: Bundle) {
        currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION_INDEX, 0)
        score = savedInstanceState.getInt(KEY_SCORE, 0)
        correctAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS, 0)
        incorrectAnswers = savedInstanceState.getInt(KEY_INCORRECT_ANSWERS, 0)
        totalTimeUsedSeconds = savedInstanceState.getInt(KEY_TOTAL_TIME_USED, 0)
        timeRemainingMillis = savedInstanceState.getLong(KEY_TIME_REMAINING, TIMER_DURATION)
        selectedOptionIndex = savedInstanceState.getInt(KEY_SELECTED_OPTION, -1)
        isTimerRunning = savedInstanceState.getBoolean(KEY_TIMER_RUNNING, false)

        // Restore user answers
        val answersArray = savedInstanceState.getIntArray(KEY_USER_ANSWERS)
        if (answersArray != null) {
            userAnswers.clear()
            for (i in answersArray.indices step 2) {
                if (i + 1 < answersArray.size) {
                    userAnswers[answersArray[i]] = answersArray[i + 1]
                }
            }
        }
    }

    private fun setupQuiz() {
        binding.progressBar.max = questions.size
        binding.progressBar.progress = currentQuestionIndex + 1

        // Set up radio button listeners for manual selection tracking
        binding.rbOption1.setOnClickListener { selectOption(0) }
        binding.rbOption2.setOnClickListener { selectOption(1) }
        binding.rbOption3.setOnClickListener { selectOption(2) }
        binding.rbOption4.setOnClickListener { selectOption(3) }

        binding.btnSubmit.setOnClickListener {
            if (selectedOptionIndex != -1) {
                checkAnswer(selectedOptionIndex)
            } else {
                Snackbar.make(
                    binding.root,
                    "Please select an answer",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        displayQuestion()
    }

    private fun selectOption(optionIndex: Int) {
        // Clear all selections first
        binding.rbOption1.isChecked = false
        binding.rbOption2.isChecked = false
        binding.rbOption3.isChecked = false
        binding.rbOption4.isChecked = false

        // Reset all card colors
        resetOptionColors()

        // Set the selected option
        selectedOptionIndex = optionIndex
        when (optionIndex) {
            0 -> {
                binding.rbOption1.isChecked = true
                binding.cardOption1.strokeColor = ContextCompat.getColor(this, R.color.primary)
                binding.cardOption1.strokeWidth = resources.getDimensionPixelSize(R.dimen.card_stroke_width) * 2
            }
            1 -> {
                binding.rbOption2.isChecked = true
                binding.cardOption2.strokeColor = ContextCompat.getColor(this, R.color.primary)
                binding.cardOption2.strokeWidth = resources.getDimensionPixelSize(R.dimen.card_stroke_width) * 2
            }
            2 -> {
                binding.rbOption3.isChecked = true
                binding.cardOption3.strokeColor = ContextCompat.getColor(this, R.color.primary)
                binding.cardOption3.strokeWidth = resources.getDimensionPixelSize(R.dimen.card_stroke_width) * 2
            }
            3 -> {
                binding.rbOption4.isChecked = true
                binding.cardOption4.strokeColor = ContextCompat.getColor(this, R.color.primary)
                binding.cardOption4.strokeWidth = resources.getDimensionPixelSize(R.dimen.card_stroke_width) * 2
            }
        }
    }

    private fun displayQuestion() {
        val currentQuestion = questions[currentQuestionIndex]

        // Don't reset selection if we're restoring state
        if (selectedOptionIndex == -1) {
            // Reset selection only for new questions
            selectedOptionIndex = -1
        }

        // Animate question and options only if it's a new question
        if (!isTimerRunning || selectedOptionIndex == -1) {
            val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            binding.cardQuestion.startAnimation(fadeIn)
            binding.optionsContainer.startAnimation(fadeIn)
        }

        // Update question text and counter
        binding.tvQuestion.text = currentQuestion.text
        binding.tvQuestionCounter.text = "Question ${currentQuestionIndex + 1}/${questions.size}"

        // Update progress bar
        binding.progressBar.progress = currentQuestionIndex + 1

        // Set options
        binding.rbOption1.text = currentQuestion.options[0]
        binding.rbOption2.text = currentQuestion.options[1]
        binding.rbOption3.text = currentQuestion.options[2]
        binding.rbOption4.text = currentQuestion.options[3]

        // Restore selection if available
        if (selectedOptionIndex != -1) {
            selectOption(selectedOptionIndex)
        } else {
            // Clear previous selection for new questions
            binding.rbOption1.isChecked = false
            binding.rbOption2.isChecked = false
            binding.rbOption3.isChecked = false
            binding.rbOption4.isChecked = false
            resetOptionColors()
        }

        // Start or restart timer
        startTimer()

        // Update button text for last question
        if (currentQuestionIndex == questions.size - 1) {
            binding.btnSubmit.text = "Submit"
        } else {
            binding.btnSubmit.text = "Next"
        }
    }

    private fun resetOptionColors() {
        val cards = listOf(
            binding.cardOption1,
            binding.cardOption2,
            binding.cardOption3,
            binding.cardOption4
        )

        cards.forEach {
            it.strokeColor = ContextCompat.getColor(this, R.color.gray_300)
            it.strokeWidth = resources.getDimensionPixelSize(R.dimen.card_stroke_width)
            it.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        }

        // Reset radio button text colors
        binding.rbOption1.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
        binding.rbOption2.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
        binding.rbOption3.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
        binding.rbOption4.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
    }

    private fun startTimer() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }

        // Use saved time remaining if available, otherwise use full duration
        val timerDuration = if (isTimerRunning) timeRemainingMillis else TIMER_DURATION
        timeRemainingMillis = timerDuration
        isTimerRunning = true

        countDownTimer = object : CountDownTimer(timeRemainingMillis, TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingMillis = millisUntilFinished
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvTimer.text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)

                // Change color based on time remaining
                when {
                    secondsRemaining < 6 -> binding.tvTimer.setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.error))
                    secondsRemaining < 11 -> binding.tvTimer.setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.warning))
                    else -> binding.tvTimer.setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.white))
                }
            }

            override fun onFinish() {
                // Time's up, move to next question
                timeRemainingMillis = 0
                isTimerRunning = false
                totalTimeUsedSeconds += (TIMER_DURATION / 1000).toInt()

                Snackbar.make(
                    binding.root,
                    "Time's up! Moving to next question.",
                    Snackbar.LENGTH_SHORT
                ).show()

                incorrectAnswers++
                moveToNextQuestion()
            }
        }.start()
    }
//check
    private fun checkAnswer(selectedOptionIndex: Int) {
        countDownTimer.cancel()
        isTimerRunning = false

        val currentQuestion = questions[currentQuestionIndex]
        val timeUsedForQuestion = ((TIMER_DURATION - timeRemainingMillis) / 1000).toInt()
        totalTimeUsedSeconds += timeUsedForQuestion

        val isCorrect = selectedOptionIndex == currentQuestion.correctAnswerIndex
        userAnswers[currentQuestion.id] = selectedOptionIndex

        if (isCorrect) {
            correctAnswers++
            score += calculateScore(timeUsedForQuestion)
            showFeedback(true)
        } else {
            incorrectAnswers++
            showFeedback(false)
        }
    }

    private fun calculateScore(timeUsedSeconds: Int): Int {
        // Base score of 100 points for a correct answer
        // Bonus points for quick answers (max 50 extra points)
        val baseScore = 100
        val timeBonus = ((TIMER_DURATION / 1000) - timeUsedSeconds) * 50 / (TIMER_DURATION / 1000)
        return baseScore + timeBonus.toInt().coerceAtLeast(0)
    }

    private fun showFeedback(isCorrect: Boolean) {
        val currentQuestion = questions[currentQuestionIndex]

        // Change card colors
        val cards = listOf(
            binding.cardOption1,
            binding.cardOption2,
            binding.cardOption3,
            binding.cardOption4
        )

        // Reset all cards
        cards.forEachIndexed { index, card ->
            val radioButton = when (index) {
                0 -> binding.rbOption1
                1 -> binding.rbOption2
                2 -> binding.rbOption3
                3 -> binding.rbOption4
                else -> null
            }
//colorbz
            when {
                index == selectedOptionIndex && isCorrect -> {
                    // Correctly selected option
                    card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.success))
                    radioButton?.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
                index == selectedOptionIndex && !isCorrect -> {
                    // Incorrectly selected option
                    card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.error))
                    radioButton?.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
                index == currentQuestion.correctAnswerIndex -> {
                    // Highlight the correct option
                    card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.success))
                    radioButton?.setTextColor(ContextCompat.getColor(this, R.color.white))
                }
            }
        }

        // Show explanation
        val message = if (isCorrect) "Correct! ${currentQuestion.explanation}" else "Incorrect. ${currentQuestion.explanation}"
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()

        // Disable options
        binding.rbOption1.isEnabled = false
        binding.rbOption2.isEnabled = false
        binding.rbOption3.isEnabled = false
        binding.rbOption4.isEnabled = false

        // Wait for 2 seconds before moving to next question
        binding.root.postDelayed({
            moveToNextQuestion()
        }, 2000)
    }

    private fun moveToNextQuestion() {
        currentQuestionIndex++

        if (currentQuestionIndex < questions.size) {
            // Reset option colors and enable options
            resetOptionColors()
            binding.rbOption1.isEnabled = true
            binding.rbOption2.isEnabled = true
            binding.rbOption3.isEnabled = true
            binding.rbOption4.isEnabled = true

            // Reset selection for new question
            selectedOptionIndex = -1
            isTimerRunning = false
            timeRemainingMillis = TIMER_DURATION

            // Display next question
            displayQuestion()
        } else {
            // Quiz completed, show results
            showResults()
        }
    }

    private fun showResults() {
        val totalQuestions = questions.size
        val percentScore = (correctAnswers * 100) / totalQuestions

        val quizResult = QuizResult(
            categoryId = categoryId,
            categoryName = categoryName,
            score = score,
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswers,
            incorrectAnswers = incorrectAnswers,
            timeTakenSeconds = totalTimeUsedSeconds
        )

        quizRepository.saveQuizResult(quizResult)

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(ResultActivity.EXTRA_SCORE, score)
            putExtra(ResultActivity.EXTRA_PERCENTAGE, percentScore)
            putExtra(ResultActivity.EXTRA_TOTAL_QUESTIONS, totalQuestions)
            putExtra(ResultActivity.EXTRA_CORRECT_ANSWERS, correctAnswers)
            putExtra(ResultActivity.EXTRA_TIME_TAKEN, totalTimeUsedSeconds)
            putExtra(ResultActivity.EXTRA_CATEGORY_ID, categoryId)
            putExtra(ResultActivity.EXTRA_CATEGORY_NAME, categoryName)
        }
        startActivity(intent)
        finish()
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Quit Quiz")
            .setMessage("Are you sure you want to quit? Your progress will be lost.")
            .setPositiveButton("Quit") { _, _ ->
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    override fun onDestroy() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        // Cancel timer when activity is paused to prevent issues
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        // Restart timer if we're in the middle of a question
        if (isTimerRunning && currentQuestionIndex < questions.size) {
            startTimer()
        }
    }
}