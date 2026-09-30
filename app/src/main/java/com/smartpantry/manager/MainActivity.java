package com.smartpantry.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.manager.adapters.PantryAdapter;
import com.smartpantry.manager.database.DatabaseHelper;
import com.smartpantry.manager.models.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView tvEmpty;

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;

    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;

    private List<PantryItem> pantryItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        tvEmpty = findViewById(R.id.tvEmpty);

        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        databaseHelper = new DatabaseHelper(this);

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        pantryAdapter = new PantryAdapter(
                pantryItems,
                new PantryAdapter.OnPantryItemActionListener() {

                    @Override
                    public void onEdit(PantryItem item) {

                        Intent intent = new Intent(
                                MainActivity.this,
                                AddEditIngredientActivity.class
                        );

                        intent.putExtra(
                                "item_id",
                                item.getId()
                        );

                        intent.putExtra(
                                "item_name",
                                item.getName()
                        );

                        intent.putExtra(
                                "item_quantity",
                                item.getQuantity()
                        );

                        intent.putExtra(
                                "item_unit",
                                item.getUnit()
                        );

                        intent.putExtra(
                                "item_expiry",
                                item.getExpiryDate()
                        );

                        startActivity(intent);
                    }

                    @Override
                    public void onDelete(PantryItem item) {
                        confirmDelete(item);
                    }
                }
        );

        recyclerPantry.setAdapter(pantryAdapter);

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            startActivity(intent);
        });

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {

        pantryItems =
                databaseHelper.getAllPantryItems();

        pantryAdapter.updateItems(pantryItems);

        if (pantryItems.isEmpty()) {

            tvEmpty.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);

        } else {

            tvEmpty.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }
    }

    private void confirmDelete(PantryItem item) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage(
                        "Are you sure you want to delete "
                                + item.getName() + "?"
                )
                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            databaseHelper.deletePantryItem(
                                    item.getId()
                            );

                            Toast.makeText(
                                    MainActivity.this,
                                    "Ingredient deleted",
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadPantryItems();
                        }
                )
                .setNegativeButton(
                        "Cancel",
                        null
                )
                .show();
    }
}