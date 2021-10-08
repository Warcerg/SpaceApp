package com.example.spaceapp.framework.ui.mars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        viewModel.getLiveData().observe(viewLifecycleOwner, { loadData(it)})
        viewModel.getMarsPhotoData(dateString)

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