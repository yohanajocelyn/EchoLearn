package com.yohana.echolearn.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.yohana.echolearn.route.PagesEnum

@Composable
fun SimpleAlertDialog(
    navHostController: NavHostController,
    score: Int
) {
    AlertDialog(
        onDismissRequest = {
            // Logika saat dialog dicoba untuk di-dismiss, jika ingin mencegah:
            // Tidak melakukan apa-apa (membuat dialog tidak bisa di-dismiss)
        },
        title = {
            Text(text = "It's Done")
        },
        text = {
            Text(text = "Your Score: $score")
        },
        confirmButton = {
            TextButton(onClick = {
                navHostController.navigate(route = PagesEnum.Home.name) {
                    // Membersihkan stack navigasi agar tidak kembali ke dialog
                    popUpTo(PagesEnum.Home.name) { inclusive = true }
                }
            }) {
                Text(text = "Exit")
            }
        },
        dismissButton = {
            // Opsional: Jika ingin tombol dismiss berbeda
            TextButton(onClick = {
                navHostController.navigate(route = PagesEnum.Home.name) {
                    popUpTo(PagesEnum.Home.name) { inclusive = true }
                }
            }) {
                Text(text = "Dismiss")
            }
        }
    )
}
