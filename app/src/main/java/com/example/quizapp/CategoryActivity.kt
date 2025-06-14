package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.adapters.CategoryAdapter
import com.example.quizapp.data.QuizRepository
import com.example.quizapp.databinding.ActivityCategoryBinding
import com.example.quizapp.models.Category

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var quizRepository: QuizRepository
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizRepository = QuizRepository(this)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        // Set up RecyclerView
        categoryAdapter = CategoryAdapter(quizRepository.getCategories()) { category ->
            onCategorySelected(category)
        }

        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity)
            adapter = categoryAdapter
        }
    }

    private fun onCategorySelected(category: Category) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QuizActivity.EXTRA_CATEGORY_ID, category.id)
            putExtra(QuizActivity.EXTRA_CATEGORY_NAME, category.name)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}