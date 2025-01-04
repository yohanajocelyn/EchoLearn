package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.R
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.Top3Bar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.components.UserCard

@Composable
fun LeaderBoardView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    token: String
) {
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
                            Top3Bar(width = 100, height = 160, Color(0xFFF39C12))
                            Top3Bar(width = 100, height = 200, Color(0xFF2C3E50))
                            Top3Bar(width = 100, height = 130, Color(0xFF3498DB))
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                    items(8) {
                        UserCard()
                        Spacer(modifier = Modifier.height(10.dp))
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

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewLeader() {
    LeaderBoardView(navController = rememberNavController(), token = "aaaa")
}

