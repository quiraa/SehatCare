package com.dicoding.asclepius.view.result

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dicoding.asclepius.R
import com.dicoding.asclepius.core.data.source.local.entity.PredictionResult
import com.dicoding.asclepius.core.helper.ImageClassifierHelper
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat


@AndroidEntryPoint
class ResultActivity : AppCompatActivity(R.layout.activity_result) {
	private val binding by viewBinding(ActivityResultBinding::bind)
	private val viewModel by viewModels<HistoryViewModel>()

	private lateinit var imageClassifierHelper: ImageClassifierHelper
	private lateinit var label: String
	private lateinit var score: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(binding.resultToolbar)
		binding.resultToolbar.setNavigationOnClickListener {
			finish()
		}

		// TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
		val image = intent.getStringExtra(EXTRA_RESULT_IMAGE)!!

		val imageUri = Uri.parse(image)
		imageUri.let {
			Log.d("ResultActivity", "Image URI : $it")
			binding.resultImage.setImageURI(it)
		}

		classify(imageUri)

		binding.fabSave.setOnClickListener {
			viewModel.insertHistory(PredictionResult(
				id = null,
				result = label,
				predictionPercentage = score,
				image = imageUri.toString()
			))
			finish()
			showToast("Result Saved to History")
		}
	}

	private fun classify(imageUri: Uri) {
		imageClassifierHelper = ImageClassifierHelper(
			context = this,
			classifierListener = object : ImageClassifierHelper.ClassifierListener {
				override fun onError(error: String) {
					runOnUiThread {
						Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
					}
				}

				override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
					runOnUiThread {
						results?.let { it ->
							if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {

								// Menemukan 'category' dengan skor tertinggi menggunakan maxBy
								val highestScoringCategory =
									it[0].categories.maxByOrNull { it.score }

								if (highestScoringCategory != null) {
									val displayResult = "${highestScoringCategory.label} " +
													NumberFormat.getPercentInstance()
														.format(highestScoringCategory.score).trim()


									label = highestScoringCategory.label
									score = NumberFormat.getPercentInstance()
										.format(highestScoringCategory.score).trim()

									binding.resultLabel.text = label
									binding.resultScore.text = score
								} else {
									binding.resultLabel.text =
										"No category with high enough score found."
									binding.resultScore.text = ""
								}
							} else {
								binding.resultLabel.text = ""
								binding.resultScore.text = ""
							}
						}
					}
				}
			}
		)

		imageClassifierHelper.classifyStaticImage(imageUri)
	}

	companion object {
		const val EXTRA_RESULT_IMAGE = "extra_result_image"
	}

	private fun showToast(message: String?) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}
}