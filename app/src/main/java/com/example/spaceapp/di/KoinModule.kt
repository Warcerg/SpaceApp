package com.example.spaceapp.di

import com.example.spaceapp.framework.ui.main.MainViewModel
import com.example.spaceapp.model.repository.Repository
import com.example.spaceapp.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl()}

    //ViewModels
    viewModel { MainViewModel(get()) }
}