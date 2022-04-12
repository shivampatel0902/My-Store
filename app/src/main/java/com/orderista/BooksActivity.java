package com.orderista;

import android.app.ProgressDialog;
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
import com.orderista.models.BookResponceModel;
import com.orderista.network.NetworkClient;
import com.orderista.network.NetworkService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends AppCompatActivity {
    RecyclerView booksRecyclerView;
    ImageView imageView;
    TextView textToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        textToolbar = findViewById(R.id.text_toolbar);
        textToolbar.setText(getIntent().getStringExtra("category"));
        booksRecyclerView = findViewById(R.id.book_recycler_view);
        imageView = findViewById(R.id.image_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BooksActivity.this.onBackPressed();
            }
        });
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        booksRecyclerView.setHasFixedSize(true);
        getBooks();

    }

    private void getBooks() {
        ProgressDialog progressDialog = new ProgressDialog(BooksActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Getting Items!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<BookResponceModel> getBook = networkService.getBookByCategories(getIntent().getStringExtra("category"));
        getBook.enqueue(new Callback<BookResponceModel>() {
            @Override
            public void onResponse(Call<BookResponceModel> call, Response<BookResponceModel> response) {
                progressDialog.cancel();
                BooksAdapter booksAdapter = new BooksAdapter(response.body().getBooks());
                booksRecyclerView.setAdapter(booksAdapter);

            }

            @Override
            public void onFailure(Call<BookResponceModel> call, Throwable t) {
                progressDialog.cancel();
            }
        });


    }

    class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
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

            return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_container, parent, false));
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

            if (books.get(position).getAuthor() != null && !books.get(position).getAuthor().equals("")) {
                holder.textAuthor.setText(books.get(position).getAuthor());
            } else {
                holder.textAuthor.setVisibility(View.GONE);
            }

            if (books.get(position).getDescription() != null && !books.get(position).getDescription().equals("")) {
                holder.txtDescription.setText(books.get(position).getAuthor());
            } else {
                holder.txtDescription.setVisibility(View.GONE);
            }
            if (books.get(position).getImage() != null && !books.get(position).getImage().equals("")) {


                Picasso.get().load(books.get(position).getImage()).into(holder.imageBook);
            }
            else{
                holder.imageBook.setVisibility(View.GONE);
            }
            holder.cardBook.setOnClickListener(v -> {
                Intent intent=new Intent(getApplicationContext(),BookDetailActivity.class);
                intent.putExtra("image", books.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("author", books.get(holder.getAdapterPosition()).getAuthor());
                intent.putExtra("publishYear", books.get(holder.getAdapterPosition()).getPublishedYear());
                intent.putExtra("description", books.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("price",books.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("name",books.get(holder.getAdapterPosition()).getName());
                intent.putExtra("id",books.get(holder.getAdapterPosition()).getId());
                intent.putExtra("category",books.get(holder.getAdapterPosition()).getCategory());
                startActivity(intent);
            });
        }


           class BookViewHolder extends RecyclerView.ViewHolder {
            CardView cardBook;
            ImageView imageBook;
            TextView textName, textAuthor, textPrice, txtDescription;

            BookViewHolder(View view) {
                super(view);
                cardBook = view.findViewById(R.id.book_card_view);
                imageBook = view.findViewById(R.id.image_book);
                textName = view.findViewById(R.id.text_book_name);
                textAuthor = view.findViewById(R.id.text_book_author);
                textPrice = view.findViewById(R.id.book_Price);
                txtDescription = view.findViewById(R.id.text_description);


            }
        }
    }


}
