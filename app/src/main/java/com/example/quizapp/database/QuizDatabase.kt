package com.example.quizapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuizDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quiz_database.db"
        private const val DATABASE_VERSION = 1

        // Table name
        const val TABLE_QUIZ_RESULTS = "quiz_results"

        // Column names
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORY_ID = "category_id"
        const val COLUMN_CATEGORY_NAME = "category_name"
        const val COLUMN_SCORE = "score"
        const val COLUMN_TOTAL_QUESTIONS = "total_questions"
        const val COLUMN_CORRECT_ANSWERS = "correct_answers"
        const val COLUMN_INCORRECT_ANSWERS = "incorrect_answers"
        const val COLUMN_TIME_TAKEN_SECONDS = "time_taken_seconds"
        const val COLUMN_DATE = "date"

        // Create table SQL
        private const val CREATE_TABLE_QUIZ_RESULTS = """
            CREATE TABLE $TABLE_QUIZ_RESULTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY_ID TEXT NOT NULL,
                $COLUMN_CATEGORY_NAME TEXT NOT NULL,
                $COLUMN_SCORE INTEGER NOT NULL,
                $COLUMN_TOTAL_QUESTIONS INTEGER NOT NULL,
                $COLUMN_CORRECT_ANSWERS INTEGER NOT NULL,
                $COLUMN_INCORRECT_ANSWERS INTEGER NOT NULL,
                $COLUMN_TIME_TAKEN_SECONDS INTEGER NOT NULL,
                $COLUMN_DATE INTEGER NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_QUIZ_RESULTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUIZ_RESULTS")
        onCreate(db)
    }
}