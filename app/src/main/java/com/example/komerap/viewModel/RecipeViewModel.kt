package com.example.komerap.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.komerap.model.Recipe
import com.example.komerap.model.RecipeRepository
import com.example.komerap.model.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository: RecipeRepository = RecipeRepository()) : ViewModel() {

    val recipe = mutableStateOf<Recipe?>(null)
    val error = mutableStateOf<String?>(null)

    val randomRecipe = mutableStateOf<Recipe?>(null)
    val similarRecipes = mutableStateOf<List<Recipe>>(emptyList())
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes




    fun getRecipe(id: Int) {
        viewModelScope.launch {
            try {
                recipe.value = recipeRepository.getRecipe(id)
            } catch (e: Exception) {
                error.value = e.message ?: "Error Message"
            }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                _recipes.value = recipeRepository.searchRecipes(query = query)
            } catch (e: Exception) {
                error.value = e.message ?: "Error Message"
            }
        }
    }

    fun getSimilarRecipes(id: Int) {
        viewModelScope.launch {
            try {
                similarRecipes.value = recipeRepository.similarRecipes(id)
            } catch (e: Exception) {
                error.value = e.message ?: "Error Message"
            }
        }
    }

    fun getRandomRecipes() {
        viewModelScope.launch {
            try {
                randomRecipe.value = recipeRepository.getRandomRecipe()
            } catch (e: Exception) {
                error.value = e.message ?: "Error Message"
            }
        }
    }

}
