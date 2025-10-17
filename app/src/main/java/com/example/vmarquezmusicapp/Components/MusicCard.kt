package com.example.vmarquezmusicapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.vmarquezmusicapp.Models.Music
import com.example.vmarquezmusicapp.ui.theme.VMarquezMusicAppTheme

@Composable
fun MusicCard(
    music: Music,
    onClick: (Music) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable { onClick(music) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = music.image,
            contentDescription = music.title,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = music.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${music.artist} • Popular Song",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Options",
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MusicCardPreview() {
    val music = Music(
        id = "XD",
        title = "Tales of Ithiria",
        artist = "Haggard",
        description = "Popular Song",
        image = "https://th.bing.com/th/id/R.f3affce2a55fb1d78f3589f4dc6245dc?rik=1xZ%2bvGQMKn0vuQ&riu=http%3a%2f%2fwww.metalmusicarchives.com%2fimages%2fcovers%2fhaggard-tales-of-ithiria-20110823064946.jpg&ehk=F8xsRKeRxnyNr%2f%2bpf3PEQCWaJqXjFGWCTMVUJHqDOX4%3d&risl=&pid=ImgRaw&r=0"
    )
    VMarquezMusicAppTheme {
        MusicCard(music = music, onClick = {})
    }
}