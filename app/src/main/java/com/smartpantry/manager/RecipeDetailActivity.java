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

        tvRecipeName.setText(
                getIntent().getStringExtra("recipe_name")
        );

        tvRecipeIngredients.setText(
                getIntent().getStringExtra("recipe_ingredients")
        );

        tvInstructions.setText(
                getIntent().getStringExtra("recipe_instructions")
        );

        btnBack.setOnClickListener(v -> finish());
    }
}