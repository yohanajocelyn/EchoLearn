package com.yohana.echolearn.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yohana.echolearn.components.MusicCard
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.SearchBar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.ListMusicViewModel

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    navController: NavController,
    token: String,
    type: String,
    viewModel: ListMusicViewModel
) {
    val searchSongs by viewModel.searchSongs.collectAsState()
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

                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)) {
                    item{
                        SearchBar(
                            listMusicViewModel = viewModel,
                            navController = navController,
                            token = token,
                            type = type
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    if (searchSongs.isNotEmpty()) {
                        itemsIndexed(searchSongs) { index, song ->
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
                                            navController?.navigate(route = PagesEnum.Listening.name + "/${song.id}")
                                        } else {
                                            navController?.navigate(PagesEnum.Speaking.name + "/${song.id}")
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    } else {
                        item {
                            Text("No songs found")
                        }
                    }
                }
            }
        },
        bottomBar = {
            Navbar(navController = navController)
        }
    )
}
