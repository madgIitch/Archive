package com.example.archive.di

import androidx.room.Room
import com.example.archive.data.local.AppDatabase
import com.example.archive.data.repository.ItemRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.archive.ui.wardrobe.WardrobeViewModel
import com.example.archive.ui.outfits.OutfitsViewModel
import com.example.archive.ui.planner.PlannerViewModel

val appModule = module {
    // Base de datos Room
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "archive_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAOs
    single { get<AppDatabase>().itemDao() }
    single { get<AppDatabase>().outfitDao() }
    single { get<AppDatabase>().plannerDao() }

    // Repository
    single { ItemRepository(get()) }

    // ViewModels
    viewModel { WardrobeViewModel(get<ItemRepository>()) }
    viewModel { OutfitsViewModel(get(), get()) }
    viewModel { PlannerViewModel(get(), get()) }
}