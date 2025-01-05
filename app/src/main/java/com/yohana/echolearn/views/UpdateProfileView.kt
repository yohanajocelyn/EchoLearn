package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.R
import com.yohana.echolearn.components.AuthenticationOutlinedTextField
import com.yohana.echolearn.components.StylishImageDropdown
import com.yohana.echolearn.components.TopBarComponent
import com.yohana.echolearn.viewmodels.AuthenticationViewModel
import com.yohana.echolearn.viewmodels.UpdateProfileViewModel

@Composable
fun UpdateProfileView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AuthenticationViewModel,
    id: Int
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                pageTitle = "Update Profile",
                onBackClick = { navController.popBackStack() }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.White)
            ) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 18.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.learning_img),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .background(color = Color(0xFFE4E4E4)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Column {
                                Text(
                                    "Daffa",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF263238),
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                Text(
                                    "Daffa",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF898989),

                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        AuthenticationOutlinedTextField(
                            inputValue = viewModel.usernameInput,
                            onInputValueChange = { viewModel.setUsername(it) },
                            labelText = "Name",
                            placeholderText = "Name",
                            leadingIconSrc = painterResource(id = R.drawable.ic_person),
                            keyboardType = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            onKeyboardNext = KeyboardActions.Default
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        AuthenticationOutlinedTextField(
                            inputValue = viewModel.emailInput,
                            onInputValueChange = { viewModel.setEmail(it) },
                            labelText = "Name",
                            placeholderText = "Name",
                            leadingIconSrc = painterResource(id = R.drawable.ic_email),
                            keyboardType = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            onKeyboardNext = KeyboardActions.Default
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        StylishImageDropdown(
                            authenticationViewModel = viewModel,
                            defaultProfilePictures = viewModel.defaultProfilePictures
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }


            }

        }
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewUpdate() {
    UpdateProfileView(
        navController = rememberNavController(),
        viewModel = viewModel(factory = UpdateProfileViewModel.Factory),

        id = 1,
    )

}