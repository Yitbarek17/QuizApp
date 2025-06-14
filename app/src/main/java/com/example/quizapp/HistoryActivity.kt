package com.example.quizapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.adapters.HistoryAdapter
import com.example.quizapp.data.QuizRepository
import com.example.quizapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var quizRepository: QuizRepository
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizRepository = QuizRepository(this)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        setupRecyclerView()
        loadHistory()
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(emptyList())
        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyAdapter
        }
    }

    private fun loadHistory() {
        val results = quizRepository.getQuizResults()

        if (results.isEmpty()) {
            binding.tvEmptyState.visibility = android.view.View.VISIBLE
            binding.recyclerViewHistory.visibility = android.view.View.GONE
        } else {
            binding.tvEmptyState.visibility = android.view.View.GONE
            binding.recyclerViewHistory.visibility = android.view.View.VISIBLE
            historyAdapter.updateResults(results)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}