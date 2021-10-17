package com.example.spaceapp.framework.ui.mars

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.example.spaceapp.R
import com.example.spaceapp.databinding.MarsFragmentBinding
import com.example.spaceapp.getCurrentDateTime
import com.example.spaceapp.model.AppState
import com.example.spaceapp.toString
import org.koin.androidx.viewmodel.ext.android.viewModel

class MarsFragment: Fragment() {
    private val viewModel: MarsViewModel by viewModel()

    private var _binding: MarsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateString : String
    private lateinit var dayString: String
    private var photoId = 0

    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MarsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initDate()
        initHeaderElevation()
        initImageViewExpander()
        viewModel.getLiveData().observe(viewLifecycleOwner, { loadData(it)})
        viewModel.getMarsPhotoData(dateString)
        activity?.let {
            with(binding){
                marsHeading.typeface = Typeface.createFromAsset(it.assets, "fonts/RedWorld.ttf")
                marsTextView.typeface = Typeface.createFromAsset(it.assets, "fonts/RedWorld.ttf")
                marsWikiText.typeface = Typeface.createFromAsset(it.assets, "fonts/RedWorld.ttf")
            }
        }

    }

    private fun initImageViewExpander() {
        with(binding) {
            imageMars.setOnClickListener {
                isExpanded = !isExpanded

                val set = TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())

                TransitionManager.beginDelayedTransition(scrollView, set)
                imageMars.scaleType = if (isExpanded) {
                    ImageView.ScaleType.CENTER_CROP
                } else {
                    ImageView.ScaleType.FIT_CENTER
                }
            }
        }
    }

    private fun initHeaderElevation() {
        with(binding){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    header.isSelected = scrollView.canScrollVertically(-1)
                }
            }
        }
    }

    private fun loadData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Loading -> {
                Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            }
            is AppState.SuccessMarsPhoto -> {
                val day = dayString.toInt()
                photoId = if (day < appState.marsPhotos.size){
                    day
                } else appState.marsPhotos.size - 1
                imageMars.load(appState.marsPhotos[photoId].url)
                marsTextView.text = getString(R.string.mars_photo_date).plus(" ").plus(appState.marsPhotos[photoId].earthDate)
            }
            is AppState.Error -> {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initDate() {
       val date = getCurrentDateTime()
        dateString = date.toString("yyyy-MM-01")
        dayString = date.toString("d")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}