package ankit.recipeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ankit.recipeapp.Models.ExtendedIngredient;
import ankit.recipeapp.R;

public class ingredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder>{

    Context context;
    List<ExtendedIngredient>list;

    public ingredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull IngredientsViewHolder holder, int position) {

        holder.textView_ingredients_name.setText(list.get(position).name);
        holder.textView_ingredients_name.setSelected(true);
        holder.textView_ingredients_quantity.setText(list.get(position).original);
        holder.textView_ingredients_quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.imageView_ingredients);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {

    TextView textView_ingredients_quantity,textView_ingredients_name;
    ImageView imageView_ingredients;
    public IngredientsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        textView_ingredients_quantity= itemView.findViewById(R.id.textView_ingredients_quantity);
        textView_ingredients_name= itemView.findViewById(R.id.textView_ingredients_name);

        imageView_ingredients= itemView.findViewById(R.id.imageView_ingredients);

    }
}