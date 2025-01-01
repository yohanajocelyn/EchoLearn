package com.yohana.echolearn.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.components.AttemptCard
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.viewmodels.AttemptViewModel

@Composable
fun AttemptView(
    navController: NavController,
    token: String,
    viewModel: AttemptViewModel
){
    LaunchedEffect(token){
        viewModel.getAttemptDetail(token)
    }

    val attempts by viewModel.attempts.collectAsState()

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
                itemsIndexed(attempts){ index, attempt ->
                    AttemptCard(
                        onClick = { navController.navigate(PagesEnum.Listening.name + "continue-attempt/${attempt.id}") },
                        numbering = index + 1,
                        attempt = attempt
                    )
                }
            }
        },
        bottomBar = {
            Navbar(
                navController = navController
            )
        }
    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AttemptViewPreview() {
    EchoLearnTheme {
        AttemptView(
            navController = rememberNavController(),
            token = "",
            viewModel = viewModel()
        )
    }
}