package com.yohana.echolearn.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.SpeakingViewModel

@Composable
fun SimpleAlertDialog(
    navHostController: NavController,
    score: Int,
    viewModel: SpeakingViewModel,
    token: String,
    id: Int,
    type: String,
    onDismiss: () -> Unit

) {
    AlertDialog(
        onDismissRequest = {
            // Mengubah state showDialog ke false saat dialog ditutup
            onDismiss()
        },
        title = {
            Text(text = "It's Done", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        },
        text = {
            Text(text = "Your Score: $score", fontSize = 16.sp)
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.resetViewModel()
                navHostController.navigate(route = PagesEnum.SongMenu.name + "/Speaking") {
                    popUpTo(PagesEnum.SongMenu.name + "/Speaking") { inclusive = true }
                }
                onDismiss() // Tutup dialog setelah navigasi
            }) {
                Text(text = "Exit")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
                viewModel.resetAnswerProcessed()
                viewModel.resetViewModel()
                navHostController.navigate(route = PagesEnum.Speaking.name + "/$id") {
                    popUpTo(PagesEnum.Speaking.name + "/$id") { inclusive = true }
                }

            }) {
                Text(text = "Play Again")
            }
        }
    )
}
