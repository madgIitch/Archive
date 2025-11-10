package com.example.archive


import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.archive.navigation.AppNavGraph

@Composable
fun ArcheApp() {
    ArcheTheme {
        Surface(color = colorScheme.background) {
            val navController = rememberNavController()
            AppNavGraph(navController = navController)
        }
    }
}
