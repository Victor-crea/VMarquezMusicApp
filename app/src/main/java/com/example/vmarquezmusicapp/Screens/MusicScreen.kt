package com.example.vmarquezmusicapp.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vmarquezmusicapp.Components.HomeCard
import com.example.vmarquezmusicapp.Components.MusicAlbumCard
import com.example.vmarquezmusicapp.Components.MusicCard
import com.example.vmarquezmusicapp.Components.MusicCard2
import com.example.vmarquezmusicapp.Models.Music
import com.example.vmarquezmusicapp.Services.MusicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun MusicScreen(navController: NavController) {
    var music by remember { mutableStateOf(listOf<Music>()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            Log.i("HomeScreen", "Inicializando")
            val retrofit = Retrofit.Builder()
                .baseUrl("https://music.juanfrausto.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(MusicService::class.java)
            val result = async(Dispatchers.IO) {
                service.getAllMusic()
            }
            music = result.await()
        } catch (e: Exception) {
            Log.e("HomeScreen", e.toString())
        } finally {
            loading = false
        }
    }

    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        music.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontraron álbumes")
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .background(Color(0xFFEDE7F6))
                ) {
                    item {
                        HomeCard()
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Albums",
                                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "See more",
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF9055FF)
                            )
                        }
                    }

                    item {
                        androidx.compose.foundation.lazy.LazyRow(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(music) { album ->
                                Box(modifier = Modifier.width(160.dp)) {
                                    MusicAlbumCard(
                                        music = album,
                                        onClick = {
                                            navController.navigate("musicDetail/${album.id}")
                                        }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Recently Played",
                                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "See more",
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF9055FF)
                            )
                        }
                    }
                    items(music) { recentMusic ->
                        MusicCard(
                            music = recentMusic,
                            onClick = {
                                navController.navigate("musicDetail/${recentMusic.id}")
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }

                if (music.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                    ) {
                        MusicCard2(
                            music = music.first(),
                            onClick = {
                                navController.navigate("musicDetail/${music.first().id}")
                            }
                        )
                    }
                }
            }
        }
    }
}