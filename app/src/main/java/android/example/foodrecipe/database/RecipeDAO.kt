package android.example.foodrecipe.database

import android.example.foodrecipe.utils.Constants.RECIPE_TABLE
import androidx.constraintlayout.helper.widget.Flow
import androidx.room.*

@Dao
interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(noteEntity: RecipeEntity)

    @Update
    fun updateRecipe(noteEntity: RecipeEntity)

    @Delete
    fun deleteRecipe(noteEntity: RecipeEntity)

    @Query("SELECT * FROM $RECIPE_TABLE ORDER BY recipeId DESC")
    fun getRecipe(): MutableList<RecipeEntity>

    @Query("SELECT * FROM $RECIPE_TABLE WHERE recipeId LIKE :id")
    fun getRecipe(id: Int): RecipeEntity

    @Query("SELECT * FROM $RECIPE_TABLE WHERE recipeName LIKE :input OR recipeTypes LIKE :input")
    fun filterRecipe(input: String): MutableList<RecipeEntity>
}