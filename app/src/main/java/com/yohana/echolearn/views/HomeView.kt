package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yohana.echolearn.R
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins
import com.yohana.echolearn.view.Navbar


@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    name: String = "Android"
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF3DB2FF))
            .padding(top = 18.dp),
    ) {
        Greeting(name = name)
        LearningMenu(navController = navController)
    }
}

@Composable
fun Greeting(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Hi, $name!",
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 28.dp)
            )
            Text(
                text = "What kind of exercise would you like to do today?",
                color = Color.White,
                fontFamily = poppins,
                fontSize = 14.sp,
                style = TextStyle(
                    lineHeight = 20.sp
                )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.learning_img),
            contentDescription = "Learning",
            modifier = Modifier
                .graphicsLayer { scaleX = (-1f).toFloat() }
                .size(200.dp)
                .offset(x = -35.dp)
        )
    }
}

@Composable
fun LearningMenu(
    navController: NavController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topStart = 13.dp,
                    topEnd = 13.dp,
                )
            )
            .background(color = Color.White)
            .padding(top = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            LearningCard(
                mainColor = Color(0xFF3DB2FF),
                shadowColor = Color(0xFF263238),
                menu = "Speaking",
                content = "Listen and shadow select lyrics to perfect your pronunciation!",
                img = painterResource(id = R.drawable.speaking),
                onCardClick = {
                    println("Navigating to SongMenu")
                    navController?.navigate(PagesEnum.SongMenu.name + "/" + PagesEnum.Speaking.name)
                }
            )
            LearningCard(
                mainColor = Color(0xFFFF8504),
                shadowColor = Color(0xFF473D33),
                menu = "Listening",
                content = "Catch the missing words in the lyrics as you listen!",
                img = painterResource(id = R.drawable.listening),
                onCardClick = {
                    println("Navigating to SongMenu")
                    navController?.navigate(PagesEnum.SongMenu.name + "/" + PagesEnum.Listening.name)
                })
        }

        Column(
            verticalArrangement = Arrangement.Bottom

        ) {
            Navbar() // Tambahkan konten navbar di sini
        }
    }
}

@Composable
fun LearningCard(
    mainColor: Color,
    shadowColor: Color,
    menu: String,
    content: String,
    img: Painter,
    onCardClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(bottom = 40.dp)
            .clickable { onCardClick() },
        ) {
        //Shadow
        Box(
            modifier = Modifier
                .width(326.dp)
                .height(132.dp)
                .offset(x = 8.dp, y = 8.dp)
                .background(color = shadowColor, shape = RoundedCornerShape(size = 12.dp))
        )

        Box(
            Modifier
                .width(326.dp)
                .height(132.dp)
                .background(color = mainColor, shape = RoundedCornerShape(size = 12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 32.dp),
            ) {
                Image(
                    painter = img,
                    contentDescription = "Speaking Img",
                    modifier = Modifier
                        .graphicsLayer { scaleX = (-1f).toFloat() }
                        .padding(end = 10.dp, start = 8.dp)
                        .size(113.dp)
                )
                Column {
                    Text(
                        text = menu,
                        style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 31.2.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFEFF8FF),
                        ),
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                    Text(
                        text = content,
                        style = TextStyle(
                            fontSize = 10.sp,
                            lineHeight = 14.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }
            FloatingActionButton(
                onClick = { onCardClick() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 10.dp)
                    .clip(CircleShape)
                    .size(52.dp),
                containerColor = Color.White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Play Button",
                    modifier = Modifier
                        .size(48.dp),
                    colorFilter = ColorFilter.tint(mainColor)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeViewPreview() {
    EchoLearnTheme {
        HomeView()
    }
}
