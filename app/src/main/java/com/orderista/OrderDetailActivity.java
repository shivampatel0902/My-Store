package com.orderista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orderista.models.BookModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    RecyclerView order_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        order_recyclerview=findViewById(R.id.oredr_details_recycler_view);
        order_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
    private abstract class orderAdapter extends RecyclerView.Adapter<orderAdapter.orderViewHolder>{
        List<BookModel> products;
        orderAdapter(List<BookModel> products){
            this.products=products;
        }

        @NonNull
        @Override
        public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new orderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item_container, parent, false));

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
            if (products.get(position).getName() !=null && !products.get(position).getName().equals("")) {
                holder.product_name.setText(products.get(position).getName());
            }
            else{
                holder.product_name.setVisibility(View.GONE);
            }
            if (products.get(position).getPrice() !=null && !products.get(position).getPrice().equals("")) {
                holder.product_price.setText("Rs." + products.get(position).getPrice());
            }
            else{
                holder.product_price.setVisibility(View.GONE);
            }
            if (products.get(position).getImage() !=null && !products.get(position).getImage().equals("")) {
                Picasso.get().load(products.get(position).getImage()).into(holder.productImg);
            }
            else{
                holder.productImg.setVisibility(View.GONE);
            }
            holder.productCard.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
                intent.putExtra("id",products.get(holder.getAdapterPosition()).getId());
                intent.putExtra("name",products.get(holder.getAdapterPosition()).getName());
                intent.putExtra("image",products.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("price",products.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("description",products.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("category",products.get(holder.getAdapterPosition()).getCategory());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        class orderViewHolder extends RecyclerView.ViewHolder{
            CardView productCard;
            ImageView productImg;
            TextView product_name,product_price;
            orderViewHolder(View view) {
                super(view);
                productCard = view.findViewById(R.id.card_order_con);
                productImg = view.findViewById(R.id.image_vehicle);
                product_name = view.findViewById(R.id.text_order_name );
                product_price = view.findViewById(R.id.text_order_price);
            }
        }
    }
}
