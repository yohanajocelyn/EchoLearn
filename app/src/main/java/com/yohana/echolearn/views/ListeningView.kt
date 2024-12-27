package com.yohana.echolearn.views

import android.media.MediaPlayer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins
import com.yohana.echolearn.viewmodels.ListeningViewModel

data class Lyrics(
    val text: String = "The drought was the very _, ah-ah, ah-ah " +
            "\nWhen the _ that we'd grown _ died of thirst " +
            "\nIt was months and months of back and _, ah-ah, ah-ah " +
            "\nYou're still all over me like a wine-stained dress I can't wear _",
    val words: ArrayList<String> = arrayListOf("worst", "flowers", "together", "forth", "anymore")
)

@Composable
fun ListeningView(
//    viewModel: ListeningViewModel = viewModel(),
){

    val context = LocalContext.current
    val isPlaying = remember { mutableStateOf(false) }
    val sound = remember { MediaPlayer.create(context, R.raw.laufeylikethemovies) }

    LazyColumn (
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ){
        item {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, bottom = 16.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.typicalofme),
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
                Column (
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Text(
                        text = "Like The Movies",
                        color = Color(0xFF005FB7),
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "Jazz, Pop",
                        color = Color(0xFF005FB7),
                        fontFamily = poppins,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.weight(1f))
                    PlayPauseButton(
                        isPlaying = isPlaying.value,
                        onToggle = {
                            if (isPlaying.value) {
                                sound.pause()
                            } else {
                                sound.start()
                            }
                            isPlaying.value = !isPlaying.value
                        }
                    )
                }
            }
        }
        item {
            LyricsScreen()
        }
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LyricsScreen(lyrics: Lyrics = Lyrics()) {
    val lines = lyrics.text.split("\n")
    val userInputs = remember { mutableStateListOf(*Array(lyrics.words.size) { "" }) }

    // Pre-process the lyrics to find all blank positions
    val blankPositions = remember {
        val positions = mutableListOf<Int>()
        var counter = 0
        lines.forEach { line ->
            line.split(" ").forEach { word ->
                if (word.contains("_")) {
                    positions.add(counter)
                }
                counter++
            }
        }
        positions
    }

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        var wordCounter = 0
        lines.forEach { line ->
            FlowRow(
                modifier = Modifier.padding(),
                verticalArrangement = Arrangement.Center
            ) {
                line.split(" ").forEach { word ->
                    if (word.contains("_")) {
                        val blankIndex = blankPositions.indexOf(wordCounter)
                        if (blankIndex < lyrics.words.size) {
//                            OutlinedTextField(
//                                value = userInputs[blankIndex],
//                                onValueChange = {
//                                    userInputs[blankIndex] = it
//                                },
//                                placeholder = {
//                                    Text(
//                                        text = "Type here",
//                                        fontSize = 16.sp
//                                    )
//                                },
//                                maxLines = 1,
//                                singleLine = true,
//                                modifier = Modifier
//                                    .width(150.dp)
//                                ,
//                            )
                            BasicTextField(
                                value = userInputs[blankIndex],
                                onValueChange = { userInputs[blankIndex] = it },
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
                                    if (userInputs[blankIndex].isEmpty()) {
                                        Text(
                                            "Type",
                                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                        if(word.length>1){
                            Text(
                                text = word.substring(1),
                                modifier = Modifier.padding(start = 2.dp, end = 2.dp)
                            )
                        }
                    } else {
                        Text(
                            text = word,
                            modifier = Modifier.padding(start = 2.dp, end = 2.dp, bottom = 10.dp),
                            fontSize = 16.sp
                        )
                    }
                    wordCounter++
                }
            }
        }
    }

    // Debug section
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        userInputs.forEachIndexed { index, input ->
            Text("Input $index: $input")
        }
    }
}

//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun LyricsSection(
//    lyrics: Lyrics = Lyrics()
//){
//    val lines = lyrics.text.split("\n")
//    val userInputs = remember { mutableStateListOf(*Array(lyrics.words.size) { "" }) }
//
//    val blankPositions = remember {
//        val positions = mutableListOf<Int>()
//        var counter = 0
//        lines.forEach { line ->
//            line.split(" ").forEach { word ->
//                if (word.contains("_")) {
//                    positions.add(counter)
//                }
//                counter++
//            }
//        }
//        positions
//    }
//
//    Column(
//        modifier = Modifier
//            .padding(top = 54.dp, start = 16.dp, end = 16.dp)
//    ) {
//        var wordCounter = 0
//        lines.forEach { line ->
//            FlowRow(
//                modifier = Modifier.padding(bottom = 16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                line.split(" ").forEach { word ->
//                    if (word.contains("_")) {
//                        val blankIndex = blankPositions.indexOf(wordCounter)
//                        if (blankIndex < lyrics.words.size) {
//                            OutlinedTextField(
//                                value = userInputs[blankIndex],
//                                onValueChange = {
//                                    userInputs[blankIndex] = it
//                                },
//                                placeholder = { Text("Type here") },
//                                modifier = Modifier
//                                    .width(100.dp)
//                                    .padding(end = 4.dp)
//                            )
//                        }
//                        if(word.length>1){
//                            Text(
//                                text = word.substring(1),
//                                modifier = Modifier.padding(end = 4.dp)
//                            )
//                        }
//                    } else {
//                        Text(
//                            text = word,
//                            modifier = Modifier.padding(end = 4.dp)
//                        )
//                    }
//                    wordCounter++
//                }
//            }
//        }
//    }
//}

//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun LyricsScreen(lyrics: Lyrics, innerPadding: PaddingValues = PaddingValues(0.dp)){
//    val lines = lyrics.text.split("\n")
//    val userInputs = remember { mutableStateListOf(*Array(lyrics.words.size) { "" }) }
//    var blankIndex = remember { mutableStateOf(0) }
//    val context = LocalContext.current
//    val isPlaying = remember { mutableStateOf(false) }
////    val sound: MediaPlayer = MediaPlayer.create(context, R.raw.laufeylikethemovies).apply {
////        setVolume(7f, 7f)
////    }
//    LazyColumn {
//        item {
//            Column (
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .then(Modifier.padding(top = 54.dp, start = 16.dp, end = 16.dp))
//            ){
////                Button(
////                    modifier = Modifier
////                        .padding(4.dp),
////                    onClick = { toggleMusicButton(sound, isPlaying) }
////                ) {
////                    Text(
////                        text = "Toggle"
////                    )
////                }
//                lines.forEach{line ->
//                    FlowRow (
//                        modifier = Modifier.padding(bottom = 16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ){
//                        val words = line.split(" ")
//                        words.forEach{word ->
//                            if(word.contains(Regex("_") )) {
//                                OutlinedTextField(
//                                    value = userInputs[blankIndex.value],
//                                    onValueChange = { userInputs[blankIndex.value] = it },
//                                    placeholder = { Text("Type here") },
//                                    modifier = Modifier.width(100.dp).padding(end = 4.dp).height(24.dp)
//                                )
//                                blankIndex.value++
//                            }else{
//                                Text(
//                                    text = word,
//                                    modifier = Modifier.padding(end = 4.dp)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun LyricsScreen(lyrics: Lyrics, innerPadding: PaddingValues = PaddingValues(0.dp)) {
//    val lines = lyrics.text.split("\n")
//    val userInputs = remember { mutableStateListOf(*Array(lyrics.words.size) { "" }) }
//
//    // Pre-process the lyrics to find all blank positions
//    val blankPositions = remember {
//        val positions = mutableListOf<Int>()
//        var counter = 0
//        lines.forEach { line ->
//            line.split(" ").forEach { word ->
//                if (word.contains("_")) {
//                    positions.add(counter)
//                }
//                counter++
//            }
//        }
//        positions
//    }
//
//    LazyColumn {
//        item {
//            Column(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .then(Modifier.padding(top = 54.dp, start = 16.dp, end = 16.dp))
//            ) {
//                var wordCounter = 0
//                lines.forEach { line ->
//                    FlowRow(
//                        modifier = Modifier.padding(bottom = 16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        line.split(" ").forEach { word ->
//                            if (word.contains("_")) {
//                                val blankIndex = blankPositions.indexOf(wordCounter)
//                                if (blankIndex < lyrics.words.size) {
//                                    OutlinedTextField(
//                                        value = userInputs[blankIndex],
//                                        onValueChange = {
//                                            userInputs[blankIndex] = it
//                                        },
//                                        placeholder = { Text("Type here") },
//                                        modifier = Modifier
//                                            .width(100.dp)
//                                            .padding(end = 4.dp)
//                                            .height(100.dp)
//                                    )
//                                }
//                                if(word.length>1){
//                                    Text(
//                                        text = word.substring(1),
//                                        modifier = Modifier.padding(end = 4.dp)
//                                    )
//                                }
//                            } else {
//                                Text(
//                                    text = word,
//                                    modifier = Modifier.padding(end = 4.dp)
//                                )
//                            }
//                            wordCounter++
//                        }
//                    }
//                }
//            }
//
//            // Debug section
//            Column(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                userInputs.forEachIndexed { index, input ->
//                    Text("Input $index: $input")
//                }
//            }
//        }
//    }
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListeningViewPreview() {
    EchoLearnTheme {
        ListeningView()
    }
}