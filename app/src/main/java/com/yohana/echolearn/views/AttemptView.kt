package com.yohana.echolearn.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.components.AttemptCard
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.ui.theme.EchoLearnTheme

@Composable
fun AttemptView(
    navController: NavController
){
    Scaffold (
        topBar = {
            TopBarComponent(
                pageTitle = "Attempts",
                onBackClick = {navController.popBackStack()}
            )
        },
        content = {
            innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .then(Modifier.padding(top = 16.dp))
            ){
                items(10){
                    AttemptCard()
                }
            }
        }
    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AttemptViewPreview() {
    EchoLearnTheme {
        AttemptView(
            navController = rememberNavController()
        )
    }
}