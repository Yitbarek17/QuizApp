package com.example.quizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ItemHistoryBinding
import com.example.quizapp.models.QuizResult
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private var results: List<QuizResult>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount() = results.size

    fun updateResults(newResults: List<QuizResult>) {
        results = newResults
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: QuizResult) {
            with(binding) {
                tvCategoryName.text = result.categoryName
                tvScore.text = "${(result.correctAnswers * 100) / result.totalQuestions}%"
                tvCorrectAnswers.text = "${result.correctAnswers}/${result.totalQuestions}"

                // Format time
                val minutes = result.timeTakenSeconds / 60
                val seconds = result.timeTakenSeconds % 60
                tvTimeTaken.text = String.format("%d:%02d", minutes, seconds)

                tvDate.text = dateFormat.format(result.date)
            }
        }
    }
}