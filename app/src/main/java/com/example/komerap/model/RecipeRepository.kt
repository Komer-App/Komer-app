package com.example.komerap.model

import com.example.komerapp.model.repository.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeService {
    @GET("recipes/{id}/information?apiKey=4e93072c818b4c94b994324cb8a474d0&includeNutrition=false")
    suspend fun getRecipe(@Path("id") id: Int): Response<Recipe>

    @GET("recipes/complexSearch?apiKey=4e93072c818b4c94b994324cb8a474d0")
    suspend fun searchRecipes(
        @Query("number") number: Int = 30,
        @Query("offset") offset: Int,
        @Query("query") query: String
    ): Response<SearchResult>

    @GET("recipes/{id}/similar?apiKey=4e93072c818b4c94b994324cb8a474d0")
    suspend fun getSimilarRecipes(@Path("id") id: Int): Response<List<Recipe>>

    @GET("recipes/random?apiKey=4e93072c818b4c94b994324cb8a474d0")
    suspend fun getRandomRecipe(): Response<Recipe>
}

data class SearchResult(val results: List<Recipe>)

class RecipeRepository: Repository() {
    private val apiService by lazy {
        retrofit.create(RecipeService::class.java)
    }

    suspend fun getRecipe(id: Int): Recipe {
        val response = apiService.getRecipe(id)

        return response.body().let { it } ?: throw Exception() //.copy(id = response.id, title = response.title, ingredients = response.ingredients, description = response.description, image = response.image, time_ready = response.time_ready, url_source = response.url_source,vegan = response.vegan, similary_recipe = response.similary_recipe)
    }

    suspend fun searchRecipes(query: String, number: Int = 30, offset: Int = 0): List<Recipe> {
        val response = apiService.searchRecipes(number, offset, query)

        return response.body()?.results ?: emptyList()
    }

    suspend fun similarRecipes(id: Int): List<Recipe> {
        val response = apiService.getSimilarRecipes(id)

        return response.body() ?: emptyList()
    }

    suspend fun getRandomRecipe(): Recipe {
        val response = apiService.getRandomRecipe()

        return response.body() ?: throw Exception()
    }


}