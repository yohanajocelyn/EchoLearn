package com.yohana.echolearn.components

import android.app.Activity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun SimpleAlertDialog() {

    AlertDialog(
        onDismissRequest = {  },
        title = {
            Text(text = "It's Done")
        },
        text = {
            Text("Your Score : ")
        },
        confirmButton = {
            TextButton(onClick = {

            }) {
                Text("Exit")
            }
        },
        dismissButton = {
            TextButton(onClick = {

            }) {
                Text("Play again")
            }
        }
    )
}