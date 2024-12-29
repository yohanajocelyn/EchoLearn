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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yohana.echolearn.viewmodels.AuthenticationViewModel
import com.yohana.echolearn.viewmodels.GenreViewModel
import com.yohana.echolearn.viewmodels.HomeViewModel
import com.yohana.echolearn.viewmodels.ListeningViewModel
import com.yohana.echolearn.views.GenreView
import com.yohana.echolearn.views.HomeView
import com.yohana.echolearn.views.ListMusic
import com.yohana.echolearn.views.ListeningView
import com.yohana.echolearn.views.LoginView
import com.yohana.echolearn.views.RegisterView
import com.yohana.echolearn.views.SpeakingView
import com.yohana.echolearn.views.SplashScreenView
import com.yohana.echolearn.views.StarterView

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
    Speaking,
    Reading,
    Leaderboards
}

@Composable
fun AppRouting(
    context: Context,
    innerpadding: PaddingValues,
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    genreViewModel: GenreViewModel = viewModel(factory = GenreViewModel.Factory),
    listeningViewModel: ListeningViewModel = viewModel(factory = ListeningViewModel.Factory)
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
                    popUpTo(PagesEnum.Splash.name) {
                        inclusive = true
                    } // Remove splash from backstack
                }
            }
        }

        composable(route = PagesEnum.Starter.name) {
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

        composable(route = PagesEnum.SongMenu.name + "/{type}",
                arguments = listOf(
                    navArgument(name = "type"){
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")

            ListMusic(navController = navController, type = type!!, context = context)
        }

        composable(route = PagesEnum.Listening.name+"/{id}",
                arguments = listOf(
                    navArgument(name = "id"){
                        type = NavType.IntType
                    }
                )
            ) {
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
           ListeningView(
               viewModel = listeningViewModel,
               songId = id!!,
               type = PagesEnum.Listening.name
           )
        }

        composable(route = PagesEnum.Speaking.name+"/{id}" ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            SpeakingView()
        }

//        composable(route = PagesEnum.SongDetail.name+"/{type}/{genre}",
//            arguments = listOf(
//                navArgument(name = "genre"){
//                    type = NavType.StringType
//                },
//                navArgument(name = "type"){
//                    type = NavType.StringType
//                }
//            )
//            ) { backStackEntry ->
//            val genre = backStackEntry.arguments?.getString("genre")
//            val type = backStackEntry.arguments?.getString("type")
//
//            GenreView(
//                genre = genre!!,
//                viewModel = genreViewModel,
//                type = type!!
//            )
//        }

        composable(route = "{type}/"+PagesEnum.SongDetail.name+"/{genre}",
            arguments = listOf(
                navArgument(name = "genre"){
                    type = NavType.StringType
                },
                navArgument(name = "type"){
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val genre = backStackEntry.arguments?.getString("genre")
            val type = backStackEntry.arguments?.getString("type")

            GenreView(
                genre = genre!!,
                viewModel = genreViewModel,
                type = type!!,
                navController = navController
            )
        }

//        composable(route = PagesEnum.SongMenu.name) {
//           ListMusic()
//        }
    }
}