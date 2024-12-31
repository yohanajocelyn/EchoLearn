package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins
import kotlinx.coroutines.delay

@Composable
fun SplashScreenView(
    onTimeout: () -> Unit,
    systemUiController: SystemUiController
){

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = Color(0xFF3DB2FF), // Your color
            darkIcons = false // true for dark icons, false for light
        )
        systemUiController.setNavigationBarColor(
            color = Color(0xFF3DB2FF) // Your color
        )

        delay(3000L) // Show for 3 seconds
        onTimeout()

        systemUiController.setStatusBarColor(
            color = Color.Transparent, // Your color
            darkIcons = true // true for dark icons, false for light
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent // Your color
        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF3DB2FF))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .width(250.dp)
        )
        Text(
            text = "Echo Learn",
            color = Color.White,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenViewPreview() {
    EchoLearnTheme {
//        SplashScreenView()
    }
}