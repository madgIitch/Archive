package com.example.archive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arche.app.ui.wardrobe.WardrobeScreen
import com.arche.app.ui.outfits.OutfitsScreen
import com.arche.app.ui.planner.PlannerScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "wardrobe") {
        composable("wardrobe") { WardrobeScreen() }
        composable("outfits") { OutfitsScreen() }
        composable("planner") { PlannerScreen() }
    }
}