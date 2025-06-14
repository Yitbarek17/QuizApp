package com.example.quizapp.models

import java.util.Date

data class QuizResult(
    val id: Long = System.currentTimeMillis(),
    val categoryId: String,
    val categoryName: String,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val timeTakenSeconds: Int,
    val date: Date = Date()
)