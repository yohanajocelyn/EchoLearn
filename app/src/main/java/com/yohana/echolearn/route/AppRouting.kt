package com.yohana.echolearn.route

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.Greeting
import kotlinx.coroutines.delay

fun isFirstTimeLaunch(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("isFirstTimeLaunch", true)
}

fun setFirstTimeLaunch(context: Context) {
    val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    sharedPref.edit().putBoolean("isFirstTimeLaunch", false).apply()
}

@Composable
fun SplashScreen(onTimeout: () -> Unit){
    LaunchedEffect(Unit) {
        delay(3000L) // Show for 3 seconds
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to My App"
        )
    }
}

@Composable
fun AppRouting(context: Context, innerpadding: PaddingValues){
    val navController = rememberNavController()
    val isFirstLaunch = remember { isFirstTimeLaunch(context) }

    NavHost(
        navController = navController,
        startDestination = if (isFirstLaunch) "splash" else "home",
        modifier = Modifier.padding(innerpadding)
    ){
        composable(route = "splash"){
            SplashScreen {
                // Mark the first launch as completed
                setFirstTimeLaunch(context)
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true } // Remove splash from backstack
                }
            }
        }

        composable(route = "home"){
            Greeting(name = "Android")
        }
    }
}