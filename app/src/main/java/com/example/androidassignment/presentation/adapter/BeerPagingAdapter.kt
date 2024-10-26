package com.example.androidassignment.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidassignment.R
import com.example.androidassignment.data.beer.BeerDto
import com.example.androidassignment.databinding.ArticleViewholderBinding
import com.example.androidassignment.databinding.ItemBeerBinding
import com.example.androidassignment.presentation.adapter.ArticleAdapter.ArticleViewHolder

class BeerPagingAdapter : PagingDataAdapter<BeerDto, BeerPagingAdapter.QuoteViewHolder>(COMPARATOR) {

    class QuoteViewHolder(private val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.beerImageView
        val title = binding.beerNameTextView
        val description = binding.beerDescriptionTextView
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            Glide.with(holder.image)
                .load(item.image_url)
                .into(holder.image)

            holder.title.text = item.name
            holder.description.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(
            ItemBeerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<BeerDto>() {
            override fun areItemsTheSame(oldItem: BeerDto, newItem: BeerDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BeerDto, newItem: BeerDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}