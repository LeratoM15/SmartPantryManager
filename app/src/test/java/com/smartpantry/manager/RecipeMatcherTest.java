package com.smartpantry.manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.smartpantry.manager.models.PantryItem;
import com.smartpantry.manager.models.Recipe;
import com.smartpantry.manager.models.RecipeIngredient;
import com.smartpantry.manager.utils.RecipeMatcher;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RecipeMatcherTest {

    @Test
    public void recipeMatchesWhenAllIngredientsAreAvailable() {

        List<PantryItem> pantry = Arrays.asList(
                new PantryItem("Milk", 1, "L", ""),
                new PantryItem("Cereal", 100, "g", "")
        );

        Recipe recipe = new Recipe(
                1,
                "Cereal and Milk",
                "Combine cereal and milk.",
                Arrays.asList(
                        new RecipeIngredient("cereal", 50, "g"),
                        new RecipeIngredient("milk", 200, "ml")
                )
        );

        assertTrue(
                RecipeMatcher.canMakeRecipe(recipe, pantry)
        );
    }

    @Test
    public void recipeFailsWhenQuantityIsInsufficient() {

        List<PantryItem> pantry = Arrays.asList(
                new PantryItem("Milk", 1, "L", ""),
                new PantryItem("Cereal", 20, "g", "")
        );

        Recipe recipe = new Recipe(
                1,
                "Cereal and Milk",
                "Combine cereal and milk.",
                Arrays.asList(
                        new RecipeIngredient("cereal", 50, "g"),
                        new RecipeIngredient("milk", 200, "ml")
                )
        );

        assertFalse(
                RecipeMatcher.canMakeRecipe(recipe, pantry)
        );
    }

    @Test
    public void pluralIngredientNamesAreHandled() {

        List<PantryItem> pantry = Arrays.asList(
                new PantryItem("Eggs", 3, "items", "")
        );

        Recipe recipe = new Recipe(
                1,
                "Egg Test",
                "Cook eggs.",
                Arrays.asList(
                        new RecipeIngredient("egg", 2, "items")
                )
        );

        assertTrue(
                RecipeMatcher.canMakeRecipe(recipe, pantry)
        );
    }
}