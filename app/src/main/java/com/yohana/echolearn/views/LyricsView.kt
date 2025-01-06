package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.ui.theme.poppins
import com.yohana.echolearn.viewmodels.ListeningViewModel
import com.yohana.echolearn.viewmodels.LyricsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LyricsView(
    songId: Int = 0,
    viewModel: LyricsViewModel,
    navController: NavController
){

    LaunchedEffect(songId) {
        viewModel.setSong(songId)
    }

    val song by viewModel.song.collectAsState()

    Scaffold(
        topBar = {
            TopBarComponent(
                pageTitle = "${song.title} Lyrics",
                onBackClick = { navController!!.navigate(PagesEnum.Home.name) }
            )
        },
        content = {
            paddingValues ->
            LazyColumn (
                modifier = Modifier
                    .padding(paddingValues)
                    .then(Modifier.padding(horizontal = 16.dp))
            ){
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp, bottom = 16.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(song.image),
                            contentDescription = "Album Cover",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    spotColor = Color.Black.copy(alpha = 0.75f),
                                    ambientColor = Color.Black.copy(alpha = 0.25f)
                                )
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surface)
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = song.title,
                                color = Color(0xFF005FB7),
                                fontFamily = poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = song.genre,
                                color = Color(0xFF005FB7),
                                fontFamily = poppins,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = song.artist,
                                color = Color(0xFF005FB7),
                                fontFamily = poppins,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(Modifier.weight(1f))
                            PlayPauseButton(
                                isPlaying = viewModel.isPlaying,
                                onToggle = {
                                    viewModel.updateIsPlaying()
                                }
                            )
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        viewModel.lines.forEach { line ->
                            FlowRow(
                                modifier = Modifier.padding(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                line.forEach { word ->
                                    Text(
                                        text = word,
                                        modifier = Modifier.padding(
                                            start = 2.dp,
                                            end = 2.dp,
                                            bottom = 10.dp
                                        ),
                                        fontSize = 16.sp,
                                        color = Color.Black
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