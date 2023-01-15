package android.example.foodrecipe.activities

import android.content.Intent
import android.example.foodrecipe.R
import android.example.foodrecipe.adapter.RecipeAdapter
import android.example.foodrecipe.database.RecipeDatabase
import android.example.foodrecipe.databinding.ActivityMainBinding
import android.example.foodrecipe.utils.Constants
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding

    private val recipeDatabase : RecipeDatabase by lazy {
        Room.databaseBuilder(this, RecipeDatabase::class.java, Constants.RECIPE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    public override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem() {
        binding.apply {
            if (recipeDatabase.doa().getRecipe().isNotEmpty()){
                rvRecipeList.visibility = View.VISIBLE
                tvEmptyText.visibility = View.GONE

                recipeAdapter.differ.submitList(recipeDatabase.doa().getRecipe())
                setupRecyclerView()
            } else {
                rvRecipeList.visibility = View.GONE
                tvEmptyText.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvRecipeList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recipeAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.search_button)
        val searchView = search?.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }

        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        recipeAdapter.differ.submitList(recipeDatabase.doa().filterRecipe(searchQuery))
        setupRecyclerView()
    }
}