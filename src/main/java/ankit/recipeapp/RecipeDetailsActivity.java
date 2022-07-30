package ankit.recipeapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ankit.recipeapp.Adapters.ingredientsAdapter;
import ankit.recipeapp.Listeners.RecipeDetailsListener;
import ankit.recipeapp.Models.Ingredient;
import ankit.recipeapp.Models.RecipeDetailsResponse;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView textView_meal_name,textview_meal_source,textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredient;
    ResquestManager manager;
    ProgressDialog dialog;
    ingredientsAdapter ingredientsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();


      id= Integer.parseInt(getIntent().getStringExtra("id"));
      manager= new ResquestManager(this);
      manager.getRecipeDetails(recipeDetailsListener, id);
      dialog = new ProgressDialog(this);
      dialog.setTitle("Loading Details...");
      dialog.show();

    }

    private void findViews() {

        textView_meal_name= findViewById(R.id.textView_meal_name);
        textview_meal_source = findViewById(R.id.textview_meal_source);
        textView_meal_summary= findViewById(R.id.textView_meal_summary);
        imageView_meal_image= findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredient= findViewById(R.id.recycler_meal_ingredient);

    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFatch(RecipeDetailsResponse response, String message) {
         dialog.dismiss();
         textView_meal_name.setText(response.title);
         textview_meal_source.setText(response.sourceName);
         textView_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_meal_image);
           recycler_meal_ingredient.setHasFixedSize(true);
           recycler_meal_ingredient.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL,false));

            ingredientsAdapter = new ingredientsAdapter(RecipeDetailsActivity.this,response.extendedIngredients);
            recycler_meal_ingredient.setAdapter( ingredientsAdapter);

        }

        @Override
        public void didError(String message) {

            Toast.makeText(RecipeDetailsActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    };
}