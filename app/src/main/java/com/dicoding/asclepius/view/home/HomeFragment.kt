package com.dicoding.asclepius.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.core.ResultState
import com.dicoding.asclepius.core.data.source.remote.response.Article
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.view.news.DetailNewsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
	private val viewModel by viewModels<HomeViewModel>()

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	override fun onStart() {
		super.onStart()
		viewModel.getTopHeadlines()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			viewModel.newsState.observe(viewLifecycleOwner) { state ->
				when (state) {
					is ResultState.Loading -> {
						showErrorView(isVisible = false)
						showLoading(true)
					}

					is ResultState.Error -> {
						showErrorView(message = state.errorMessage, isVisible = true)
						showLoading(false)
					}

					is ResultState.Success -> {
						showErrorView(isVisible = false)
						showLoading(false)
						with(binding) {
							rvNews.adapter = NewsAdapter(news = state.data.articles, onNewsClick = { article ->
								val intent = Intent(activity, DetailNewsActivity::class.java)
								intent.putExtra(DetailNewsActivity.EXTRA_NEWS_DATA, article)
								startActivity(intent)
							})
							rvNews.setHasFixedSize(false)
							rvNews.visibility = View.VISIBLE
							rvNews.layoutManager = LinearLayoutManager(requireContext())
						}
					}
				}
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun showErrorView(message: String? = "", isVisible: Boolean) {
		if (isVisible == true) {
			binding.viewError.root.visibility = View.VISIBLE
			binding.viewError.tvError.text = message
			binding.viewError.tryAgainBtn.setOnClickListener {
				viewModel.getTopHeadlines()
			}
		} else {
			binding.viewError.root.visibility = View.GONE
		}
	}

	private fun showToast(message: String) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
	}

	private fun showLoading(isLoading: Boolean) {
		if (isLoading) {
			binding.loadingIndicator.visibility = View.VISIBLE
		} else {
			binding.loadingIndicator.visibility = View.GONE
		}
	}
}