package com.yohana.echolearn.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yohana.echolearn.route.PagesEnum
import com.yohana.echolearn.viewmodels.SpeakingViewModel

@Composable
fun SimpleAlertDialog(
    navHostController: NavHostController,
    score: Int,
    viewModel: SpeakingViewModel,
    onDismiss: () -> Unit // Tambahkan callback untuk mengontrol state showDialog dari luar
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
                navHostController.navigate(route = PagesEnum.Home.name) {
                    popUpTo(PagesEnum.Home.name) { inclusive = true }
                }
                onDismiss() // Tutup dialog setelah navigasi
            }) {
                Text(text = "Exit")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss() // Tutup dialog
                viewModel.resetAnswerProcessed() // Reset proses jawaban jika perlu
                navHostController.popBackStack() // Navigasi kembali untuk bermain lagi
            }) {
                Text(text = "Play Again")
            }
        }
    )
}
