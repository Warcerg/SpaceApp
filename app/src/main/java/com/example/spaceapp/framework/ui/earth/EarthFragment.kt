package com.example.spaceapp.framework.ui.earth

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
import com.example.spaceapp.databinding.EarthFragmentBinding
import com.example.spaceapp.getYesterdayDateTime
import com.example.spaceapp.model.AppState
import com.example.spaceapp.spanHighlightFirstWord
import com.example.spaceapp.toString
import org.koin.androidx.viewmodel.ext.android.viewModel

class EarthFragment: Fragment() {
    private val viewModel: EarthViewModel by viewModel()

    private var _binding: EarthFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateString : String

    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EarthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDate()
        initImageViewExpander()
        viewModel.getLiveData().observe(viewLifecycleOwner, { loadData(it)})
        viewModel.getEarthPhotoData(dateString)
    }

    private fun initImageViewExpander() {
        with(binding) {
            imageEarth.setOnClickListener {
                isExpanded = !isExpanded

                val set = TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())

                TransitionManager.beginDelayedTransition(constraintLayout, set)
                imageEarth.layoutParams.height = if (isExpanded) {
                    ViewGroup.LayoutParams.MATCH_PARENT
                } else {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                }
                imageEarth.scaleType = if (isExpanded) {
                    ImageView.ScaleType.CENTER_CROP
                } else {
                    ImageView.ScaleType.FIT_CENTER
                }
            }
        }
    }

    private fun loadData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Loading -> {
                Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            }
            is AppState.SuccessEarthPhoto -> {
                imageEarth.load(appState.earthPhoto.imageURL)

                val spannable = spanHighlightFirstWord(appState.earthPhoto.caption, ' ')
                earthPhotoDescription.text = spannable
            }
            is AppState.Error -> {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initDate() {
        val date = getYesterdayDateTime()
        dateString = date.toString("yyyy-MM-dd")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        fun newInstance() = EarthFragment()
    }
}