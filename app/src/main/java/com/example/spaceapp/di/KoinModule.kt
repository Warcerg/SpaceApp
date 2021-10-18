package com.example.spaceapp.di

import com.example.spaceapp.framework.ui.earth.EarthViewModel
import com.example.spaceapp.framework.ui.main.MainViewModel
import com.example.spaceapp.framework.ui.mars.MarsViewModel
import com.example.spaceapp.framework.ui.notes.NotesViewModel
import com.example.spaceapp.model.repository.Repository
import com.example.spaceapp.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl()}

    //ViewModels
    viewModel { MainViewModel(get()) }
    viewModel { MarsViewModel(get()) }
    viewModel { EarthViewModel(get()) }
    viewModel { NotesViewModel(get()) }
}