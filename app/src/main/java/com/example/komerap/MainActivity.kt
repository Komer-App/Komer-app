package com.example.komerap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.komerap.model.Recipe
import com.example.komerap.ui.theme.KomerApTheme
import com.example.komerap.view.HomeActivity
import com.example.komerap.view.RecipeActivity
import com.example.komerap.view.auth.AuthScreen
import com.example.komerap.viewModel.RecipeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KomerApTheme() {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "authScreen") {
                    composable("recipe/{recipeId}") { backStackEntry ->
                        val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: 0
                        RecipeActivity().RecipeScreen(recipeViewModel = RecipeViewModel(), recipeId = recipeId)
                    }
                    composable("Home"){
                        HomeActivity().HomeScreen(viewModel = RecipeViewModel(), navController = navController )
                    }
                    composable("authScreen") { AuthScreen().AuthContent(navController) }
                }
            }
        }
    }
}


