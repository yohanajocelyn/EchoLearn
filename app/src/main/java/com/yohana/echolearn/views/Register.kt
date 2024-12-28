package com.yohana.echolearn.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.yohana.echolearn.R
import com.yohana.echolearn.uistates.AuthenticationStatusUIState
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.view.AuthenticationOutlinedTextField
import com.yohana.echolearn.view.PasswordOutlinedTextField
import com.yohana.echolearn.view.StylishImageDropdown
import com.yohana.echolearn.viewmodels.AuthenticationViewModel

@Composable
fun RegisterView(
    viewModel: AuthenticationViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val context = LocalContext.current

    val registerUIState by viewModel.authenticationUIState.collectAsState()

    LaunchedEffect(viewModel.dataStatus) {
        val dataStatus = viewModel.dataStatus
        if (dataStatus is AuthenticationStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearErrorMessage()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 40.dp)
            .background(Color(0xFFF9FAFB)), // Light background
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween // Make sure the elements align at the top
    ) {
        // Row with the back button and "WELCOME TO" text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "",
                modifier = Modifier
                    .clickable { navController.navigate(route = PagesEnum.Login.name) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "WELCOME TO",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "EchoLearn",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007BFF)
                )
            }
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AuthenticationOutlinedTextField(
                inputValue = viewModel.emailInput,
                onInputValueChange = { viewModel.setEmail(it) },
                labelText = "Email Address",
                placeholderText = "Enter your email",
                leadingIconSrc = painterResource(id = R.drawable.ic_email),
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                onKeyboardNext = KeyboardActions.Default
            )

            Spacer(modifier = Modifier.height(12.dp))

            AuthenticationOutlinedTextField(
                inputValue = viewModel.usernameInput,
                onInputValueChange = { viewModel.setUsername(it) },
                labelText = "Username",
                placeholderText = "Enter your username",
                leadingIconSrc = painterResource(id = R.drawable.ic_person),
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                onKeyboardNext = KeyboardActions.Default
            )

            Spacer(modifier = Modifier.height(12.dp))

            StylishImageDropdown(
                authenticationViewModel = viewModel,
                defaultProfilePictures = viewModel.defaultProfilePictures
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordOutlinedTextField(
                passwordInput = viewModel.passwordInput,
                onPasswordInputValueChange = { viewModel.setPassword(it) },
                labelText = "Password",
                placeholderText = "Enter your password",
                onTrailingIconClick = { viewModel.setPasswordVisibility() },
                passwordVisibility = registerUIState.passwordVisibility,
                keyboardImeAction = ImeAction.Done,
                onKeyboardNext = KeyboardActions.Default,
                passwordVisibilityIcon = painterResource(id = registerUIState.passwordVisibilityIcon)
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordOutlinedTextField(
                passwordInput = viewModel.confirmPasswordInput,
                onPasswordInputValueChange = { viewModel.setConfirmPassword(it) },
                labelText = "Confirm Password",
                placeholderText = "Re-enter your password",
                onTrailingIconClick = { viewModel.setConfirmPasswordVisibility() },
                passwordVisibility = registerUIState.passwordVisibility,
                keyboardImeAction = ImeAction.Done,
                onKeyboardNext = KeyboardActions.Default,
                passwordVisibilityIcon = painterResource(id = registerUIState.passwordVisibilityIcon)
            )

            Spacer(modifier = Modifier.height(20.dp))


        }
        Button(
            onClick = {
                viewModel.registerUser(navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .background(Color(0xFF007BFF), shape = MaterialTheme.shapes.medium), // Modern rounded button
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Register",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun RegisterViewPreview() {
    RegisterView(
        viewModel = viewModel(),
        navController = rememberNavController(),
    )
}
