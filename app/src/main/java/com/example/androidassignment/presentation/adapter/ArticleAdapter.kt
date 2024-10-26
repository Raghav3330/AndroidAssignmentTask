package com.example.androidassignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.data.article.Article
import com.example.androidassignment.data.article.createdText
import com.example.androidassignment.databinding.ArticleViewholderBinding

/**
 * Adapter for an [Article] [List].
 */
class ArticleAdapter : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ArticleViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }

    /**
     * View Holder for a [Article] RecyclerView list item.
     */
    class ArticleViewHolder(
        private val binding: ArticleViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                binding.title.text = article.title
                binding.description.text = article.description
                binding.created.text = article.createdText
            }
        }
    }
}