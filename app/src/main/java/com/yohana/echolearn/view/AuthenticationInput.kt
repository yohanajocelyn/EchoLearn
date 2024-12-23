package com.yohana.echolearn.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.yohana.echolearn.R

@Composable
fun AuthenticationOutlinedTextField(
    labelText: String,
    placeholderText: String,
    leadingIconSrc: Painter,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardOptions,
    onKeyboardNext: KeyboardActions
) {
    // State for managing the text input value
    val inputValue = remember { mutableStateOf("") }

    OutlinedTextField(
        value = inputValue.value,
        onValueChange = { inputValue.value = it },
        singleLine = true,
        label = {
            Text(
                text = labelText
            )
        },
        placeholder = {
            Text(
                text = placeholderText
            )
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = 15.dp),
        leadingIcon = {
            Image(
                painter = leadingIconSrc,
                contentDescription = null
            )
        },
        keyboardOptions = keyboardType,
        keyboardActions = onKeyboardNext
    )
}

@Composable
fun PasswordOutlinedTextField(
    passwordInput: String,
    onPasswordInputValueChange: (String) -> Unit,
    passwordVisibilityIcon: Painter,
    labelText: String,
    placeholderText: String,
    onTrailingIconClick: () -> Unit,
    passwordVisibility: VisualTransformation,
    modifier: Modifier = Modifier,
    onKeyboardNext: KeyboardActions,
    keyboardImeAction: ImeAction
) {
    OutlinedTextField(
        value = passwordInput,
        onValueChange = onPasswordInputValueChange,
        singleLine = true,
        label = {
            Text(
                text = labelText
            )
        },
        trailingIcon = {
            Image(
                painter = passwordVisibilityIcon,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onTrailingIconClick()
                    }
            )
        },
        placeholder = {
            Text(
                text = placeholderText
            )
        },
        visualTransformation = passwordVisibility,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = 15.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = keyboardImeAction
        ),
        keyboardActions = onKeyboardNext
    )
}