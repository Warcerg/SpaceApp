package com.example.spaceapp.framework.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spaceapp.R
import com.example.spaceapp.databinding.MainFragmentBinding
import com.example.spaceapp.databinding.SettingsFragmentBinding

class SettingsFragment: Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initThemeChips()
    }

    private fun initThemeChips() {
        with(binding){
            chipThemeEuropa.setOnClickListener { view->
                activity?.theme?.applyStyle(R.style.Theme_SpaceApp_Europa, true)
                activity?.recreate()
            }
            chipThemeMars.setOnClickListener { view->
                activity?.theme?.applyStyle(R.style.Theme_SpaceApp_Europa, true)
                activity?.recreate()
            }
            chipThemeUranus.setOnClickListener { view->
                activity?.theme?.applyStyle(R.style.Theme_SpaceApp_Europa, true)
                activity?.recreate()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}