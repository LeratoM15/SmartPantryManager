package com.smartpantry.manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.database.DatabaseHelper;
import com.smartpantry.manager.models.PantryItem;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;
    private EditText etExpiryDate;

    private TextView tvFormTitle;

    private DatabaseHelper databaseHelper;

    private int pantryItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        tvFormTitle = findViewById(R.id.tvFormTitle);
        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiryDate = findViewById(R.id.etExpiryDate);

        Button btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        Button btnCancel = findViewById(R.id.btnCancel);

        databaseHelper = new DatabaseHelper(this);

        pantryItemId = getIntent().getIntExtra("item_id", -1);

        if (pantryItemId != -1) {

            tvFormTitle.setText("Edit Ingredient");

            etIngredientName.setText(
                    getIntent().getStringExtra("item_name")
            );

            etQuantity.setText(
                    String.valueOf(
                            getIntent().getDoubleExtra(
                                    "item_quantity",
                                    0
                            )
                    )
            );

            etUnit.setText(
                    getIntent().getStringExtra("item_unit")
            );

            etExpiryDate.setText(
                    getIntent().getStringExtra("item_expiry")
            );
        }

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveIngredient() {

        String name =
                etIngredientName.getText().toString().trim();

        String quantityText =
                etQuantity.getText().toString().trim();

        String unit =
                etUnit.getText().toString().trim();

        String expiryDate =
                etExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            etIngredientName.setError("Ingredient name is required");
            etIngredientName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            etQuantity.setError("Quantity is required");
            etQuantity.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            etQuantity.setError("Enter a valid quantity");
            return;
        }

        if (quantity <= 0) {
            etQuantity.setError("Quantity must be greater than 0");
            return;
        }

        if (unit.isEmpty()) {
            etUnit.setError("Unit is required");
            etUnit.requestFocus();
            return;
        }

        if (pantryItemId == -1) {

            PantryItem item = new PantryItem(
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            databaseHelper.addPantryItem(item);

            Toast.makeText(
                    this,
                    "Ingredient added",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            PantryItem item = new PantryItem(
                    pantryItemId,
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            databaseHelper.updatePantryItem(item);

            Toast.makeText(
                    this,
                    "Ingredient updated",
                    Toast.LENGTH_SHORT
            ).show();
        }

        finish();
    }
}