package com.orderista;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.orderista.database.DatabaseHandler;
import com.orderista.models.BookModel;
import com.orderista.models.PlaceOrderResponse;
import com.orderista.network.NetworkClient;
import com.orderista.network.NetworkService;
import com.orderista.utils.Constance;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    RecyclerView booksRecyclerView;
    ImageView imageView;
    //EditText quantity;
    //Integer paisa;

    Button btn_place_order;
    TextView textTotalAmount;
    SharedPreferences sharedPreferences;
    int total_amount=0;

    List<BookModel> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences(Constance.PREFRENCE_NAME,0);

        setContentView(R.layout.activity_cart);

        imageView = findViewById(R.id.image_back);
        textTotalAmount=findViewById(R.id.text_total_amount);
       // quantity=findViewById(R.id.quantity);
       // paisa = quantity.getText().length();
        btn_place_order=findViewById(R.id.btn_place_order);
        imageView.setOnClickListener(v -> onBackPressed());
        booksRecyclerView = findViewById(R.id.cart_recycler_view);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        booksRecyclerView.setHasFixedSize(true);

        cartItems  = new DatabaseHandler(getApplicationContext()).getCartItems();
        booksRecyclerView.setAdapter(new BooksAdapter(cartItems));

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_email", sharedPreferences.getString(Constance.EMAIL, "N/A"));
                params.put("total_amount", String.valueOf(total_amount));
                params.put("products", new Gson().toJson(cartItems));
                params.put("order_date", new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
                CartActivity.this.placeOrder(params);


            }
        });


        calculateTotal();

    }

    private void placeOrder(HashMap<String, String> params)
    {
        final ProgressDialog progressDialog=new ProgressDialog(CartActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Placing Order...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<PlaceOrderResponse> placeOrderCall =networkService.placeOrder(params);
        placeOrderCall.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {
                PlaceOrderResponse responceBody=response.body();
                if (responceBody != null)
                {
                    if (responceBody.getSuccess().equals("1"))
                    {
                        Toast.makeText(CartActivity.this,responceBody.getMessage(), Toast.LENGTH_LONG).show();
                        DatabaseHandler databasehandler = new DatabaseHandler(getApplicationContext());
                        databasehandler.deleteCart();
                        finish();
                    }else
                    {
                        Toast.makeText(CartActivity.this, responceBody.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {
                 progressDialog.dismiss();
            }
        });

    }
    private void calculateTotal()
    {
         total_amount=0;

        for (int i=0; i<=cartItems.size()-1;i++)
        {

            //paisa= paisa*Integer.parseInt((cartItems.get(i).getPrice()));
            total_amount=Integer.parseInt(cartItems.get(i).getPrice());
        }
        textTotalAmount.setText("TOTAL AMOUNT:\u20B9 "+ total_amount);

    }


    private class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
        List<BookModel> books;

        BooksAdapter(List<BookModel> books) {
            this.books = books;
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_container, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            if (books.get(position).getName() != null && !books.get(position).getName().equals("")) {
                holder.textName.setText(books.get(position).getName());

            } else {
                holder.textName.setVisibility(View.GONE);
            }
            if (books.get(position).getPrice() != null && !books.get(position).getPrice().equals("")) {
                holder.textPrice.setText("\u20B9" + books.get(position).getPrice());

            } else {
                holder.textPrice.setVisibility(View.GONE);
            }

            /*if (books.get(position).getAuthor() != null && !books.get(position).getAuthor().equals("")) {
                holder.textAuthor.setText(books.get(position).getAuthor());
            } else {
                holder.textAuthor.setVisibility(View.GONE);
            }
*/

            if (books.get(position).getImage() != null && !books.get(position).getImage().equals("")) {


                Picasso.get().load(books.get(position).getImage()).into(holder.imageBook);
            }
            else{
                holder.imageBook.setVisibility(View.GONE);
            }

            holder.textRemove.setPaintFlags(holder.textRemove.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.textRemove.setOnClickListener(v -> {
                new DatabaseHandler(getApplicationContext()).removeItem(books.get(holder.getAdapterPosition()).getId());
                cartItems.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),cartItems.size());
                calculateTotal();
            });

        }


        class BookViewHolder extends RecyclerView.ViewHolder {
            ImageView imageBook;
            TextView textName, textAuthor, textPrice, textRemove;

            BookViewHolder(View view) {
                super(view);
                imageBook = view.findViewById(R.id.image_book);
                textName = view.findViewById(R.id.text_book_name);
               // textAuthor = view.findViewById(R.id.text_book_author);
                textPrice = view.findViewById(R.id.book_Price);
                textRemove = view.findViewById(R.id.text_remove);


            }
        }
    }



}
