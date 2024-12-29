package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yohana.echolearn.R
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.view.GenreCard
import com.yohana.echolearn.view.MusicCard
import com.yohana.echolearn.view.Navbar
import com.yohana.echolearn.viewmodels.GenreViewModel

@Composable
fun GenreView(
    genre: String,
    viewModel: GenreViewModel,
    navController: NavController? = null,
    type: String
) {
    LaunchedEffect(genre) {
        viewModel.setSongs(genre)
    }
    val songs by viewModel.songs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F6F6))
    ) {
        // Top bar
        TopBar()

        // Content list
        if (songs.isEmpty()){
            Text(
                text = "No songs found"
            )
        }else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(15.dp) // Jarak antar item
            ) {
                // Genre Section
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(color = Color(0xFF3DB2FF))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GenreCard("Pop", backgroundColor = Color.Blue)
                        Spacer(modifier = Modifier.width(25.dp))
                        Column {
                            Text(
                                text = "Genre",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(
                                text = "Pop",
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                itemsIndexed(songs) { index, song ->
                    Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)){
                        MusicCard(
                            song = song,
                            index = index+1,
                            onCardClick = {if (type == "Listening") {
                                navController?.navigate(route = PagesEnum.Listening.name+"/${song.id}")
                            } else {
                                navController?.navigate(route = PagesEnum.Speaking.name+"/${song.id}")
                            }}
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF3DB2FF))
        ) {
            Navbar() // Tambahkan konten navbar di sini
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(color = Color(0xFF3DB2FF))
            .padding(top = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_button),
            contentDescription = "Back Button"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "List Music",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF6F6F6),
            modifier = Modifier.padding(bottom = 15.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewGenreMusic() {
    GenreView(
        genre = "Pop",
        viewModel = viewModel(),
        type = "Listening"
    )
}
