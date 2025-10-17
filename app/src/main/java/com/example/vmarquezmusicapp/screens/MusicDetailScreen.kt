package com.example.vmarquezmusicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vmarquezmusicapp.Components.DetalleCard
import com.example.vmarquezmusicapp.Components.MusicCard
import com.example.vmarquezmusicapp.Components.MusicCard2
import com.example.vmarquezmusicapp.Models.Music
import com.example.vmarquezmusicapp.Services.MusicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun MusicDetailScreen(
    id: String,
    navController: NavController
) {
    var music by remember {
        mutableStateOf<Music?>(null)
    }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        try {
            val retrofit = Retrofit
                .Builder()
                .baseUrl("https://music.juanfrausto.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(MusicService::class.java)
            val result = withContext(Dispatchers.IO) {
                service.getMusicById(id)
            }
            music = result
            Log.i("MusicDetailScreen", music.toString())
        } catch (e: Exception) {
            Log.e("MusicDetailScreen", e.toString())
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

        music == null -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color(0xFFEDE7F6)),
                contentAlignment = Alignment.Center
            ) {
                Text("Error al cargar el álbum")
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        DetalleCard(
                            music = music!!,
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 24.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "About this album",
                                        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = music!!.description ?: "Un álbum sinfónico que mezcla elementos de música clásica con death metal melódico.",
                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Segundo Box - Información del artista
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Artist: ${music!!.artist}",
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(horizontal = 16.dp)
                                .background(Color.Gray.copy(alpha = 0.3f))
                        )
                    }

                    items(10) { index ->
                        MusicCard(
                            music = Music(
                                id = "${music!!.id}-$index",
                                title = "${music!!.title} • Track ${index + 1}",
                                artist = music!!.artist,
                                description = "Track ${index + 1} del álbum ${music!!.title}",
                                image = music!!.image
                            ),
                            onClick = {
                                navController.navigate("musicDetail/${music!!.id}-$index")
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
                if (music != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                    ) {
                        MusicCard2(
                            music = music!!,
                            onClick = {
                                navController.navigate("musicDetail/${music!!.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}