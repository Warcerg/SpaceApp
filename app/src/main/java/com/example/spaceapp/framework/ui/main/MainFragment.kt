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
import com.example.spaceapp.databinding.MainFragmentBinding
import com.example.spaceapp.model.AppState
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.text.method.ScrollingMovementMethod
import com.example.spaceapp.databinding.SettingsFragmentBinding
import com.example.spaceapp.framework.ui.settings.SettingsFragment


class MainFragment: Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun loadData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Loading -> {
                Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
            }
            is AppState.Success -> {
                imagePod.load(appState.pictureOfTheDayData.url)
                podDescription.text = appState.pictureOfTheDayData.explanation
                podDescription.movementMethod = ScrollingMovementMethod()
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