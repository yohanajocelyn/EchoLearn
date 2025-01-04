package com.yohana.echolearn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yohana.echolearn.R
import com.yohana.echolearn.models.LeaderboardResponse

@Composable
fun Top3Bar(width: Int, height: Int, color: Color, user: LeaderboardResponse, pos: Int) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.learning_img),
            contentDescription = "search icon",
            modifier = Modifier
                .size(100.dp),
            contentScale = ContentScale.FillBounds

        )
        Column(
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .background(color = color)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("${user.username}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    "${user.totalScore} PTS",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text("$pos", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)


        }
    }
}