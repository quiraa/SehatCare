package com.dicoding.asclepius.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dicoding.asclepius.core.data.source.remote.response.Article
import com.dicoding.asclepius.databinding.ItemNewsBinding
import com.dicoding.asclepius.utils.formatDate

class NewsAdapter(private val news : List<Article>, private val onNewsClick: ((article: Article) -> Unit)?) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

	class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(article: Article) {
			binding.apply {
				itemNewsDescription.text = article.description
				itemNewsTitle.text = article.title
				itemNewsImage.load(article.urlToImage)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
		val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return NewsViewHolder(view)
	}

	override fun getItemCount(): Int = news.size

	override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
		val data = news[position]
		holder.bind(data)
		holder.binding.root.setOnClickListener {
			onNewsClick?.invoke(data)
		}
	}
}