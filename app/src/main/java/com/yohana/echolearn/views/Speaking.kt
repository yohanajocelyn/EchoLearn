package com.yohana.echolearn.views


import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.yohana.echolearn.R
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.SimpleAlertDialog
import com.yohana.echolearn.viewmodels.SpeakingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SpeakingView(
    modifier: Modifier = Modifier,
    viewModel: SpeakingViewModel = viewModel(),
    id: Int,
    token: String,
    activity: Activity,
    navController: NavController

) {
    val variants by viewModel.variants.collectAsState()
    val variant by viewModel.variant.collectAsState()
    val song by viewModel.song.collectAsState()
    val context = LocalContext.current
    val recognizedText by viewModel.recognizedText.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val answerResponse by viewModel.answerResponse.collectAsState()
    val isAnswerProcessed by viewModel.isAnswerProcessed.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getVariants(token, id, "Speaking", navController)
        viewModel.getSong(
            id,
            token = token,
            navController = navController
        )
    }

    LaunchedEffect(variants) {
        if (variants.isNotEmpty()) {
            viewModel.randomizedVariants()

        }
    }

    LaunchedEffect(recognizedText) {
        if (recognizedText.isNotEmpty()) {
            viewModel.checkAnswerSpeaking(token, variant.id, recognizedText)
            viewModel.resetViewModel()
            showDialog = true
        }
    }



    if (showDialog) {

        SimpleAlertDialog(
            navHostController = navController,
            score = answerResponse.score,
            viewModel = viewModel,
            token = token,
            id = id,
            type = "Speaking",
            onDismiss = {
                showDialog = false
            },

            )
    }

    Box(modifier = Modifier.fillMaxSize()) { // Gunakan Box untuk mengatur tata letak seluruh layar

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF6F6F6))
        ) {
            Column(modifier = Modifier.weight(1f)) { // Gunakan weight untuk mengambil sisa ruang
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
                        modifier = Modifier.clickable { navController.popBackStack() },
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
                        // Konten LazyColumn Anda
                        Row(modifier = modifier.fillMaxWidth()) {
                            Image(
                                painter = rememberImagePainter(song.image),
                                contentDescription = "",
                                modifier = modifier
                                    .width(130.dp)
                                    .height(130.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    "${song.title}", fontSize = 32.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000)
                                )
                                Text(
                                    "${song.artist}", fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Text(
                                    "${song.genre}", fontSize = 16.sp,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${variant.emptyLyric}",
                                fontSize = 18.sp,
                                lineHeight = 25.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = { viewModel.updateIsPlaying() }) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(
                                            id = if (isPlaying) R.drawable.play else R.drawable.play
                                        ),
                                        contentDescription = "Play/Pause Button",
                                        contentScale = ContentScale.None
                                    )
                                    Spacer(modifier = Modifier.width(13.dp))
                                    Text(if (isPlaying) "Pause" else "Play", fontSize = 20.sp)
                                }
                            }


                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Your Answer: ",
                                fontSize = 16.sp,
                                lineHeight = 22.4.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                            )

                            Text(
                                text = "$recognizedText",
                                fontSize = 18.sp,
                                lineHeight = 25.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                            Spacer(modifier = Modifier.height(100.dp))

                            Button(
                                onClick = { viewModel.askSpeechInput(context, activity) },
                                modifier = Modifier
                                    .size(135.dp) // Ukuran lingkaran
                                    .background(Color.Blue, CircleShape),
                                shape = CircleShape, // Bentuk lingkaran
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.voice),
                                    contentDescription = "image description",
                                    contentScale = ContentScale.None
                                )
                            }
                        }
                    }
                }
            }
            // Spacer untuk memisahkan konten dengan Navbar
        }
        // Navbar tetap di bagian bawah
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Selalu di bagian bawah
        ) {
            Navbar(navController = navController)
        }
    }
}


