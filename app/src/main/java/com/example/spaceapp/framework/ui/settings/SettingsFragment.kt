package com.example.spaceapp.framework.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spaceapp.R
import com.example.spaceapp.databinding.MainFragmentBinding
import com.example.spaceapp.databinding.SettingsFragmentBinding
import com.example.spaceapp.model.entities.AppThemes

class SettingsFragment: Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private var currentTheme: String = ""


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
        initDataTheme()
        initThemeChips()
    }

    private fun initDataTheme() {
        activity?.let {
            currentTheme = it.getPreferences(Context.MODE_PRIVATE).getString(APPTHEME,AppThemes.EUROPA.theme).toString()
        }

    }

    private fun initThemeChips() {
        with(binding){
            chipThemeEuropa.setOnClickListener {
                currentTheme = AppThemes.EUROPA.theme
                safeData()
                activity?.recreate()
            }
            chipThemeMars.setOnClickListener {
                currentTheme = AppThemes.MARS.theme
                safeData()
                activity?.recreate()
            }
            chipThemeUranus.setOnClickListener {
                currentTheme = AppThemes.URANUS.theme
                safeData()
                activity?.recreate()
            }
        }
    }

    private fun safeData() {
        activity?.let {
            val preferences = it.getPreferences(Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(APPTHEME, currentTheme)
            editor.apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val APPTHEME = "APPTHEME"

        fun newInstance() = SettingsFragment()
    }


}