package com.orderista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.orderista.database.DatabaseHandler;
import com.orderista.models.BookModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    ImageView imageView,imageAddToCart;
    TextView textAuthor,textPublish,textDescription,textPrice,textToolBar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        imageView=findViewById(R.id.image_book);
       // textAuthor=findViewById(R.id.text_author);
      //  textPublish=findViewById(R.id.text_year);
        textDescription=findViewById(R.id.text_description);
        textPrice=findViewById(R.id.text_price);
        textToolBar=findViewById(R.id.text_toolbar);
        imageAddToCart=findViewById(R.id.image_cart);
        Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);
       // textAuthor.setText(getIntent().getStringExtra("author"));
      //  textPublish.setText(getIntent().getStringExtra("publishYear"));
        textDescription.setText(getIntent().getStringExtra("description"));
        textPrice.setText("\u20B9" +getIntent().getStringExtra("price"));
        textToolBar.setText(getIntent().getStringExtra("name"));



        findViewById(R.id.image_back).setOnClickListener(v -> onBackPressed());
        imageAddToCart.setOnClickListener(v -> {
            BookModel bookModel=new BookModel();
            bookModel.setId(getIntent().getStringExtra("id"));
            bookModel.setCategory(getIntent().getStringExtra("category"));
            bookModel.setName(getIntent().getStringExtra("name"));
            bookModel.setDescription(getIntent().getStringExtra("description"));
           // bookModel.setAuthor(getIntent().getStringExtra("author"));
            bookModel.setImage(getIntent().getStringExtra("image"));
            //bookModel.setPublishedYear(getIntent().getStringExtra("publishYear"));
            bookModel.setPrice(getIntent().getStringExtra("price"));



            DatabaseHandler databaseHandler=new DatabaseHandler(getApplicationContext());
            databaseHandler.addToCart(bookModel);
            Toast.makeText(this, "Added To Cart!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),CartActivity.class));

        });
        DatabaseHandler databaseHandler=new DatabaseHandler(getApplicationContext());
        List<BookModel> cartItems=databaseHandler.getCartItems();


        for (int i=0;i<=cartItems.size()-1;i++)
        {
            Log.d("CART_DATA",cartItems.get(i).getName() + " - " + cartItems.get(i).getPrice());


        }
    }

}
