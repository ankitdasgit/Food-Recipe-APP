package ankit.recipeapp;

import android.content.Context;

import java.util.List;

import ankit.recipeapp.Listeners.RandomRepiceResposeListener;
import ankit.recipeapp.Listeners.RecipeDetailsListener;
import ankit.recipeapp.Models.RandomRecipeApiResponse;
import ankit.recipeapp.Models.RecipeDetailsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ResquestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ResquestManager(Context context) {
        this.context = context; }

        public void getRandomRecipes(RandomRepiceResposeListener listener, List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
            Call<RandomRecipeApiResponse> call = callRandomRecipes.CallRandomeRecipe(context.getString(R.string.api_key), "1000", tags);
            call.enqueue(new Callback<RandomRecipeApiResponse>() {
                @Override
                public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                    if(!response.isSuccessful()){
                        listener.didError(response.message());
                        return;
                    }
                    listener.didFetch(response.body(), response.message());
                }

                @Override
                public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                     listener.didError(t.getMessage());
                }
            });
        }



        public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails =retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call= callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFatch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
             listener.didError(t.getMessage());
            }
        });
        }

    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse>CallRandomeRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags")List<String>tags

                );

    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")

        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apikey
        );
    }

}
