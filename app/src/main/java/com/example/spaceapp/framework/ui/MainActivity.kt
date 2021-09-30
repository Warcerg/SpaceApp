package com.example.spaceapp.framework.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceapp.R
import com.example.spaceapp.framework.ui.main.MainFragment
import com.example.spaceapp.framework.ui.settings.SettingsFragment
import com.example.spaceapp.model.entities.AppThemes

class MainActivity : AppCompatActivity() {

    private var currentTheme = AppThemes.EUROPA.theme

    override fun onCreate(savedInstanceState: Bundle?) {
        loadThemeData()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun loadThemeData() {
        currentTheme =
            getPreferences(Context.MODE_PRIVATE)
                .getString(SettingsFragment.APPTHEME,AppThemes.EUROPA.theme)
                .toString()
        when (currentTheme) {
            AppThemes.MARS.theme -> {
                setTheme(R.style.Theme_SpaceApp_Mars)
            }
            AppThemes.URANUS.theme -> {
                setTheme(R.style.Theme_SpaceApp_Uranus)
            }
            else -> {
                setTheme(R.style.Theme_SpaceApp_Europa)
            }
        }
    }
}