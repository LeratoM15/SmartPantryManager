package com.smartpantry.manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView tvRecipeName =
                findViewById(R.id.tvRecipeName);

        TextView tvRecipeIngredients =
                findViewById(R.id.tvRecipeIngredients);

        TextView tvInstructions =
                findViewById(R.id.tvInstructions);

        Button btnBack =
                findViewById(R.id.btnBack);

        String recipeName =
                getIntent().getStringExtra("recipe_name");

        String ingredients =
                getIntent().getStringExtra("recipe_ingredients");

        String instructions =
                getIntent().getStringExtra("recipe_instructions");

        tvRecipeName.setText(
                recipeName != null
                        ? recipeName
                        : "Recipe"
        );

        tvRecipeIngredients.setText(
                ingredients != null
                        ? ingredients
                        : "No ingredients available."
        );

        tvInstructions.setText(
                instructions != null
                        ? instructions
                        : "No instructions available."
        );

        btnBack.setOnClickListener(v -> finish());
    }
}