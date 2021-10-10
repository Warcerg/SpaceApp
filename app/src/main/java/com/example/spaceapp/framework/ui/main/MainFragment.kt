package com.example.spaceapp.framework.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.spaceapp.R
import com.example.spaceapp.model.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.spaceapp.databinding.MainFragmentStartBinding


class MainFragment: Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private var _binding: MainFragmentStartBinding? = null
    private val binding get() = _binding!!

    private var isExpanded = false

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

    private fun loadData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Loading -> {
                Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            }
            is AppState.SuccessPOD -> {
                imagePod.load(appState.pictureOfTheDayData.url)
                podDescription.text = appState.pictureOfTheDayData.explanation
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