package com.capstone.nutrilens.ui.recipe

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.nutrilens.data.response.RecipesItem
import com.capstone.nutrilens.databinding.CardRecipeBesarBinding

class RecipeBesarAdapter: ListAdapter<RecipesItem, RecipeBesarAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback:OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    class MyViewHolder ( val binding : CardRecipeBesarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe:RecipesItem){
            binding.tvNamaRecipeBesar.text = recipe.name
            binding.tvKaloriRecipeBesar.text = recipe.calories
            Glide.with(binding.ivRecipeBesar.context)
                .load("https://www.allrecipes.com/thmb/9aWCdbfttLcsW2dFQWwVQBGJM3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AR-236973-CreamyAlfredoSauce-0238-4x3-1-01e7091f47ae452d991abe32cbed5921.jpg")
                .into(binding.ivRecipeBesar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeBesarAdapter.MyViewHolder {
        val binding = CardRecipeBesarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeBesarAdapter.MyViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        holder.itemView.setOnClickListener{
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                holder.itemView.context as Activity,
                Pair(holder.binding.ivRecipeBesar,"gambar_recipe"),
                Pair(holder.binding.tvNamaRecipeBesar,"nama_recipe"),
                Pair(holder.binding.tvKaloriRecipeBesar,"kalori_recipe")
            )
            onItemClickCallback.onItemClicked(recipe,options)
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(recipe:RecipesItem, options: ActivityOptionsCompat)
    }

    companion object{
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<RecipesItem>(){
            override fun areItemsTheSame(oldItem: RecipesItem, newItem: RecipesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecipesItem, newItem: RecipesItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}