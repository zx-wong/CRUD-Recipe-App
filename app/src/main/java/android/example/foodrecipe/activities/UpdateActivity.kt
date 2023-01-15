package android.example.foodrecipe.activities

import android.example.foodrecipe.R
import android.example.foodrecipe.database.RecipeDatabase
import android.example.foodrecipe.database.RecipeEntity
import android.example.foodrecipe.databinding.ActivityUpdateBinding
import android.example.foodrecipe.utils.Constants
import android.example.foodrecipe.utils.Constants.RECIPE_ID_BUNDLE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    var images = arrayOf(R.drawable.roast_chicken, R.drawable.roasted_pork_loin, R.drawable.meatloaf, R.drawable.grilled_lamb_chop,
        R.drawable.chicken_casserole, R.drawable.grilled_salmon, R.drawable.chicken_soup)

    private val recipeDatabase : RecipeDatabase by lazy {
        Room.databaseBuilder(this, RecipeDatabase::class.java, Constants.RECIPE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private lateinit var recipeEntity: RecipeEntity

    private var recipeId = 0
    private var defaultImg = 0
    private var defaultName = ""
    private var defaultTypes = ""
    private var defaultIngredients = ""
    private var defaultSteps = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageViewer = binding.imagePlacement

        intent.extras?.let {
            recipeId = it.getInt(RECIPE_ID_BUNDLE)
        }

        binding.apply {
            defaultImg = recipeDatabase.doa().getRecipe(recipeId).recipeImg
            defaultName = recipeDatabase.doa().getRecipe(recipeId).recipeName
            defaultTypes = recipeDatabase.doa().getRecipe(recipeId).recipeTypes
            defaultIngredients = recipeDatabase.doa().getRecipe(recipeId).recipeIngredients
            defaultSteps = recipeDatabase.doa().getRecipe(recipeId).recipeSteps

            edtTitle.setText(defaultName)
            edtTypes.setText(defaultTypes)
            edtIngredients.setText(defaultIngredients)
            edtSteps.setText(defaultSteps)

            imageViewer.setImageResource(images[defaultImg])

            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val types = edtTypes.text.toString()
                val ingredients = edtIngredients.text.toString()
                val steps = edtSteps.text.toString()

                if (title.isNotEmpty() && ingredients.isNotEmpty() && steps.isNotEmpty()){
                    recipeEntity = RecipeEntity(recipeId, defaultImg, title, types, ingredients, steps)
                    recipeDatabase.doa().updateRecipe(recipeEntity)
                    finish()

                } else {
                    if (title.isEmpty())
                    {
                        Snackbar.make(it, "Recipe Name Cannot Be Empty", Snackbar.LENGTH_LONG).show()
                    }

                    if (types.isEmpty())
                    {
                        Snackbar.make(it, "Recipe Types Cannot Be Empty", Snackbar.LENGTH_LONG).show()
                    }

                    if (ingredients.isEmpty())
                    {
                        Snackbar.make(it, "Recipe Ingredients Cannot Be Empty", Snackbar.LENGTH_LONG).show()
                    }

                    if (steps.isEmpty())
                    {
                        Snackbar.make(it, "Recipe Steps Cannot Be Empty", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

            btnDelete.setOnClickListener {
                recipeEntity = RecipeEntity(recipeId, defaultImg, defaultName, defaultTypes, defaultIngredients, defaultSteps)
                recipeDatabase.doa().deleteRecipe(recipeEntity)
                finish()
            }
        }
    }
}