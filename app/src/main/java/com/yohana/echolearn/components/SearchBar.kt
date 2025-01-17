package com.yohana.echolearn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yohana.echolearn.R
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.ListMusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    listMusicViewModel: ListMusicViewModel,
    navController: NavController,
    token: String,
    type: String
) {
    var query by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search Artists, Songs") }, // Placeholder diubah
            modifier = Modifier.fillMaxWidth(0.8f)

                .height(50.dp), // Tinggi TextField
            textStyle = TextStyle(fontSize = 16.sp), // Ukuran teks
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Image(
            painter = painterResource(id = R.drawable.search), // Ganti dengan resource gambar Anda
            contentDescription = "search icon",
            modifier = Modifier
                .size(35.dp).padding(end = 10.dp)
                .clickable {
                    listMusicViewModel.searchSongs(token, query)
                    navController.navigate(PagesEnum.Search.name + "/$type")
                }
        )


    }
}
