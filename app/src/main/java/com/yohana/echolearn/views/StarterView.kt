package com.yohana.echolearn.views

import android.hardware.camera2.params.ColorSpaceTransform
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.ColorFilter
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp) // Set a maximum height for the bottom sheet
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleText(
                        text = "Practice English",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    TitleText(
                        text = "for free!",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Practice English interactively through music!",
                        color = Color.White,
                        fontFamily = poppins,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 30.dp),
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 18.dp,
                                end = 18.dp,
                                top = 28.dp,
                                bottom = 28.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { TODO() },
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(size = 40.dp)
                                )
                                .width(131.dp)
                                .height(58.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3DB2FF),
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                        ) {
                            ButtonContent(
                                text = "Register",
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = { TODO() },
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(size = 40.dp)
                                )
                                .width(131.dp)
                                .height(58.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF3DB2FF)
                            ),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                top = 8.dp,
                                end = 16.dp,
                                bottom = 8.dp
                            ),
                        ) {
                            ButtonContent(
                                text = "Login",
                                color = Color(0xFF3DB2FF)
                            )
                        }
                    }
                }
            }
        },
        sheetPeekHeight = 48.dp // Adjust this to your needs if a partial view of the sheet should be visible initially
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
}

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier){
    Text(
        text = text,
        color = Color.White,
        fontFamily = poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun ButtonContent(text: String, color: Color){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(
            text = text,
            fontFamily = poppins,
            fontSize = 18.sp,
        )
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Button Img",
            colorFilter = ColorFilter.tint(color = color),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StarterViewPreview() {
    EchoLearnTheme {
        StarterView()
    }
}
