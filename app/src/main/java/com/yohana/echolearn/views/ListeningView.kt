package com.yohana.echolearn.views

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins
import com.yohana.echolearn.viewmodels.ListeningViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListeningView(
    navController: NavController,
    viewModel: ListeningViewModel,
    songId: Int,
    type: String,
    token: String,
    attemptId: Int = 0,
    isContinue: Boolean = false
){

    LaunchedEffect(Unit){
        viewModel.initializeViewModel(
            songId = songId,
            token = token,
            navController = navController,
            type = type,
            attemptId = attemptId,
            isContinue = isContinue
        )
    }

    val song by viewModel.song.collectAsState()
    val lines = viewModel.processLyrics()

    BackHandler {
        viewModel.setShowDialog()
    }

    if (viewModel.isLoading){
        CircularProgressIndicator()
    }else {
        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp, bottom = 16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(song.image),
                        contentDescription = "Album Cover",
                        contentScale = ContentScale.Crop,  // This ensures the image fills the space nicely
                        modifier = Modifier
                            .size(150.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(8.dp),
                                spotColor = Color.Black.copy(alpha = 0.75f),
                                ambientColor = Color.Black.copy(alpha = 0.25f)
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = song.title,
                            color = Color(0xFF005FB7),
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = song.genre,
                            color = Color(0xFF005FB7),
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = song.artist,
                            color = Color(0xFF005FB7),
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.weight(1f))
                        PlayPauseButton(
                            isPlaying = viewModel.isPlaying,
                            onToggle = {
                                viewModel.updateIsPlaying()
                            }
                        )
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    lines.forEach { line ->
                        FlowRow(
                            modifier = Modifier.padding(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            line.words.forEach { word ->
                                when (word) {
                                    is ListeningViewModel.TextElement.Blank -> {
                                        BasicTextField(
                                            value = viewModel.userAnswers[word.index],
                                            onValueChange = {
                                                viewModel.updateUserAnswer(
                                                    word.index,
                                                    it
                                                )
                                            },
                                            singleLine = true,
                                            textStyle = TextStyle(
                                                fontSize = 16.sp,
                                                textAlign = TextAlign.Center
                                            ),
                                            modifier = Modifier
                                                .width(120.dp)
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                                .border(
                                                    width = 1.dp,
                                                    color = MaterialTheme.colorScheme.outline,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .then(Modifier.padding(vertical = 1.dp))
                                        ) { innerTextField ->
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                if (viewModel.userAnswers[word.index].isEmpty()) {
                                                    Text(
                                                        "Type",
                                                        color = MaterialTheme.colorScheme.outline.copy(
                                                            alpha = 0.5f
                                                        ),
                                                        fontSize = 16.sp,
                                                    )
                                                }
                                                innerTextField()
                                            }
                                        }
                                    }

                                    is ListeningViewModel.TextElement.Regular -> {
                                        Text(
                                            text = word.text,
                                            modifier = Modifier.padding(
                                                start = 2.dp,
                                                end = 2.dp,
                                                bottom = 10.dp
                                            ),
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Button(
                        onClick = {
                            if(isContinue){
                                viewModel.updateProgress(
                                    token = token,
                                    navController = navController,
                                    isCompleted = true
                                )
                            }else{
                                viewModel.saveProgress(
                                    token = token,
                                    navController = navController,
                                    isCompleted = true
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3DB2FF),
                        )
                    ) {
                        Text(
                            text = "Submit Answer",
                            fontFamily = poppins,
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }

    if (viewModel.showSaveDialog) {
        SaveDialog(
            onDismissRequest = {
                viewModel.returnWithoutSaving(navController)
           },
            onConfirmation = { viewModel.saveProgress(token = token, navController = navController, isCompleted = false) },
            viewModel = viewModel
        )
    }
}

@Composable
fun PlayPauseButton(
    isPlaying: Boolean = true,
    onToggle: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .clickable { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = isPlaying) { playing ->
            if (playing) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pause),
                    contentDescription = "Pause",
                    modifier = Modifier
                        .size(45.dp)
                    ,
                    colorFilter = ColorFilter.tint(Color(0xFF005FB7))
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "Play",
                    modifier = Modifier
                        .size(45.dp)
                    ,
                    colorFilter = ColorFilter.tint(Color(0xFF005FB7))
                )
            }
        }
    }
}

@Composable
fun SaveDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    viewModel: ListeningViewModel
) {
    AlertDialog(
        title = {
            Text(text = "Confirmation")
        },
        text = {
            Text(text = "Would you like to save your progress?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListeningViewPreview() {
    EchoLearnTheme {
        ListeningView(
            navController = rememberNavController(),
            viewModel = viewModel(),
            songId = 1,
            type = "Listening",
            token = ""
        )
    }
}