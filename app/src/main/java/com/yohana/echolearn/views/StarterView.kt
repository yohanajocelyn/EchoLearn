package com.yohana.echolearn.views

import android.hardware.camera2.params.ColorSpaceTransform
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarterView(){
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    // Automatically expand the bottom sheet when the screen is displayed
    LaunchedEffect(Unit) {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = Color(0xFF3DB2FF),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Practice English for free!",
                    color = Color.White,
                    fontFamily = poppins
                )
                Text(
                    text = "Practice English interactively through music!",
                    color = Color.White,
                    fontFamily = poppins,
                    textAlign = TextAlign.Center
                )
            }
        },
        sheetPeekHeight = 0.dp // Adjust this to your needs if a partial view of the sheet should be visible initially
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo_colorful),
                contentDescription = "App Logo",
                modifier = Modifier.width(250.dp)
            )
            Text(
                text = "Echo Learn",
                color = Color(0xFF0068AD),
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

//    Column (
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = Color.White)
//            .padding(top = 64.dp)
//        ,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//        Image(
//            painter = painterResource(id = R.drawable.app_logo_colorful),
//            contentDescription = "App Logo",
//            modifier = Modifier
//                .width(250.dp)
//        )
//        Text(
//            text = "Echo Learn",
//            color = Color(0xFF0068AD),
//            fontFamily = poppins,
//            fontWeight = FontWeight.SemiBold,
//            fontSize = 36.sp,
//            modifier = Modifier
//                .padding(top = 16.dp)
//        )
//        ModalBottomSheet(
//            onDismissRequest = {},
//        ) {
//            Text(
//                text = "Lorem Ipsum",
//                color = Color.Black
//            )
//        }
//    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StarterViewPreview() {
    EchoLearnTheme {
        StarterView()
    }
}
