package com.smartpantry.manager.utils;

import com.smartpantry.manager.models.PantryItem;
import com.smartpantry.manager.models.Recipe;
import com.smartpantry.manager.models.RecipeIngredient;

import java.util.List;

public class RecipeMatcher {

    public static boolean canMakeRecipe(
            Recipe recipe,
            List<PantryItem> pantryItems) {

        for (RecipeIngredient required : recipe.getIngredients()) {

            boolean ingredientSatisfied = false;

            for (PantryItem pantryItem : pantryItems) {

                String pantryName =
                        normalizeIngredient(pantryItem.getName());

                String requiredName =
                        normalizeIngredient(required.getName());

                if (pantryName.equals(requiredName)) {

                    double pantryQuantity =
                            convertToBaseUnit(
                                    pantryItem.getQuantity(),
                                    pantryItem.getUnit()
                            );

                    double requiredQuantity =
                            convertToBaseUnit(
                                    required.getQuantity(),
                                    required.getUnit()
                            );

                    if (unitsCompatible(
                            pantryItem.getUnit(),
                            required.getUnit())
                            && pantryQuantity >= requiredQuantity) {

                        ingredientSatisfied = true;
                        break;
                    }
                }
            }

            // One missing or insufficient ingredient rejects the recipe.
            if (!ingredientSatisfied) {
                return false;
            }
        }

        return true;
    }

    private static String normalizeIngredient(String value) {

        if (value == null) {
            return "";
        }

        value = value
                .trim()
                .toLowerCase();

        switch (value) {

            case "eggs":
                return "egg";

            case "tomatoes":
                return "tomato";

            case "potatoes":
                return "potato";

            case "bananas":
                return "banana";

            case "onions":
                return "onion";

            case "cucumbers":
                return "cucumber";

            default:
                return value;
        }
    }

    private static double convertToBaseUnit(
            double quantity,
            String unit) {

        if (unit == null) {
            return quantity;
        }

        switch (unit.trim().toLowerCase()) {

            case "kg":
                return quantity * 1000;

            case "g":
                return quantity;

            case "l":
            case "litre":
            case "litres":
                return quantity * 1000;

            case "ml":
                return quantity;

            default:
                return quantity;
        }
    }

    private static boolean unitsCompatible(
            String pantryUnit,
            String recipeUnit) {

        if (pantryUnit == null || recipeUnit == null) {
            return false;
        }

        String p = pantryUnit.trim().toLowerCase();
        String r = recipeUnit.trim().toLowerCase();

        if (p.equals(r)) {
            return true;
        }

        boolean pantryWeight =
                p.equals("g") || p.equals("kg");

        boolean recipeWeight =
                r.equals("g") || r.equals("kg");

        if (pantryWeight && recipeWeight) {
            return true;
        }

        boolean pantryLiquid =
                p.equals("ml")
                        || p.equals("l")
                        || p.equals("litre")
                        || p.equals("litres");

        boolean recipeLiquid =
                r.equals("ml")
                        || r.equals("l")
                        || r.equals("litre")
                        || r.equals("litres");

        if (pantryLiquid && recipeLiquid) {
            return true;
        }

        return false;
    }
}