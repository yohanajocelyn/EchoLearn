package com.yohana.echolearn.route

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yohana.echolearn.viewmodels.AuthenticationViewModel
import com.yohana.echolearn.viewmodels.HomeViewModel
import com.yohana.echolearn.views.HomeView
import com.yohana.echolearn.views.ListMusic
import com.yohana.echolearn.views.LoginView
import com.yohana.echolearn.views.RegisterView
import com.yohana.echolearn.views.SplashScreenView

import com.yohana.echolearn.views.StarterView
import kotlinx.coroutines.delay

fun isFirstTimeLaunch(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("isFirstTimeLaunch", true)
}

fun setFirstTimeLaunch(context: Context) {
    val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    sharedPref.edit().putBoolean("isFirstTimeLaunch", false).apply()
}

enum class PagesEnum {
    Splash,
    Starter,
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
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),

    ) {
    val navController = rememberNavController()
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }

    val token = homeViewModel.token.collectAsState()

    NavHost(
        navController = navController,
        startDestination =
        if (isFirstLaunch) PagesEnum.Splash.name
        else if (token.value != "Unknown" && token.value != "") PagesEnum.Login.name
        else PagesEnum.Starter.name,
        modifier = Modifier.padding(innerpadding)
    ) {
        composable(route = PagesEnum.Splash.name) {
            SplashScreenView {
                // Mark the first launch as completed
                setFirstTimeLaunch(context)

                navController.navigate(PagesEnum.Starter.name) {
                    popUpTo(PagesEnum.Splash.name) { inclusive = true } // Remove splash from backstack
                }
            }
        }

        composable(route = PagesEnum.Starter.name){
            StarterView(
                navController = navController
            )
        }

        //Untuk sementara home yg greeting dulu br nanti dipindah ke logreg
        composable(route = PagesEnum.Login.name) {
            LoginView(
                viewModel = authenticationViewModel,
                navController = navController,
                context = context
            )
        }

        composable(route = PagesEnum.Register.name) {
            RegisterView(
                viewModel = authenticationViewModel,
                navController = navController
            )
        }

        composable(route = PagesEnum.Home.name) {
            HomeView(navController = navController)
        }
        composable(route = PagesEnum.SongMenu.name) {
           ListMusic()
        }
    }
}