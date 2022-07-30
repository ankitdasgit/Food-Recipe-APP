 package ankit.recipeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ankit.recipeapp.Adapters.RandomRecipeAdapter;
import ankit.recipeapp.Listeners.RandomRepiceResposeListener;
import ankit.recipeapp.Listeners.RecipeClickListener;
import ankit.recipeapp.Models.RandomRecipeApiResponse;

 public class MainActivity extends AppCompatActivity {

     ProgressDialog dialog;
     ResquestManager manager;
     RandomRecipeAdapter randomRecipeAdapter;
     RecyclerView recyclerView;
     Spinner spinner;
     List<String> tags = new ArrayList<>();

     SearchView searchView;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         dialog = new ProgressDialog(this);
         dialog.setTitle("Loading...");
         dialog.show();



         searchView = findViewById(R.id.searchView_home);
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {

                 tags.clear();
                 tags.add(query);
                 manager.getRandomRecipes(randomRepiceResposeListener, tags);
                 dialog.show();
                 return true;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 return false;
             }
         });


         spinner = findViewById(R.id.spinner_tags);
         ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                 this,
                 R.array.tags,
                 R.layout.spinner_text
         );

         arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
         spinner.setAdapter(arrayAdapter);
         spinner.setOnItemSelectedListener(SpinnerSelectedListener);

         manager = new ResquestManager(this);
//        manager.getRandomRecipes(randomRepiceResposeListener);
//        dialog.show();
     }

     private final RandomRepiceResposeListener randomRepiceResposeListener = new RandomRepiceResposeListener() {
         @Override
         public void didFetch(RandomRecipeApiResponse response, String message) {

             recyclerView = findViewById(R.id.recycler_random);
             recyclerView.setHasFixedSize(true);
             recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
             randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes,recipeClickListener);
             recyclerView.setAdapter(randomRecipeAdapter);
         }

         @Override
         public void didError(String message) {
             Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
         }

     };


    private final AdapterView.OnItemSelectedListener  SpinnerSelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            tags.clear();
            tags.add(parent.getSelectedItem().toString());
            manager.getRandomRecipes(randomRepiceResposeListener, tags);
            dialog.show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private final RecipeClickListener recipeClickListener= new RecipeClickListener() {
        @Override
        public void OnRecipeClicked(String id) {
//            startActivities(new Intent(MainActivity.this,RecipeDetailsActivity.class).putExtra("id", id));
            startActivity( new Intent(MainActivity.this,RecipeDetailsActivity.class)
            .putExtra("id",id));
        }
    };

}