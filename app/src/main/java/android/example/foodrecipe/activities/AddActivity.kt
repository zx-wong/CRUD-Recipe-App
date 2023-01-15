package android.example.foodrecipe.activities

import android.example.foodrecipe.R
import android.example.foodrecipe.database.RecipeDatabase
import android.example.foodrecipe.database.RecipeEntity
import android.example.foodrecipe.databinding.ActivityAddBinding
import android.example.foodrecipe.utils.Constants.RECIPE_DATABASE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    private lateinit var recipeCategories: String

    private val recipeDatabase : RecipeDatabase by lazy {
        Room.databaseBuilder(this, RecipeDatabase::class.java, RECIPE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private lateinit var recipeEntity: RecipeEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recipeImg: Int = 0

        binding.imageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                recipeImg = position
            }
        }

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                recipeCategories = binding.typeSpinner.getItemAtPosition(position).toString()
            }
        }

        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val ingredients = edtIngredients.text.toString()
                val steps = edtSteps.text.toString()

                if (title.isNotEmpty() && ingredients.isNotEmpty() && steps.isNotEmpty()){
                    recipeEntity = RecipeEntity(0, recipeImg, title, recipeCategories, ingredients, steps)
                    recipeDatabase.doa().insertRecipe(recipeEntity)
                    finish()

                } else {
                    if (title.isEmpty())
                    {
                        Snackbar.make(it, "Recipe Name Cannot Be Empty", Snackbar.LENGTH_LONG).show()
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
        }
    }
}
