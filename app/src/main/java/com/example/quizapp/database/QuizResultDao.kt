package com.example.quizapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.quizapp.models.QuizResult
import java.util.*

class QuizResultDao(context: Context) {

    private val dbHelper = QuizDatabase(context)

    fun insertQuizResult(result: QuizResult): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(QuizDatabase.COLUMN_CATEGORY_ID, result.categoryId)
            put(QuizDatabase.COLUMN_CATEGORY_NAME, result.categoryName)
            put(QuizDatabase.COLUMN_SCORE, result.score)
            put(QuizDatabase.COLUMN_TOTAL_QUESTIONS, result.totalQuestions)
            put(QuizDatabase.COLUMN_CORRECT_ANSWERS, result.correctAnswers)
            put(QuizDatabase.COLUMN_INCORRECT_ANSWERS, result.incorrectAnswers)
            put(QuizDatabase.COLUMN_TIME_TAKEN_SECONDS, result.timeTakenSeconds)
            put(QuizDatabase.COLUMN_DATE, result.date.time)
        }

        val id = db.insert(QuizDatabase.TABLE_QUIZ_RESULTS, null, values)
        db.close()
        return id
    }

    fun getAllQuizResults(): List<QuizResult> {
        val results = mutableListOf<QuizResult>()
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.query(
            QuizDatabase.TABLE_QUIZ_RESULTS,
            null,
            null,
            null,
            null,
            null,
            "${QuizDatabase.COLUMN_DATE} DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val result = QuizResult(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_ID)),
                    categoryId = cursor.getString(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CATEGORY_ID)),
                    categoryName = cursor.getString(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CATEGORY_NAME)),
                    score = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_SCORE)),
                    totalQuestions = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_TOTAL_QUESTIONS)),
                    correctAnswers = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CORRECT_ANSWERS)),
                    incorrectAnswers = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_INCORRECT_ANSWERS)),
                    timeTakenSeconds = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_TIME_TAKEN_SECONDS)),
                    date = Date(cursor.getLong(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_DATE)))
                )
                results.add(result)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return results
    }

    fun getQuizResultsByCategory(categoryId: String): List<QuizResult> {
        val results = mutableListOf<QuizResult>()
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.query(
            QuizDatabase.TABLE_QUIZ_RESULTS,
            null,
            "${QuizDatabase.COLUMN_CATEGORY_ID} = ?",
            arrayOf(categoryId),
            null,
            null,
            "${QuizDatabase.COLUMN_DATE} DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val result = QuizResult(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_ID)),
                    categoryId = cursor.getString(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CATEGORY_ID)),
                    categoryName = cursor.getString(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CATEGORY_NAME)),
                    score = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_SCORE)),
                    totalQuestions = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_TOTAL_QUESTIONS)),
                    correctAnswers = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CORRECT_ANSWERS)),
                    incorrectAnswers = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_INCORRECT_ANSWERS)),
                    timeTakenSeconds = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_TIME_TAKEN_SECONDS)),
                    date = Date(cursor.getLong(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_DATE)))
                )
                results.add(result)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return results
    }

    fun deleteQuizResult(id: Long): Int {
        val db = dbHelper.writableDatabase
        val deletedRows = db.delete(
            QuizDatabase.TABLE_QUIZ_RESULTS,
            "${QuizDatabase.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        db.close()
        return deletedRows
    }

    fun deleteAllQuizResults(): Int {
        val db = dbHelper.writableDatabase
        val deletedRows = db.delete(QuizDatabase.TABLE_QUIZ_RESULTS, null, null)
        db.close()
        return deletedRows
    }

    fun getQuizResultsCount(): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ${QuizDatabase.TABLE_QUIZ_RESULTS}", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    fun getBestScoreForCategory(categoryId: String): QuizResult? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            QuizDatabase.TABLE_QUIZ_RESULTS,
            null,
            "${QuizDatabase.COLUMN_CATEGORY_ID} = ?",
            arrayOf(categoryId),
            null,
            null,
            "${QuizDatabase.COLUMN_SCORE} DESC",
            "1"
        )

        var result: QuizResult? = null
        if (cursor.moveToFirst()) {
            result = QuizResult(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_ID)),
                categoryId = cursor.getString(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CATEGORY_ID)),
                categoryName = cursor.getString(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CATEGORY_NAME)),
                score = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_SCORE)),
                totalQuestions = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_TOTAL_QUESTIONS)),
                correctAnswers = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_CORRECT_ANSWERS)),
                incorrectAnswers = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_INCORRECT_ANSWERS)),
                timeTakenSeconds = cursor.getInt(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_TIME_TAKEN_SECONDS)),
                date = Date(cursor.getLong(cursor.getColumnIndexOrThrow(QuizDatabase.COLUMN_DATE)))
            )
        }

        cursor.close()
        db.close()
        return result
    }
}