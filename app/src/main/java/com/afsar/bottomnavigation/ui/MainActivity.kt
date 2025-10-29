package com.afsar.bottomnavigation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.afsar.bottomnavigation.data.ToDo
import com.afsar.bottomnavigation.ui.theme.BottomNavigationTheme
import com.google.gson.Gson
import java.net.URLDecoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomNavigationTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { MyBottomNavBar(navController = navController) }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.List.route) {
            ListScreen(navController = navController)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("todoJson") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->

            val todoJson = backStackEntry.arguments?.getString("todoJson")


            val decodedJson = todoJson?.let { URLDecoder.decode(it, "UTF-8") }

            val todoItem = decodedJson?.let {
                Gson().fromJson(it, ToDo::class.java)
            }

            DetailScreen(navController = navController, todo = todoItem)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BottomNavigationTheme {

    }
}