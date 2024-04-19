package com.dicoding.asclepius.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.core.ResultState
import com.dicoding.asclepius.core.data.source.local.entity.PredictionResult
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

	private val viewModel by viewModels<HistoryViewModel>()

	private var _binding: FragmentHistoryBinding? = null
	private val binding get() = _binding!!

	override fun onStart() {
		super.onStart()
		viewModel.getAllHistory()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (activity != null) {
			viewModel.allHistory.observe(viewLifecycleOwner) { state ->
				when (state) {
					is ResultState.Loading -> {
						showLoading(true)
						showEmptyView(isVisible = false)
					}

					is ResultState.Error -> {
						showLoading(false)
						showEmptyView(message = state.errorMessage, isVisible = true)
					}

					is ResultState.Success -> {
						showLoading(false)
						if(state.data.isNotEmpty()) {
							val historyAdapter = HistoryAdapter(state.data, onRemove = { prediction ->
								viewModel.deleteHistory(prediction)
							})
							binding.rvHistory.adapter = historyAdapter
							binding.rvHistory.setHasFixedSize(false)
							binding.rvHistory.visibility = View.VISIBLE
							binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
						} else {
							binding.rvHistory.visibility = View.GONE
							showEmptyView(message = "Empty Data", isVisible = true)
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
		_binding = FragmentHistoryBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun showEmptyView(message: String? = "", isVisible: Boolean) {
		if(isVisible == true) {
			binding.viewEmpty.root.visibility = View.VISIBLE
			binding.viewEmpty.tvEmpty.text = message
		} else {
			binding.viewEmpty.root.visibility = View.GONE
		}
	}

	private fun showToast(message: String) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
	}

	private fun showLoading(isLoading: Boolean) {
		if (isLoading) {
			binding.historyLoadingIndicator.visibility = View.VISIBLE
		} else {
			binding.historyLoadingIndicator.visibility = View.GONE
		}
	}

}