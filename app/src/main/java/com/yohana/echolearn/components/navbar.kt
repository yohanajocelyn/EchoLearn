package com.yohana.echolearn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yohana.echolearn.R
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.BottomNavBarViewModel

@Composable
fun Navbar(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: BottomNavBarViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.setCurrentView(
            navController!!.currentBackStackEntry?.destination?.route.toString()
        )
    }

    Column {
        Divider(
            color = Color.LightGray,
            thickness = 2.dp    // Ketebalan garis
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(color = Color(0xFFF6F6F6))
                .padding(start = 25.dp, end= 25.dp, top=10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            navButton(
                imageId = R.drawable.bookopentext,
                text = "Home",
                onClick = { navController!!.navigate(PagesEnum.Home.name) },
                color = viewModel.getColor("Home")
            )
            navButton(
                imageId = R.drawable.bookbookmark,
                text = "Notes",
                onClick = { navController!!.navigate(PagesEnum.Notes.name) },
                color = viewModel.getColor("Notes")
            )
            navButton(
                imageId = R.drawable.trophy,
                text = "Leaderboard",
                onClick = { navController!!.navigate(PagesEnum.Leaderboards.name) },
                color = viewModel.getColor("Leaderboard")
            )
            navButton(
                imageId = R.drawable.usercircle,
                text = "Profile",
                onClick = { navController!!.navigate(PagesEnum.Profile.name) },
                color = viewModel.getColor("Profile")
            )
        }
    }
}

@Composable
fun navButton(imageId: Int, text: String, onClick: () -> Unit = {}, color: Color){
    Column(
        modifier = Modifier
            .clickable { onClick() }
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "Logo")
        Text(
            text = text,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Navbar()
}