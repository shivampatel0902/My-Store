package com.orderista;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AboutusActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_about_page);
        setTitle("About Page");
       /*fancyAboutPage.setCover(R.drawable.coverimg);
        fancyAboutPage.setName("Padmani Shivam");
        fancyAboutPage.setDescription("Study At Shantilal Shah Engineering College in BE ");
        fancyAboutPage.setAppIcon(R.drawable.orderista);
        fancyAboutPage.setAppName("MyStore");
        fancyAboutPage.setVersionNameAsAppSubTitle("1.0");
        fancyAboutPage.setAppDescription("My Store is the practical implementation of E-commerce for grocery goods.\n\n" +
                "The different items of different brands can sell their product under one window.\n\n" +
                "The flexibility and enhanced shopping environment is provided to customers.");

        fancyAboutPage.addFacebookLink("https://www.facebook.com/shivampadmani");
        fancyAboutPage.addTwitterLink("https://twitter.com/shivam0902");
       // fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/shashank-singhal-a87729b5/");
       // fancyAboutPage.addGitHubLink("https://github.com/Shashank02051997");*/

    }
}
