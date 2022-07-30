package ankit.recipeapp.Listeners;

import ankit.recipeapp.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {

    void didFatch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
