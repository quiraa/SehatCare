package com.dicoding.asclepius.view.history

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dicoding.asclepius.core.data.source.local.entity.PredictionResult
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter(
	private val histories: List<PredictionResult>,
	private val onRemove: ((prediction: PredictionResult) -> Unit)?,
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {




	class HistoryViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(prediction: PredictionResult) {
			binding.apply {
				itemHistoryImage.setImageURI(Uri.parse(prediction.image))
				itemHistoryTitle.text = prediction.result
				itemHistorySubtitle.text = prediction.predictionPercentage
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
		val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return HistoryViewHolder(view)
	}

	override fun getItemCount(): Int = histories.size

	override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
		val data = histories[position]
		holder.bind(data)
		holder.binding.itemHistoryBtnDelete.setOnClickListener {
			onRemove?.invoke(data)
		}
	}
}