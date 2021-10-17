package com.example.spaceapp.framework.ui.main

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import com.example.spaceapp.databinding.MainFragmentStartBinding
import com.example.spaceapp.getDayBeforeYesterdayDateTime
import com.example.spaceapp.getYesterdayDateTime
import com.example.spaceapp.model.AppState
import com.example.spaceapp.toString
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment: Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private var _binding: MainFragmentStartBinding? = null
    private val binding get() = _binding!!

    private var isExpanded = false

    private lateinit var yesterdayDate : String
    private lateinit var dayBeforeYesterdayDate : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDate()
        initImageViewExpander()
        with(binding){
            inputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
                })
            }
            viewModel.getLiveData().observe(viewLifecycleOwner, { loadData(it)})
            viewModel.getPODData()
        }
        initChips()
    }

    private fun initDate() {
        yesterdayDate = getYesterdayDateTime().toString("yyyy-MM-dd")
        dayBeforeYesterdayDate = getDayBeforeYesterdayDateTime().toString("yyyy-MM-dd")
    }

    private fun initImageViewExpander() {
        with(binding) {
            imagePod.setOnClickListener {
                isExpanded = !isExpanded

                val set = TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())

                TransitionManager.beginDelayedTransition(main, set)
                imagePod.scaleType = if (isExpanded) {
                    ImageView.ScaleType.CENTER_CROP
                } else {
                    ImageView.ScaleType.FIT_CENTER
                }
            }
        }
    }

    private fun initChips() {
        with(binding){


            todayChip.setOnClickListener {
                viewModel.getPODData()

            }
            yesterdayChip.setOnClickListener {
                viewModel.getSpecificPODData(yesterdayDate)
            }
            dayBeforeYesterdayChip.setOnClickListener {
                viewModel.getSpecificPODData(dayBeforeYesterdayDate)
            }
        }
    }

    private fun loadData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Loading -> {
                Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            }
            is AppState.SuccessPOD -> {
                imagePod.load(appState.pictureOfTheDayData.url)
                val spannable = SpannableString(appState.pictureOfTheDayData.explanation)
                spannable.setSpan(ForegroundColorSpan(Color.RED),0,spannable.indexOf(" "),
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
              /*  spannable.setSpan()*/
                podDescription.text = spannable
            }
            is AppState.Error -> {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}