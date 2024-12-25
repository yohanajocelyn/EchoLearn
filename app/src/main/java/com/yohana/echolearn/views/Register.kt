package com.yohana.echolearn.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.yohana.echolearn.R
import com.yohana.echolearn.view.AuthenticationOutlinedTextField
import com.yohana.echolearn.view.PasswordOutlinedTextField

@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit // Callback to handle back button press
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    var password by remember { mutableStateOf("password123") }
    var confirmPassword by remember { mutableStateOf("password123") }
    var isPasswordVisible by remember { mutableStateOf(false) }

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
                inputValue = email.value,
                onInputValueChange = { email.value = it },
                labelText = "Email Address",
                placeholderText = "Enter your email",
                leadingIconSrc = painterResource(id = R.drawable.ic_email),
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                onKeyboardNext = KeyboardActions.Default
            )

            Spacer(modifier = Modifier.height(12.dp))

            AuthenticationOutlinedTextField(
                inputValue = username.value,
                onInputValueChange = { username.value = it },
                labelText = "Username",
                placeholderText = "Enter your username",
                leadingIconSrc = painterResource(id = R.drawable.ic_person),
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                onKeyboardNext = KeyboardActions.Default
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordOutlinedTextField(
                passwordInput = password,
                onPasswordInputValueChange = { newPassword -> password = newPassword },
                labelText = "Password",
                placeholderText = "Enter your password",
                onTrailingIconClick = { isPasswordVisible = !isPasswordVisible },
                passwordVisibility = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardImeAction = ImeAction.Done,
                onKeyboardNext = KeyboardActions.Default,
                passwordVisibilityIcon = painterResource(id = if (isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye)
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordOutlinedTextField(
                passwordInput = confirmPassword,
                onPasswordInputValueChange = { newPassword -> confirmPassword = newPassword },
                labelText = "Confirm Password",
                placeholderText = "Re-enter your password",
                onTrailingIconClick = { isPasswordVisible = !isPasswordVisible },
                passwordVisibility = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardImeAction = ImeAction.Done,
                onKeyboardNext = KeyboardActions.Default,
                passwordVisibilityIcon = painterResource(id = if (isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye)
            )

            Spacer(modifier = Modifier.height(20.dp))


        }
        Button(
            onClick = {
                Toast.makeText(context, "Register clicked", Toast.LENGTH_SHORT).show()
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
    RegisterView(onBackPressed = { /* Handle back navigation here */ })
}
