package com.yohana.echolearn.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yohana.echolearn.components.GenreCard
import com.yohana.echolearn.components.MusicCard
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.SearchBar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.ListMusicViewModel

@Composable
fun ListMusic(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ListMusicViewModel,
    type: String,
    token: String,
) {
    val songs by viewModel.songs.collectAsState()
    LaunchedEffect(Unit) { viewModel.setSongs() }
    Scaffold(
        topBar = {
            TopBarComponent(
                pageTitle = "List Music",
                onBackClick = { navController.popBackStack() }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                // Content list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        SearchBar()
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    item {
                        Text(
                            text = "Top Musics",
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 31.9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF000000),

                                )
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                    itemsIndexed(songs) { index, song ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            MusicCard(
                                song = song,
                                index = index + 1,
                                onCardClick = {
                                    if (type == "Listening") {
                                        navController?.navigate(route = PagesEnum.Listening.name + "/$song.id")
                                    } else {
                                        navController?.navigate(PagesEnum.Speaking.name + "/${song.id}")
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))

                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            // Header Title
                            Text(
                                text = "Genre",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp),
                                color = Color.Black,
                            )

                            // Genre Grid
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    GenreCard(
                                        title = "Pop",
                                        backgroundColor = Color(0xFF9854B2),
                                        onClick = {
                                            navController.navigate("${type}/${PagesEnum.SongDetail.name}/Pop")
                                        }// Ungu
                                    )
                                    GenreCard(
                                        title = "Acoustic/Folk",
                                        backgroundColor = Color(0xFF678026),
                                        onClick = {
                                            navController.navigate("${type}/${PagesEnum.SongDetail.name}/Acoustic")
                                        }
                                        // Hijau
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    GenreCard(
                                        title = "Country",
                                        backgroundColor = Color(0xFFCF4321),
                                        onClick = {
                                            navController.navigate("${type}/${PagesEnum.SongDetail.name}/Country")
                                        }// Oranye
                                    )
                                    GenreCard(
                                        title = "R&B/Soul",
                                        backgroundColor = Color(0xFF3371E4),
                                        onClick = {
                                            navController.navigate("${type}/${PagesEnum.SongDetail.name}/R&B")
                                        }// Biru
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    GenreCard(
                                        title = "Children's Songs",
                                        backgroundColor = Color(0xFF0068AD) // Biru muda
                                    )
                                    GenreCard(
                                        title = "Acoustic/Folk",
                                        backgroundColor = Color(0xFF223160) // Biru tua
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Navbar(
                navController = navController
            )
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
//    ListMusic()
}
