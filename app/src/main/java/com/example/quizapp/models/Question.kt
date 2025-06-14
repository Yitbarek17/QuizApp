package com.example.quizapp.models

data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val category: String,
    val explanation: String = ""
)