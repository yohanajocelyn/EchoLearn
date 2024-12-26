package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yohana.echolearn.R
import com.yohana.echolearn.view.Navbar

@Composable
fun SpeakingView(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(color = Color(0xFF3DB2FF))
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "",
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "Speaking",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 15.dp),
                color = Color(0xFFF6F6F6)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Row(modifier = modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.aset1),
                        contentDescription = "",
                        modifier = modifier
                            .width(130.dp)
                            .height(130.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            "Hello World", fontSize = 32.sp,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000)
                        )
                        Text(
                            "Hello World", fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            "Hello World", fontSize = 16.sp,

                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "died of thirst It was months and months of back and forth, ah-ah, ah-ah  ",
                        fontSize = 18.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = {}) {
                   Text("play")
                    }
                }
            }
        }

        // Navbar fixed at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF3DB2FF))
        ) {
            Navbar() // Tambahkan konten navbar di sini
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSpeaking() {
    SpeakingView()
}