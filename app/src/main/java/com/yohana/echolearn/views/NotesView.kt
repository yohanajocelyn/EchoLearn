package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yohana.echolearn.R
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.NoteViewModel

@Composable
fun NotesView(
    navController: NavController,
    token: String,
    viewModel: NoteViewModel = viewModel(),
    username: String
) {
    val notes by viewModel.notes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNotes(token, username)
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                pageTitle = "Notes",
                onBackClick = { navController.popBackStack() }
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    item {
                        Button(onClick = {
                            navController.navigate(PagesEnum.CreateNote.name)
                        }) {
                            Text("Create Note", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                    }

                    itemsIndexed(notes) { index, note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .border(
                                    width = 1.dp, // Ketebalan border
                                    color = Color.LightGray, // Warna border
                                    shape = RoundedCornerShape(8.dp) // Bentuk border
                                ),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F6F6))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column {
                                    Text(
                                        "${note.word}", fontSize = 32.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF000000),
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        "${note.meaning}", fontSize = 16.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF000000),

                                        )
                                }
                                Row {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_edit3),
                                        contentDescription = "profile picture",
                                        modifier = Modifier
                                            .size(32.dp),

                                        contentScale = ContentScale.Crop
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_trash),
                                        contentDescription = "profile picture",
                                        modifier = Modifier
                                            .size(32.dp).clickable {
                                                viewModel.deleteNote(
                                                    token = token,
                                                    id = note.id,
                                                    username = username
                                                )
                                            },

                                        contentScale = ContentScale.Crop
                                    )
                                }

                            }
                        }

                    }

                }
            }
        }, bottomBar = {
            Navbar(
                navController = navController
            )
        }


    )
}



