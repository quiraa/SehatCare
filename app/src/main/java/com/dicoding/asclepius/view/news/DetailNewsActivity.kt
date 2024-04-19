package com.dicoding.asclepius.view.news

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.dicoding.asclepius.R
import com.dicoding.asclepius.core.data.source.remote.response.Article
import com.dicoding.asclepius.databinding.ActivityDetailNewsBinding
import com.dicoding.asclepius.utils.formatDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailNewsActivity : AppCompatActivity(R.layout.activity_detail_news) {
	private val binding by viewBinding(ActivityDetailNewsBinding::bind)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(binding.newsToolbar)
		binding.newsToolbar.setNavigationOnClickListener {
			finish()
		}

		val data = intent.getParcelableExtra<Article>(EXTRA_NEWS_DATA)

		if(data != null) {
			binding.apply {
				newsImage.load(data.urlToImage)
				newsTextTitle.text = data.title
				newsTextAuthor.text = data.author
				newsTextDate.text = formatDate(data.publishedAt ?: "")
				newsTextContent.text = data.content
				newsTextDescription.text = data.description
			}
		}

		binding.readMoreBtn.setOnClickListener {
			val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data?.url ?: ""))
			startActivity(browserIntent)
		}
	}

	companion object {
		const val EXTRA_NEWS_DATA = "extra_news_data"
	}
}