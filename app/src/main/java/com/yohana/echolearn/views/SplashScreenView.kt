package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme

val poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_italic, FontWeight.Normal, FontStyle.Italic)
)

@Composable
fun SplashScreenView(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF3DB2FF))
    ){
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo"
        )
        Text(
            text = "Echo Learn",
            color = Color.White,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenViewPreview() {
    EchoLearnTheme {
        SplashScreenView()
    }
}