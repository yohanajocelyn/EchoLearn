package com.yohana.echolearn.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yohana.echolearn.Greeting
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme

@Composable
fun Navbar(modifier: Modifier = Modifier) {
Column {
    Divider(
        color = Color.Black,
        thickness = 2.dp    // Ketebalan garis
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(color = Color(0xFFF6F6F6)
            ).padding(start = 25.dp, end= 25.dp, top=10.dp), horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.bookopentext), contentDescription = "")
            Text("Learn")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.bookbookmark), contentDescription = "")
            Text("Notes")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.trophy), contentDescription = "")
            Text("Leaderboard")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.usercircle), contentDescription = "")
            Text("Profile")
        }
    }
}


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Navbar()
}