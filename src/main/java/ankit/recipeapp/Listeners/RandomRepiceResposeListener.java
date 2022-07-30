package ankit.recipeapp.Listeners;

import ankit.recipeapp.Models.RandomRecipeApiResponse;

public interface RandomRepiceResposeListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
