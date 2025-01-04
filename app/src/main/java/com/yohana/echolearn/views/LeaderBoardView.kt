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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.Top3Bar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.components.UserCard
import com.yohana.echolearn.viewmodels.LeaderBoardViewModel

@Composable
fun LeaderBoardView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LeaderBoardViewModel = viewModel(),
    token: String
) {
 val users by viewModel.users.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUsersByTotalScore(token)
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                pageTitle = "Leaderboard",
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
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    //top 3
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom
                        ) {

                            if (users.isNotEmpty()) {
                                Top3Bar(width = 100, height = 160, Color(0xFF2C3E50), users[1], 2)
                                Top3Bar(width = 100, height = 200, Color(0xFFF39C12), users[0], 1)
                                Top3Bar(width = 100, height = 130, Color(0xFF3498DB), users[2], 3)
                            }

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                            for (i in 3 until users.size) {
                                UserCard(user = users[i], index = i + 1)
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                    }
//                    items(8) {
//                        UserCard()
//                        Spacer(modifier = Modifier.height(10.dp))
//                    }

                }


            }


        }, bottomBar = {
            Navbar(
                navController = navController
            )
        }
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewLeader() {
    LeaderBoardView(
        navController = rememberNavController(), viewModel = viewModel(), token = "aaa"
    )
}

