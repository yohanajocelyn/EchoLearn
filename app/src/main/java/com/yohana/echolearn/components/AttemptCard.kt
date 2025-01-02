package com.yohana.echolearn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yohana.echolearn.models.AttemptDetail
import com.yohana.echolearn.models.AttemptModel
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins
import java.util.Date

@Composable
fun AttemptCard(
    numbering: Int = 0,
    onClick: () -> Unit = {},
    attempt: AttemptDetail
){
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                onClick()
            }
        ,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color(0xFFf3faff))
        ){
            Box(
                modifier = Modifier
                    .width(90.dp)
                        .background(color = Color(0xFF3DB2FF))
                    .padding(vertical = 8.dp)
                ,
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "${numbering}",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                        ,
                        fontFamily = poppins,
                        color = Color(0xFF0068AD),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Column (
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ){
                        Text(
                            text = attempt.getDay(),
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = attempt.getDate(),
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Column (
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ){
                Text(
                    text = attempt.song.title,
                    fontFamily = poppins,
                    color = Color.Black,
                )
                Text(
                    text = attempt.song.artist,
                    fontFamily = poppins,
                    color = Color.Black,
                )
                Text(
                    text = "${attempt.variant.type} Exercise",
                    fontFamily = poppins,
                    color = Color.Black,
                )
                Text(
                    text = "Score: ${attempt.score} / 100",
                    fontFamily = poppins,
                    color = Color.Black,
                )
                Text(
                    text = if (attempt.isComplete) "Complete" else "Not Completed",
                    fontWeight = FontWeight.Bold,
                    color = if (attempt.isComplete) Color(0xFF0A5C36) else Color(0xFFBC2023)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AttemptCardPreview() {
    EchoLearnTheme {
        AttemptCard(
            attempt = AttemptDetail(
                attemptedAt = Date(),
            )
        )
    }
}