package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yohana.echolearn.R
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.view.GenreCard
import com.yohana.echolearn.view.MusicCard
import com.yohana.echolearn.view.Navbar
import com.yohana.echolearn.view.SearchBar
import com.yohana.echolearn.viewmodels.ListMusicViewModel

@Composable
fun ListMusic(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ListMusicViewModel,
    type: String
) {

    val songs by viewModel.songs.collectAsState()
    LaunchedEffect(Unit) { viewModel.setSongs() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Top bar
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
                contentDescription = "",
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "List Music + $type",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 15.dp),
                color = Color(0xFFF6F6F6)
            )
        }
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
                    text = "lIST Musics",
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 31.9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF000000),

                        )
                )
                Spacer(modifier = Modifier.height(10.dp))

            }
//            hilangin dulu nti dibuka lg pas bisa passing song ke MusicCard
            itemsIndexed(songs) { index, song ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)) {
                    MusicCard(
                        song = song,
                        index = index + 1,
                        onCardClick = {
                            if (type == "Listening") {
                                navController?.navigate(route = PagesEnum.Listening)
                            } else {
                                navController?.navigate(route = PagesEnum.Speaking)
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
                        modifier = Modifier.padding(bottom = 16.dp)
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
                                    val currentNav =
                                        navController.currentBackStackEntry?.destination?.route
                                    navController.navigate(currentNav!!.substringAfterLast("/") + "/${PagesEnum.SongDetail.name}/Pop")
                                }// Ungu
                            )
                            GenreCard(
                                title = "Acoustic/Folk",
                                backgroundColor = Color(0xFF678026),
                                onClick = {
                                    val currentNav =
                                        navController.currentBackStackEntry?.destination?.route
                                    navController.navigate(currentNav!!.substringAfterLast("/") + "/${PagesEnum.SongDetail.name}/Acoustic")
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
                                    val currentNav =
                                        navController.currentBackStackEntry?.destination?.route
                                    navController.navigate(currentNav!!.substringAfterLast("/") + "/${PagesEnum.SongDetail.name}/Country")
                                }// Oranye
                            )
                            GenreCard(
                                title = "R&B/Soul",
                                backgroundColor = Color(0xFF3371E4),
                                onClick = {
                                    val currentNav =
                                        navController.currentBackStackEntry?.destination?.route
                                    navController.navigate(currentNav!!.substringAfterLast("/") + "/${PagesEnum.SongDetail.name}/R&B")
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

        // Navbar fixed at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF3DB2FF))
        ) {
            Navbar() // Tambahkan konten navbar di sini
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
//    ListMusic()
}
