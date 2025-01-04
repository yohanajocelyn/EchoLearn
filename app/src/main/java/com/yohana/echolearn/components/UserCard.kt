package com.yohana.echolearn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yohana.echolearn.R
import com.yohana.echolearn.models.LeaderboardResponse

@Composable
fun UserCard(user: LeaderboardResponse, index:Int){
    Card(modifier = Modifier
        .height(70.dp)
        .fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("$index", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.learning_img),
                    contentDescription = "search icon",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop

                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("${user.username}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Text("${user.totalScore} PTS", fontSize = 17.sp, fontWeight = FontWeight.Bold)

        }

    }
}