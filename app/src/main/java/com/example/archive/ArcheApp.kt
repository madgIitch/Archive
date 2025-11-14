package com.example.archive

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.archive.navigation.AppNavGraph
import com.example.archive.ui.theme.ArchiveTheme

@Composable
fun ArcheApp() {
    ArchiveTheme {
        val navController = rememberNavController()
        AppNavGraph(navController = navController)
    }
}