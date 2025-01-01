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
import com.yohana.echolearn.models.AttemptModel
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins

@Composable
fun AttemptCard(
    attemptModel: AttemptModel = AttemptModel(),
    index: Int = 0,
    onClick: () -> Unit = {}
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
                        text = "${index}",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                        ,
                        fontFamily = poppins,
                        color = Color.White,
                        fontSize = 24.sp,
                    )
                    Column (
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ){
                        Text(
                            text = attemptModel.getDay(),
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = attemptModel.getDate(),
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
                    text = "Like the Movies",
                    fontFamily = poppins,
                )
                Text(
                    text = "Laufey",
                    fontFamily = poppins,
                )
                Text(
                    text = "Listening Exercise",
                    fontFamily = poppins,
                )
                Text(
                    text = "Completed",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0A5C36)
                )
                Text(
                    text = "Not Completed",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBC2023)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AttemptCardPreview() {
    EchoLearnTheme {
        AttemptCard()
    }
}