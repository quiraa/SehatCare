package com.dicoding.asclepius.view.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.FragmentScanBinding
import com.dicoding.asclepius.view.result.ResultActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScanFragment : Fragment(R.layout.fragment_scan) {

	private var _binding: FragmentScanBinding? = null
	private val binding get() = _binding!!

	private var currentImageUri: Uri? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)


		binding.pickImageBtn.setOnClickListener {
			startCrop()
		}

		binding.analyzeBtn.setOnClickListener {
			currentImageUri?.let {
				analyzeImage()
			} ?: run {
				showToast("Please Select Image")
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentScanBinding.inflate(inflater, container, false)
		return binding.root
	}

	private val cropImage = registerForActivityResult(CropImageContract()) { result ->
		if (result.isSuccessful) {
			currentImageUri = result.uriContent
			showImage()
		} else {
			val exception = result.error
			showToast("No Image Selected")
			Log.d("ScanFragment", "ERROR on CROP_IMAGE : $exception")
		}
	}

	private fun startCrop() {
		cropImage.launch(
			options {
				setGuidelines(CropImageView.Guidelines.ON_TOUCH)
				setImageSource(includeGallery = true, includeCamera = false)
			}
		)
	}

	private fun showImage() {
		currentImageUri?.let { uri ->
			binding.previewImageView.setImageURI(uri)
		}
	}

	private fun analyzeImage() {
		val intent = Intent(activity, ResultActivity::class.java)
		intent.putExtra(ResultActivity.EXTRA_RESULT_IMAGE, currentImageUri.toString())
		startActivity(intent)
	}

	private fun showToast(message: String) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
	}
}