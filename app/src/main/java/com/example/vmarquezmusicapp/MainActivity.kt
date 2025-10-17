package com.example.vmarquezmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vmarquezmusicapp.screens.MusicDetailScreen
import com.example.vmarquezmusicapp.screens.MusicScreen
import com.example.vmarquezmusicapp.ui.theme.VMarquezMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VMarquezMusicAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "musicScreen"
                    ) {
                        composable("musicScreen") {
                            MusicScreen(navController)
                        }

                        composable("musicDetail/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            MusicDetailScreen(
                                id = id,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MusicDetailScreenPreview() {
    VMarquezMusicAppTheme {
        val navController = rememberNavController()
        MusicDetailScreen(
            id = "682243ecf60db4caa642a48b",
            navController = navController
        )
    }
}