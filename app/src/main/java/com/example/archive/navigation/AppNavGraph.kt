package com.example.archive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.archive.ui.wardrobe.WardrobeScreen
import com.example.archive.ui.outfits.OutfitsScreen
import com.example.archive.ui.planner.PlannerScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "wardrobe") {
        composable("wardrobe") { WardrobeScreen() }
        composable("outfits") { OutfitsScreen() }
        composable("planner") { PlannerScreen() }
    }
}