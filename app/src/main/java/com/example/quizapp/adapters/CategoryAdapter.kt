package com.example.quizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.databinding.ItemCategoryBinding
import com.example.quizapp.models.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
        
        // Apply animation
        val animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.item_animation_from_right
        )
        animation.startOffset = position * 100L
        holder.itemView.startAnimation(animation)
    }
    
    override fun getItemCount() = categories.size
    
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(category: Category) {
            with(binding) {
                tvCategoryName.text = category.name
                tvQuestionCount.text = "${category.questionCount} questions"
                ivCategoryIcon.setImageResource(category.icon)
                
                cardCategory.setOnClickListener {
                    // Apply ripple effect
                    it.isPressed = true
                    it.postDelayed({ it.isPressed = false }, 100)
                    
                    // Trigger click
                    onItemClick(category)
                }
            }
        }
    }
}