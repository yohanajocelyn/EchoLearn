package com.yohana.echolearn.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.yohana.echolearn.Greeting
import com.yohana.echolearn.ui.theme.EchoLearnTheme

@Composable
fun StarterScreenView(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StarterScreenPreview() {
    EchoLearnTheme {
        StarterScreenView()
    }
}