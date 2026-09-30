package com.smartpantry.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.database.DatabaseHelper;
import com.smartpantry.manager.models.PantryItem;
import com.smartpantry.manager.models.Recipe;
import com.smartpantry.manager.models.RecipeIngredient;
import com.smartpantry.manager.utils.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView listRecipes;
    private TextView tvNoRecipes;

    private final List<Recipe> matchedRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        listRecipes = findViewById(R.id.listRecipes);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);

        Button btnBackToPantry =
                findViewById(R.id.btnBackToPantry);

        btnBackToPantry.setOnClickListener(v -> finish());

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

        List<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        List<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        matchedRecipes.clear();

        for (Recipe recipe : allRecipes) {

            if (RecipeMatcher.canMakeRecipe(
                    recipe,
                    pantryItems)) {

                matchedRecipes.add(recipe);
            }
        }

        if (matchedRecipes.isEmpty()) {

            tvNoRecipes.setVisibility(View.VISIBLE);
            listRecipes.setVisibility(View.GONE);

        } else {

            tvNoRecipes.setVisibility(View.GONE);
            listRecipes.setVisibility(View.VISIBLE);

            List<String> recipeNames = new ArrayList<>();

            for (Recipe recipe : matchedRecipes) {
                recipeNames.add(recipe.getName());
            }

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_list_item_1,
                            recipeNames
                    );

            listRecipes.setAdapter(adapter);
        }

        listRecipes.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Recipe selected =
                            matchedRecipes.get(position);

                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailActivity.class
                    );

                    intent.putExtra(
                            "recipe_name",
                            selected.getName()
                    );

                    intent.putExtra(
                            "recipe_instructions",
                            selected.getInstructions()
                    );

                    StringBuilder ingredients =
                            new StringBuilder();

                    for (RecipeIngredient ingredient :
                            selected.getIngredients()) {

                        ingredients
                                .append("• ")
                                .append(ingredient.getName())
                                .append(" - ")
                                .append(ingredient.getQuantity())
                                .append(" ")
                                .append(ingredient.getUnit())
                                .append("\n");
                    }

                    intent.putExtra(
                            "recipe_ingredients",
                            ingredients.toString()
                    );

                    startActivity(intent);
                }
        );
    }
}