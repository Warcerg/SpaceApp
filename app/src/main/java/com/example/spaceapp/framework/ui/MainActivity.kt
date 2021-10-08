package com.example.spaceapp.framework.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.spaceapp.R
import com.example.spaceapp.framework.ui.earth.EarthFragment
import com.example.spaceapp.framework.ui.main.MainFragment
import com.example.spaceapp.framework.ui.mars.MarsFragment
import com.example.spaceapp.framework.ui.settings.SettingsFragment
import com.example.spaceapp.model.entities.AppThemes
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var currentTheme = AppThemes.EUROPA.theme
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        loadThemeData()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        initBottomNavView()
    }

    private fun initBottomNavView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_apod -> {
                    openFragment(MainFragment.newInstance())
                    true
                }
                R.id.bottom_view_earth -> {
                    openFragment(EarthFragment.newInstance())
                    true
                }
                R.id.bottom_view_mars -> {
                    openFragment(MarsFragment.newInstance())
                    true
                }
                R.id.bottom_view_settings -> {
                    openFragment(SettingsFragment.newInstance())
                    true
                }
                else -> false
            }

        }
        bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_apod -> {

                }
                R.id.bottom_view_earth -> {

                }
                R.id.bottom_view_mars -> {

                }
                R.id.bottom_view_settings -> {

                }
            }

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

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            supportFragmentManager.findFragmentById(R.id.container)?.let {
                beginTransaction()
                    .add(R.id.container, fragment)
                    .addToBackStack("")
                    .remove(it)
                    .commitAllowingStateLoss()
            }
        }
    }
}