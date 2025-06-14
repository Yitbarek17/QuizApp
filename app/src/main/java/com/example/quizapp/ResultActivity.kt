package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_PERCENTAGE = "extra_percentage"
        const val EXTRA_TOTAL_QUESTIONS = "extra_total_questions"
        const val EXTRA_CORRECT_ANSWERS = "extra_correct_answers"
        const val EXTRA_TIME_TAKEN = "extra_time_taken"
        const val EXTRA_CATEGORY_ID = "extra_category_id"
        const val EXTRA_CATEGORY_NAME = "extra_category_name"
    }

    private lateinit var binding: ActivityResultBinding

    // Store result data for sharing
    private var percentage = 0
    private var totalQuestions = 0
    private var correctAnswers = 0
    private var timeTakenSeconds = 0
    private var categoryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)

        // Get extras
        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        percentage = intent.getIntExtra(EXTRA_PERCENTAGE, 0)
        totalQuestions = intent.getIntExtra(EXTRA_TOTAL_QUESTIONS, 0)
        correctAnswers = intent.getIntExtra(EXTRA_CORRECT_ANSWERS, 0)
        timeTakenSeconds = intent.getIntExtra(EXTRA_TIME_TAKEN, 0)
        val categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID) ?: ""
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: ""

        supportActionBar?.title = "$categoryName Quiz Results"

        // Update UI with result data
        binding.tvScore.text = "$percentage%"
        binding.tvCorrectAnswers.text = correctAnswers.toString()
        binding.tvIncorrectAnswers.text = (totalQuestions - correctAnswers).toString()

        // Format time
        val minutes = timeTakenSeconds / 60
        val seconds = timeTakenSeconds % 60
        binding.tvTimeTaken.text = String.format("%d:%02d", minutes, seconds)

        // Update congratulations message based on performance
        binding.tvCongratulations.text = when {
            percentage >= 90 -> "Excellent!"
            percentage >= 70 -> "Great job!"
            percentage >= 50 -> "Good effort!"
            else -> "Nice try!"
        }

        // Set button listeners
        binding.btnRetry.setOnClickListener {
            // Go back to the quiz activity with same category
            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra(QuizActivity.EXTRA_CATEGORY_ID, categoryId)
                putExtra(QuizActivity.EXTRA_CATEGORY_NAME, categoryName)
            }
            startActivity(intent)
            finish()
        }

        binding.btnHome.setOnClickListener {
            // Return to main screen
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        // Set share button listener
        binding.btnShare.setOnClickListener {
            shareQuizResults()
        }
    }

    private fun shareQuizResults() {
        val shareText = buildShareText()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "My Quiz Results - $categoryName")
        }

        // Create chooser to show all available sharing apps
        val chooserIntent = Intent.createChooser(shareIntent, "Share your quiz results")

        // Check if there are apps that can handle this intent
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        }
    }

    private fun buildShareText(): String {
        val performanceEmoji = when {
            percentage >= 90 -> "🏆"
            percentage >= 70 -> "🎉"
            percentage >= 50 -> "👍"
            else -> "💪"
        }

        val performanceText = when {
            percentage >= 90 -> "Excellent!"
            percentage >= 70 -> "Great job!"
            percentage >= 50 -> "Good effort!"
            else -> "Keep practicing!"
        }

        val minutes = timeTakenSeconds / 60
        val seconds = timeTakenSeconds % 60
        val timeText = String.format("%d:%02d", minutes, seconds)

        return """
            $performanceEmoji Quiz Results - $categoryName $performanceEmoji
            
            📊 Score: $percentage%
            ✅ Correct: $correctAnswers/$totalQuestions
            ❌ Incorrect: ${totalQuestions - correctAnswers}/$totalQuestions
            ⏱️ Time: $timeText
            
            $performanceText
            
            #QuizMaster #$categoryName #Quiz
        """.trimIndent()
    }
}