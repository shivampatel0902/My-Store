package com.orderista;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SpalshActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=5000;
    Animation topanim;
    ImageView image;
    public TextView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalsh);
       topanim= AnimationUtils.loadAnimation(this, R.anim.top_animation);
       image=findViewById(R.id.imageview);
       image.setAnimation(topanim);



       new Handler().postDelayed(() -> {
            Intent intent=new Intent(SpalshActivity.this,SignInActivity.class);
            startActivity(intent);
            finish();
       },SPLASH_SCREEN);


    }
}
