package com.yohana.echolearn.view

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
import com.yohana.echolearn.R

@Composable
fun MusicCard(    onCardClick: () -> Unit

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
                Text(text = "1", fontSize = 18.sp, color = Color.Black)
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.aset1),
                    contentDescription = "search icon",
                    modifier = Modifier
                        .size(42.dp),
                    contentScale = ContentScale.FillBounds

                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = "Easy", fontSize = 18.sp, color = Color.Black)
                    Text(text = "Troye Sivan", fontSize = 18.sp, color = Color.Black)
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

