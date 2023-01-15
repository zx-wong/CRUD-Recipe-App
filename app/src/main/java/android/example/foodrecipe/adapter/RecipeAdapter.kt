package android.example.foodrecipe.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.example.foodrecipe.activities.UpdateActivity
import android.example.foodrecipe.database.RecipeEntity
import android.example.foodrecipe.databinding.RecipeItemBinding
import android.example.foodrecipe.utils.Constants.RECIPE_ID_BUNDLE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){
    private lateinit var binding: RecipeItemBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RecipeItemBinding.inflate(inflater, parent, false)
        context = parent.context

        return ViewHolder()
    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: RecipeEntity) {
            //InitView
            binding.apply {
                //Set text
                tvName.text = item.recipeName
                tvCategories.text = item.recipeTypes

                root.setOnClickListener {
                    val intent = Intent(context, UpdateActivity::class.java)
                    intent.putExtra(RECIPE_ID_BUNDLE, item.recipeId)
                    context.startActivity(intent)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<RecipeEntity>() {
        override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
            return oldItem.recipeId == newItem.recipeId
        }

        override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}