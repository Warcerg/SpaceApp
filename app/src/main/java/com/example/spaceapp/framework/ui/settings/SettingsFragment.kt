package com.example.spaceapp.framework.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.spaceapp.databinding.SettingsFragmentBinding
import com.example.spaceapp.model.entities.AppThemes

class SettingsFragment: Fragment() {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!
    private var currentTheme: String = ""

    private var cardVisible = true
    private var filledBoxTextVisible = true


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
        initMotions()
    }

    private fun initMotions() {
        with(binding){
            outlinedButton.setOnClickListener {
                TransitionManager.beginDelayedTransition(constraintLayout, Slide(Gravity.END))
                cardVisible = !cardVisible
                card.visibility = if (cardVisible) View.VISIBLE else View.GONE
            }

            raisedButton.setOnClickListener {
                val fade = Fade().addTarget(filledBoxTextField)
                fade.duration = 2000
                val setTransition = TransitionSet().addTransition(fade)
                TransitionManager.beginDelayedTransition(constraintLayout, setTransition)
                filledBoxTextVisible = !filledBoxTextVisible
                filledBoxTextField.visibility = if (filledBoxTextVisible) View.VISIBLE else View.GONE

            }



        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val APPTHEME = "APPTHEME"

        fun newInstance() = SettingsFragment()
    }


}