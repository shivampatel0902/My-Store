package com.orderista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.orderista.utils.Constance;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ViewPager2 viewPager2;
    private Handler sliderHander = new Handler();
    private CardView cardView1,cardView2,cardView3,cardView4;
    private TextView sidehome , sidecart , sideorder, sidelogout,sideaboutus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageMenu = findViewById(R.id.imageMenu);
        drawerLayout = findViewById(R.id.drawerLayout);
        cardView1=findViewById(R.id.hotel_1);
        cardView2=findViewById(R.id.hotel_2);
        cardView3=findViewById(R.id.hotel_3);
        cardView4=findViewById(R.id.hotel_4);
        sidehome =findViewById(R.id.btnHome);
        sidecart =findViewById(R.id.btnCart);
        sideorder =findViewById(R.id.btnOrder);
        sideaboutus =findViewById(R.id.btnabout);
        sidelogout =findViewById(R.id.btnLogout);
        Intent in = getIntent();
        String string = in.getStringExtra("message");

            //cardView

        cardView1.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),CategoriesActivity.class);
            startActivity(intent);
        });
        cardView2.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),CartActivity.class);
            startActivity(intent);
        });
        cardView3.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),MyOrdersActivity.class);
            startActivity(intent);
        });
        cardView4.setOnClickListener(view -> {
            // Launching News Feed Screen

            SharedPreferences preferences = getSharedPreferences("mystore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
            Intent intent=new Intent(getBaseContext(),SignInActivity.class);
            startActivity(intent);
        });
        sidehome.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
        });
        sidecart.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),CartActivity.class);
            startActivity(intent);
        });
        sideorder.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),MyOrdersActivity.class);
            startActivity(intent);
        });
        sideaboutus.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),AboutusActivity.class);
            startActivity(intent);
        });
        sidelogout.setOnClickListener(view -> {
            // Launching News Feed Screen

            SharedPreferences preferences = getSharedPreferences("mystore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
            Intent intent=new Intent(getBaseContext(),SignInActivity.class);
            startActivity(intent);
        });
        //ViewPager2

        viewPager2 = findViewById(R.id.viewPager);
        List<SlideItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SlideItem(R.drawable.img1));
        sliderItems.add(new SlideItem(R.drawable.img2));
        sliderItems.add(new SlideItem(R.drawable.img3));
        sliderItems.add(new SlideItem(R.drawable.img4));
        sliderItems.add(new SlideItem(R.drawable.img5));
        sliderItems.add(new SlideItem(R.drawable.img6));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(60));
        compositePageTransformer.addTransformer((page, position) -> {

        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHander.removeCallbacks(sliderRunnable);
                sliderHander.postDelayed(sliderRunnable, 2500);
            }
        });


        imageMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        TextView txtUserName = findViewById(R.id.txtUserName);
        TextView txtMobile = findViewById(R.id.txtMobileNumber);
        TextView txtEmail = findViewById(R.id.txtEmail);

        SharedPreferences sharedPreferences = getSharedPreferences(Constance.PREFRENCE_NAME, 0);
        txtUserName.setText(
                sharedPreferences.getString(Constance.FULLNAME, "")
        );
        txtEmail.setText(sharedPreferences.getString(Constance.EMAIL, ""));
        txtMobile.setText(sharedPreferences.getString(Constance.MOBILE, ""));
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

}
