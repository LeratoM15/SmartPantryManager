package com.smartpantry.manager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.smartpantry.manager.models.PantryItem;
import com.smartpantry.manager.models.Recipe;
import com.smartpantry.manager.models.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_PANTRY = "pantry_items";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY = "expiry_date";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COL_RECIPE_ID = "recipe_id";
    public static final String COL_RECIPE_NAME = "recipe_name";
    public static final String COL_INSTRUCTIONS = "instructions";
    public static final String COL_RECIPE_INGREDIENT_ID = "recipe_ingredient_id";
    public static final String COL_INGREDIENT_NAME = "ingredient_name";
    public static final String COL_REQUIRED_QUANTITY = "required_quantity";
    public static final String COL_REQUIRED_UNIT = "required_unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPantryTable =
                "CREATE TABLE " + TABLE_PANTRY + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT NOT NULL, " +
                        COL_QUANTITY + " REAL NOT NULL, " +
                        COL_UNIT + " TEXT NOT NULL, " +
                        COL_EXPIRY + " TEXT)";

        db.execSQL(createPantryTable);

        createRecipeTables(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 2) {
            createRecipeTables(db);
        }
    }

    private void createRecipeTables(SQLiteDatabase db) {

        String createRecipesTable =
                "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" +
                        COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_RECIPE_NAME + " TEXT NOT NULL, " +
                        COL_INSTRUCTIONS + " TEXT NOT NULL)";

        String createRecipeIngredientsTable =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_RECIPE_INGREDIENTS + " (" +
                        COL_RECIPE_INGREDIENT_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_RECIPE_ID +
                        " INTEGER NOT NULL, " +
                        COL_INGREDIENT_NAME +
                        " TEXT NOT NULL, " +
                        COL_REQUIRED_QUANTITY +
                        " REAL NOT NULL, " +
                        COL_REQUIRED_UNIT +
                        " TEXT NOT NULL)";

        db.execSQL(createRecipesTable);
        db.execSQL(createRecipeIngredientsTable);

        seedRecipes(db);
    }

    private void seedRecipes(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                null
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count > 0) {
            return;
        }

        long recipeId;

        recipeId = insertRecipe(
                db,
                "Fried Egg",
                "Heat oil in a pan, crack in the egg and cook until done."
        );
        insertRecipeIngredient(db, recipeId, "egg", 1, "items");
        insertRecipeIngredient(db, recipeId, "oil", 10, "ml");

        recipeId = insertRecipe(
                db,
                "Scrambled Eggs",
                "Beat the eggs with milk and cook gently with butter."
        );
        insertRecipeIngredient(db, recipeId, "egg", 2, "items");
        insertRecipeIngredient(db, recipeId, "milk", 30, "ml");
        insertRecipeIngredient(db, recipeId, "butter", 10, "g");

        recipeId = insertRecipe(
                db,
                "Cheese Omelette",
                "Beat eggs with milk, cook in a pan and add cheese before folding."
        );
        insertRecipeIngredient(db, recipeId, "egg", 2, "items");
        insertRecipeIngredient(db, recipeId, "cheese", 50, "g");
        insertRecipeIngredient(db, recipeId, "milk", 30, "ml");

        recipeId = insertRecipe(
                db,
                "Tomato Omelette",
                "Cook chopped tomato briefly, add beaten eggs and cook until set."
        );
        insertRecipeIngredient(db, recipeId, "egg", 2, "items");
        insertRecipeIngredient(db, recipeId, "tomato", 1, "items");
        insertRecipeIngredient(db, recipeId, "oil", 10, "ml");

        recipeId = insertRecipe(
                db,
                "Egg Sandwich",
                "Cook the egg and place it between two slices of buttered bread."
        );
        insertRecipeIngredient(db, recipeId, "egg", 1, "items");
        insertRecipeIngredient(db, recipeId, "bread", 2, "slices");
        insertRecipeIngredient(db, recipeId, "butter", 10, "g");

        recipeId = insertRecipe(
                db,
                "Cheese Sandwich",
                "Butter the bread, add cheese and close the sandwich."
        );
        insertRecipeIngredient(db, recipeId, "bread", 2, "slices");
        insertRecipeIngredient(db, recipeId, "cheese", 40, "g");
        insertRecipeIngredient(db, recipeId, "butter", 10, "g");

        recipeId = insertRecipe(
                db,
                "Tomato Sandwich",
                "Butter the bread, add sliced tomato and close the sandwich."
        );
        insertRecipeIngredient(db, recipeId, "bread", 2, "slices");
        insertRecipeIngredient(db, recipeId, "tomato", 1, "items");
        insertRecipeIngredient(db, recipeId, "butter", 10, "g");

        recipeId = insertRecipe(
                db,
                "Banana Toast",
                "Toast the bread, spread with butter and top with sliced banana."
        );
        insertRecipeIngredient(db, recipeId, "bread", 2, "slices");
        insertRecipeIngredient(db, recipeId, "banana", 1, "items");
        insertRecipeIngredient(db, recipeId, "butter", 10, "g");

        recipeId = insertRecipe(
                db,
                "Peanut Butter Toast",
                "Toast the bread and spread peanut butter evenly on top."
        );
        insertRecipeIngredient(db, recipeId, "bread", 2, "slices");
        insertRecipeIngredient(db, recipeId, "peanut butter", 30, "g");

        recipeId = insertRecipe(
                db,
                "Cereal and Milk",
                "Place cereal in a bowl and pour milk over it."
        );
        insertRecipeIngredient(db, recipeId, "cereal", 50, "g");
        insertRecipeIngredient(db, recipeId, "milk", 200, "ml");

        recipeId = insertRecipe(
                db,
                "Banana Cereal",
                "Add cereal and milk to a bowl and top with sliced banana."
        );
        insertRecipeIngredient(db, recipeId, "cereal", 50, "g");
        insertRecipeIngredient(db, recipeId, "milk", 200, "ml");
        insertRecipeIngredient(db, recipeId, "banana", 1, "items");

        recipeId = insertRecipe(
                db,
                "Tomato Pasta",
                "Cook pasta, sauté tomato in oil and combine."
        );
        insertRecipeIngredient(db, recipeId, "pasta", 100, "g");
        insertRecipeIngredient(db, recipeId, "tomato", 2, "items");
        insertRecipeIngredient(db, recipeId, "oil", 10, "ml");

        recipeId = insertRecipe(
                db,
                "Cheese Pasta",
                "Cook pasta and stir through cheese and milk until creamy."
        );
        insertRecipeIngredient(db, recipeId, "pasta", 100, "g");
        insertRecipeIngredient(db, recipeId, "cheese", 50, "g");
        insertRecipeIngredient(db, recipeId, "milk", 50, "ml");

        recipeId = insertRecipe(
                db,
                "Rice and Eggs",
                "Cook the rice, fry the eggs and serve them together."
        );
        insertRecipeIngredient(db, recipeId, "rice", 100, "g");
        insertRecipeIngredient(db, recipeId, "egg", 2, "items");
        insertRecipeIngredient(db, recipeId, "oil", 10, "ml");

        recipeId = insertRecipe(
                db,
                "Simple Salad",
                "Chop the vegetables, place in a bowl and drizzle with oil."
        );
        insertRecipeIngredient(db, recipeId, "lettuce", 100, "g");
        insertRecipeIngredient(db, recipeId, "tomato", 1, "items");
        insertRecipeIngredient(db, recipeId, "cucumber", 1, "items");
        insertRecipeIngredient(db, recipeId, "oil", 10, "ml");
    }
    private long insertRecipe(
            SQLiteDatabase db,
            String name,
            String instructions) {

        ContentValues values = new ContentValues();

        values.put(COL_RECIPE_NAME, name);
        values.put(COL_INSTRUCTIONS, instructions);

        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }

    private void insertRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String name,
            double quantity,
            String unit) {

        ContentValues values = new ContentValues();

        values.put(COL_RECIPE_ID, recipeId);
        values.put(COL_INGREDIENT_NAME, name);
        values.put(COL_REQUIRED_QUANTITY, quantity);
        values.put(COL_REQUIRED_UNIT, unit);

        db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }
    // CREATE
    public long addPantryItem(PantryItem item) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_QUANTITY, item.getQuantity());
        values.put(COL_UNIT, item.getUnit());
        values.put(COL_EXPIRY, item.getExpiryDate());

        return db.insert(TABLE_PANTRY, null, values);
    }

    // READ
    public List<PantryItem> getAllPantryItems() {

        List<PantryItem> pantryItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(COL_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_UNIT)
                );

                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_EXPIRY)
                );

                PantryItem item = new PantryItem(
                        id,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

                pantryItems.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return pantryItems;
    }

    // UPDATE
    public int updatePantryItem(PantryItem item) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_QUANTITY, item.getQuantity());
        values.put(COL_UNIT, item.getUnit());
        values.put(COL_EXPIRY, item.getExpiryDate());

        return db.update(
                TABLE_PANTRY,
                values,
                COL_ID + " = ?",
                new String[]{String.valueOf(item.getId())}
        );
    }

    // DELETE
    public int deletePantryItem(int id) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_PANTRY,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }
    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                COL_RECIPE_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int recipeId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COL_RECIPE_ID)
                );

                String recipeName = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_RECIPE_NAME)
                );

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_INSTRUCTIONS)
                );

                List<RecipeIngredient> ingredients =
                        getRecipeIngredients(db, recipeId);

                Recipe recipe = new Recipe(
                        recipeId,
                        recipeName,
                        instructions,
                        ingredients
                );

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return recipes;
    }
    private List<RecipeIngredient> getRecipeIngredients(
            SQLiteDatabase db,
            int recipeId) {

        List<RecipeIngredient> ingredients = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                COL_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String ingredientName = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_INGREDIENT_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(COL_REQUIRED_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_REQUIRED_UNIT)
                );

                RecipeIngredient ingredient =
                        new RecipeIngredient(
                                ingredientName,
                                quantity,
                                unit
                        );

                ingredients.add(ingredient);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return ingredients;
    }
}