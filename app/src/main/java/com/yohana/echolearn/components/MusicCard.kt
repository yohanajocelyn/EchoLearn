package com.yohana.echolearn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.yohana.echolearn.R
import com.yohana.echolearn.models.SongModel

@Composable
fun MusicCard(
    song: SongModel,
    index: Int,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE4E4E4)
        ),
        elevation = CardDefaults.cardElevation(3.dp),
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$index", fontSize = 18.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    painter = rememberImagePainter(song.image),
                    contentDescription = "search icon",
                    modifier = Modifier
                        .size(42.dp),
                    contentScale = ContentScale.FillBounds

                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = song.title, fontSize = 16.sp, color = Color.Black)
                    Text(text = song.artist, fontSize = 12.sp, color = Color.Black)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "search icon",
                modifier = Modifier
                    .size(25.dp),
                contentScale = ContentScale.FillBounds

            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMusicCard() {
//    MusicCard()
}