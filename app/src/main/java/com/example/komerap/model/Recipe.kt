package com.example.komerap.model

import com.example.komerapp.model.Ingredient

data class Recipe(val id: Int,
                  val title: String,
                  val extendedIngredients: List<Ingredient>?,
                  val instructions: String?,
                  val summary: String?,
                  val servings: Int,
                  val image: String?,
                  val readyInMinutes: String?,
                  val sourceUrl: String?,
                  val vegan: Boolean?,
                  val similary_recipe: List<Recipe>?) {
    // propriétés de la recette + fonctions pour ajouter/supprimer en favori

}