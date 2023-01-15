package android.example.foodrecipe.database

import android.example.foodrecipe.utils.Constants.RECIPE_TABLE
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RECIPE_TABLE)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Int,

    val recipeImg: Int,
    val recipeName: String,
    val recipeTypes: String,
    val recipeIngredients: String,
    val recipeSteps: String
)
