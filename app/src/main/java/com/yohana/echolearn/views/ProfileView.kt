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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.yohana.echolearn.R
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.ProfileViewModel

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel(),
    token: String,
    username: String
) {
    LaunchedEffect(token) {
        viewModel.getUserByUsername(token, username)
    }
    val user by viewModel.user.collectAsState()
    Scaffold(
        topBar = {
            TopBarComponent(
                pageTitle = "Profile",
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
                if (viewModel.isLoading){
                    CircularProgressIndicator()
                }else {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 18.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = rememberImagePainter(user.profilePicture),
                                        contentDescription = "profile picture",
                                        modifier = Modifier
                                            .size(120.dp)
                                            .clip(CircleShape)
                                            .background(color = Color(0xFFE4E4E4)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column {
                                        Text(
                                            text = user.username,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = user.email,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight(400),
                                            color = Color.Gray
                                        )
                                        Text(
                                            if (user.totalScore >= 300) {
                                                "Master"
                                            } else if (user.totalScore >= 200) {
                                                "Intermediate"
                                            } else {
                                                "Beginner"
                                            },
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight(400),
                                            color = Color.Gray
                                        )
                                    }
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.ic_edit2),
                                    contentDescription = "profile picture",
                                    modifier = Modifier
                                        .size(32.dp).clickable {
                                            navController?.navigate(route = PagesEnum.UpdatedProfile.name + "/${user.id}")

                                        },

                                    contentScale = ContentScale.Crop
                                )

                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Divider(color = Color.LightGray, thickness = 1.dp)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(13.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Text("{${user.id}}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    "|",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color.LightGray
                                )
                                Text(
                                    "${user.totalScore} pts",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.app_logo_colorful),
                                    contentDescription = "profile picture",
                                    modifier = Modifier
                                        .size(250.dp),

                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp, // Ketebalan border
                                        color = Color.LightGray, // Warna border
                                        shape = RoundedCornerShape(8.dp) // Bentuk border
                                    ), colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF6F6F6) // Warna latar belakang kartu
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp, vertical = 20.dp)
                                ) {
                                    Text(
                                        text = "My Account",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF898A8D),
                                        textAlign = TextAlign.Center,
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "Switch to Another Account",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF3E5FAF), modifier = Modifier.clickable {
                                            navController.navigate(route = PagesEnum.Login.name)
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))

                                    Text(
                                        text = "Logout Account",
                                        modifier = Modifier.clickable {

                                            viewModel.logout(token, navController)

                                        },
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFFFB6D64),
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
        },

        )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileViewPreview() {
    ProfileView(
        navController = rememberNavController(),
        viewModel = viewModel(),
        token = "",
        username = ""
    )
}