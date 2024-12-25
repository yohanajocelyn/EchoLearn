package com.yohana.echolearn.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yohana.echolearn.R
import com.yohana.echolearn.ui.theme.EchoLearnTheme
import com.yohana.echolearn.ui.theme.poppins
import com.yohana.echolearn.view.AuthenticationOutlinedTextField
import com.yohana.echolearn.view.PasswordOutlinedTextField
import com.yohana.echolearn.viewmodels.LoginViewModel
import javax.security.auth.login.LoginException

@Composable
fun LoginView(
    viewModel: LoginViewModel = viewModel()
){
    val loginUIState by viewModel.loginUIState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.app_logo_colorful),
            contentDescription = "App Logo",
            modifier = Modifier.width(250.dp)
        )
        Text(
            text = "Echo Learn",
            color = Color(0xFF0068AD),
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
            ,
        ){
            Text(
                text = "Input your email",
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0068AD)
            )
            AuthenticationOutlinedTextField(
                inputValue = loginUIState.email,
                onInputValueChange = { viewModel.onEmailChange(it) },
                labelText = "Email Address",
                placeholderText = "Enter your email",
                leadingIconSrc = painterResource(id = R.drawable.ic_email),
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                onKeyboardNext = KeyboardActions.Default
            )
            Text(
                text = "Input your password",
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0068AD),
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            PasswordOutlinedTextField(
                passwordInput = loginUIState.password,
                onPasswordInputValueChange = { viewModel.onPasswordChange(it) },
                labelText = "Password",
                placeholderText = "Enter your password",
                onTrailingIconClick = { viewModel.setIsPasswordVisible(!loginUIState.isPasswordVisible) },
                passwordVisibility = if (loginUIState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardImeAction = ImeAction.Done,
                onKeyboardNext = KeyboardActions.Default,
                passwordVisibilityIcon = painterResource(id = if (loginUIState.isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye)
            )
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3DB2FF),
                )
            ) {
                Text(
                    text = "Login",
                    fontFamily = poppins,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                    ,
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.weight(1f))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 54.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Don't have an account yet? ",
                    fontFamily = poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0x993C3C43),
                )
                Text(
                    text = "Register",
                    fontFamily = poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF3DB2FF),
                    modifier = Modifier
                        .clickable {  }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginViewPreview() {
    EchoLearnTheme {
        LoginView()
    }
}