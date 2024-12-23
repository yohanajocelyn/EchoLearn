package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins

@Composable
fun HomeView(
    name: String = "Android"
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF3DB2FF))
            .padding(top = 18.dp)
        ,
    ){
        Greeting(name = name)
    }
}

@Composable
fun Greeting(name: String){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
            ,
        ){
            Text(
                text = "Hi, $name!",
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 28.dp)
            )
            Text(
                text = "What kind of exercise would you like to do today?",
                color = Color.White,
                fontFamily = poppins,
                fontSize = 14.sp,
                style = TextStyle(
                    lineHeight = 20.sp
                )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.learning_img),
            contentDescription = "Learning",
            modifier = Modifier
                .graphicsLayer { scaleX = (-1f).toFloat() }
                .size(200.dp)
                .offset(x = -35.dp)
        )
    }
}

@Composable
fun LearningMenu(){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                )
            )
        ,
    ){

    }
}

@Composable
fun LearningCard(){
    Card {
        Box(){
            Row {

            }
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Play Button",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeViewPreview() {
    EchoLearnTheme {
        HomeView()
    }
}
