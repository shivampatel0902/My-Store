package com.orderista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.orderista.models.CategoryModel;
import com.orderista.models.CategoryResponseModel;
import com.orderista.network.NetworkClient;
import com.orderista.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {
    RecyclerView categoriesRecyclerView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        imageView = findViewById(R.id.image_back);
        imageView.setOnClickListener(v -> CategoriesActivity.this.onBackPressed());
        categoriesRecyclerView=findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categoriesRecyclerView.setHasFixedSize(true);
        getCategories();
    }
    private  void getCategories()
    {
        ProgressDialog progressDialog=new ProgressDialog(CategoriesActivity.this);
        setTitle("Please Wait");
        progressDialog.setMessage("Getting Categories!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        NetworkService networkService= NetworkClient.getClient().create(NetworkService.class);
        Call<CategoryResponseModel> categoryResponseModelCall = networkService.getCategoriesFromServer();

        categoryResponseModelCall.enqueue(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call<CategoryResponseModel> call, Response<CategoryResponseModel> response) {
                CategoryResponseModel categoryResponseModel=response.body();
                CategoriesAdapter categoriesAdapter= new CategoriesAdapter(categoryResponseModel.getCategories());
                categoriesRecyclerView.setAdapter(categoriesAdapter);
                progressDialog.cancel();

            }

            @Override
            public void onFailure(Call<CategoryResponseModel> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    class  CategoryViewHolder extends RecyclerView.ViewHolder
    {
        CardView categoryItemLayout;
        TextView textCategory;
        CategoryViewHolder(View view)
        {
            super(view);
            categoryItemLayout=view.findViewById(R.id.category_card_view);
            textCategory=view.findViewById(R.id.text_category);
        }
    }
    class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder>
    {
        List<CategoryModel> categories;
        CategoriesAdapter (List<CategoryModel> categories)
        {
            this.categories=categories;
        }

        @Override
        public int getItemCount() {


            return categories.size();
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_container,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

            if(categories.get(holder.getAdapterPosition()).getCategory()!=null) {


                holder.textCategory.setText(categories.get(holder.getAdapterPosition()).getCategory());
                holder.categoryItemLayout.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
                    intent.putExtra("category", categories.get(holder.getAdapterPosition()).getCategory());
                    startActivity(intent);
                });
            }
        }
    }
}
