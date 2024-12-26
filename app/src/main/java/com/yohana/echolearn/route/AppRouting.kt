package com.yohana.echolearn.route

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.Greeting
import com.yohana.echolearn.viewmodels.AuthenticationViewModel
import com.yohana.echolearn.views.LoginView
import com.yohana.echolearn.views.RegisterView
import com.yohana.echolearn.views.SplashScreenView
import kotlinx.coroutines.delay

fun isFirstTimeLaunch(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("isFirstTimeLaunch", true)
}

fun setFirstTimeLaunch(context: Context) {
    val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    sharedPref.edit().putBoolean("isFirstTimeLaunch", false).apply()
}

enum class PagesEnum{
    Splash,
    Login,
    Register,
    Home,
    SongMenu,
    SongDetail,
    Notes,
    Profile,
    Listening,
    Reading,
    Leaderboards
}

@Composable
fun AppRouting(
    context: Context,
    innerpadding: PaddingValues,
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
){
    val navController = rememberNavController()
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }

    NavHost(
        navController = navController,
        startDestination = if (isFirstLaunch) PagesEnum.Splash.name else PagesEnum.Login.name,
        modifier = Modifier.padding(innerpadding)
    ){
        composable(route = PagesEnum.Splash.name){
            SplashScreenView {
                // Mark the first launch as completed
                setFirstTimeLaunch(context)
                navController.navigate(PagesEnum.Login.name) {
                    popUpTo(PagesEnum.Splash.name) { inclusive = true } // Remove splash from backstack
                }
            }
        }

        //Untuk sementara home yg greeting dulu br nanti dipindah ke logreg
        composable(route = PagesEnum.Login.name){
            LoginView(
                viewModel = authenticationViewModel,
                navController = navController,
                context = context
            )
        }

        composable(route = PagesEnum.Register.name){
            RegisterView()
        }
    }
}