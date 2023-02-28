package com.example.komerap.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.komerap.model.Recipe
import com.example.komerap.viewModel.RecipeViewModel

class RecipeActivity {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun RecipeScreen(recipeViewModel: RecipeViewModel, recipeId : Int ) {
        val recipe = recipeViewModel.recipe.value
        recipeViewModel.getRecipe(recipeId)
        recipeViewModel.getSimilarRecipes(recipeId)

        Column() {
            Box {
                Image(
                    painter = rememberAsyncImagePainter("${recipe?.image}"),
                    contentDescription = "Recipe image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(Modifier.padding(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { /* TODO: Implement back button click */ }
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { /* TODO: Implement favorite button click */ }
                            .size(24.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = recipe?.title ?: "",
                            style = MaterialTheme.typography.h5
                        )
                        if (recipe?.vegan == true) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Vegan",
                                tint = MaterialTheme.colors.secondary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Lock,
                                contentDescription = "Preparation time",
                                tint = MaterialTheme.colors.secondary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${recipe?.readyInMinutes?.toInt() ?: 0} mins",
                                style = MaterialTheme.typography.subtitle2
                            )
                            Spacer(modifier = Modifier.width(30.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription = "Number of servings",
                                    tint = MaterialTheme.colors.secondary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${recipe?.servings}",
                                    style = MaterialTheme.typography.subtitle2
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                elevation = 10.dp,
                                modifier = Modifier.padding(10.dp),
                                backgroundColor = Color.Black.copy(alpha = 0.6f)
                            ) {
                                Text(
                                    text = "Share",
                                    color = Color.White,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            recipe?.extendedIngredients?.forEachIndexed { index, ingredient ->
                                Text(
                                    text = "- ${ingredient.name} : ${ingredient.amount} ${ingredient.unit}",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                                if (index != recipe.extendedIngredients.lastIndex) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Similar Recipes",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(recipeViewModel.similarRecipes.value) { similarRecipe ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Card(
                                        shape = RoundedCornerShape(20.dp),
                                        elevation = 10.dp,
                                        modifier = Modifier.padding(10.dp),
                                        backgroundColor = Color.Black.copy(alpha = 0.6f),
                                        onClick = { recipeViewModel.getRecipe(similarRecipe.id) }
                                    ) {
                                        Text(
                                            text = similarRecipe.title,
                                            color = Color.White,
                                            modifier = Modifier.padding(8.dp)
                                        )

                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        recipe?.sourceUrl?.let { url ->
                            Text(
                                text = "URL source",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            recipe.sourceUrl.let { url -> ClickableText(text = url, url = url) }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ClickableText(text: String, url: String) {
        val context = LocalContext.current
        Text(
            text = text,
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    ContextCompat.startActivity(context, intent, null)
                }
        )
    }
}
