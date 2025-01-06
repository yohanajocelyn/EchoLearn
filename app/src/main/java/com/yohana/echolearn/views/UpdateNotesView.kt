package com.yohana.echolearn.views


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yohana.echolearn.R
import com.yohana.echolearn.components.AuthenticationOutlinedTextField
import com.yohana.echolearn.components.Navbar
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.viewmodels.UpdateNoteViewModel

@Composable
fun UpdateNote(
    navController: NavController,
    viewModel: UpdateNoteViewModel = viewModel(),
    token: String,
    id: Int,
    username: String
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                pageTitle = "Update Notes",
                onBackClick = { navController.popBackStack() }
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    item {
                        AuthenticationOutlinedTextField(
                            inputValue = viewModel.wordInput,
                            onInputValueChange = { viewModel.setWord(it) },
                            labelText = "Word",
                            placeholderText = "Word",
                            leadingIconSrc = painterResource(id = R.drawable.ic_person),
                            keyboardType = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            onKeyboardNext = KeyboardActions.Default
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        AuthenticationOutlinedTextField(
                            inputValue = viewModel.meaningInput,
                            onInputValueChange = { viewModel.setMeaning(it) },
                            labelText = "Meaning",
                            placeholderText = "Meaning",
                            leadingIconSrc = painterResource(id = R.drawable.ic_person),
                            keyboardType = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            onKeyboardNext = KeyboardActions.Default
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = { viewModel.createNote(token, navController, username) }) {
                            Text("Update")
                        }
                    }

                }
            }
        }, bottomBar = {
            Navbar(
                navController = navController
            )
        }


    )
}