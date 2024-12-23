package com.yohana.echolearn.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.yohana.echolearn.R
import com.yohana.echolearn.view.AuthenticationOutlinedTextField
import com.yohana.echolearn.view.PasswordOutlinedTextField

@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    var password by remember { mutableStateOf("password123") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 100.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "WELCOME TO",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "EchoLearn",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AuthenticationOutlinedTextField(

                labelText = "Email Address",
                placeholderText = "Enter your email",
                leadingIconSrc = painterResource(id = R.drawable.ic_email),
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                onKeyboardNext = KeyboardActions.Default
            )

            Spacer(modifier = Modifier.height(10.dp))

            AuthenticationOutlinedTextField(

                labelText = "Username",
                placeholderText = "Enter your username",
                leadingIconSrc = painterResource(id = R.drawable.ic_person),
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                onKeyboardNext = KeyboardActions.Default
            )

            Spacer(modifier = Modifier.height(10.dp))


            PasswordOutlinedTextField(
                passwordInput = password,
                onPasswordInputValueChange = { newPassword -> password = newPassword },
                labelText = "Password",
                placeholderText = "Enter your password",
                onTrailingIconClick = {
                    isPasswordVisible = !isPasswordVisible
                },
                passwordVisibility = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardImeAction = ImeAction.Done,
                onKeyboardNext = KeyboardActions.Default,
                passwordVisibilityIcon = painterResource(id = if (isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye)
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordOutlinedTextField(
                passwordInput = password,
                onPasswordInputValueChange = { newPassword -> password = newPassword },
                labelText = "Confirm Password",
                placeholderText = "Enter your password",
                onTrailingIconClick = {
                    isPasswordVisible = !isPasswordVisible
                },
                passwordVisibility = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardImeAction = ImeAction.Done,
                onKeyboardNext = KeyboardActions.Default,
                passwordVisibilityIcon = painterResource(id = if (isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye)
            )
        }

        Button(
            onClick = {
                Toast.makeText(context, "Register clicked", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3DB2FF))
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
    RegisterView()
}
