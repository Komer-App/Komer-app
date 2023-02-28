package com.example.komerap.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.komerap.model.Recipe
import com.example.komerap.viewModel.RecipeViewModel

class HomeActivity {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun HomeScreen(viewModel: RecipeViewModel, navController: NavHostController) {
        var searchQuery by remember { mutableStateOf("") }
        val searchResult: List<Recipe> by viewModel.recipes.observeAsState(emptyList())
        viewModel.getRandomRecipes()
        Scaffold(
            bottomBar = { BottomNavBar() }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    label = { Text("Que voulez-vous rechercher ?") },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        searchQuery = it
                        viewModel.searchRecipes(query = searchQuery)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Résultat de la recherche")

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (searchResult.isNotEmpty()) {
                        items(searchResult.size) { index ->
                            RecipeListItem(recipe = searchResult[index], navController)
                        }
                    } else if (searchQuery.isNotEmpty()) {
                        item {
                            Text(text = "Aucune recette trouvée.")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(128.dp))

                Column {
                    Text(
                        text = "Random Recipe",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    val randomRecipe = viewModel.randomRecipe.value

                    /*
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = 10.dp,
                        modifier = Modifier.padding(10.dp),
                        backgroundColor = Color.Black.copy(alpha = 0.6f),
                        onClick = {
                            if (randomRecipe != null) {
                                viewModel.getRecipe(randomRecipe.id)
                            }
                        }
                    ) {
                        if (randomRecipe != null) {
                            Text(
                                text = randomRecipe.title,
                                color = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                     */

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    @Composable
    fun RecipeListItem(recipe: Recipe, navController: NavHostController) {
        var recipeId: Int? = null
        Row(
            modifier = Modifier
                .clickable {
                    recipeId = recipe.id
                    Log.d("TAG", "GRZGZGZEG EEHH OOHHH $recipeId")
                    navController.navigate("recipe/${recipeId}")
                }
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = recipe.title,
                    fontWeight= FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "id : ${recipe.id}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun BottomNavBar() {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Profile
        )

        BottomNavigation {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = false,
                    onClick = {}
                )
            }
        }
    }

    sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
        object Home : BottomNavItem("Home", "Accueil", Icons.Filled.Home)
        object Profile : BottomNavItem("profile", "Profil", Icons.Filled.Person)
    }


}
