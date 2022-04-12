package com.orderista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.orderista.models.SignInResponse;
import com.orderista.network.ApiClient;
import com.orderista.network.ApiInterface;
import com.orderista.utils.Constance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
ViewFlipper vfliper;
Button siupbt,siinbtn;
CheckBox checkbox;
EditText inputemail,inputpassword;
ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences(Constance.PREFRENCE_NAME,0);

        if (sharedPreferences.getBoolean(Constance.IS_SIGNED_IN,false))
        {
            Intent intent=new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_sign_in);
        siupbt=findViewById(R.id.signupbtn);
        siinbtn=findViewById(R.id.btnsignin);
        inputemail=findViewById(R.id.inputusername);
        inputpassword=findViewById(R.id.inputpasswordsignin);
        checkbox =findViewById(R.id.checkbox);
        progressBar=findViewById(R.id.progressBar);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                // show password
                inputpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // hide password
                inputpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        siinbtn.setOnClickListener(v -> SignInActivity.this.login());
        siupbt.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),SignUpActivity.class);
            startActivity(intent);

        });

        int[] images = {R.drawable.image6, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image1};

        vfliper=findViewById(R.id.v_flipper);

        for (int image:images)
            fliperimage(image);

    }



    @SuppressLint("NewApi")
    public void fliperimage(int image)

    {
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);


        vfliper.addView(imageView);
        vfliper.setFlipInterval(1500);
        vfliper.setAutoStart(true);
        vfliper.setOutAnimation(this,android.R.anim.slide_out_right);
        vfliper.setInAnimation(this,android.R.anim.slide_in_left);

    }
    private void login()
    {
        if (TextUtils.isEmpty(inputemail.getText().toString().trim()))
        {
            inputemail.setError("Enter Email Address");
        }
        else if (!emailValidator(inputemail.getText().toString()))
        {
            inputemail.setError("Enter  Valid Email Address");
        }
        else if (TextUtils.isEmpty(inputpassword.getText().toString().trim()))
        {
            inputpassword.setError("Enter Password ");
        }
        else
        {
            signIn();

        }
    }

    private void signIn() {
        siinbtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.getClient().create(ApiInterface.class).signIn(inputemail.getText().toString(),inputpassword.getText().toString()).enqueue(new Callback<SignInResponse>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull  Call<SignInResponse> call,@NonNull Response<SignInResponse> response) {
                siinbtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                SignInResponse signInResponse=response.body();
                if(signInResponse!= null)
                {
                    if (signInResponse.success)
                    {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putBoolean(Constance.IS_SIGNED_IN,true);
                        editor.putString(Constance.USER_ID,signInResponse.userData.userDetails.get(0).user_id);
                        editor.putString(Constance.FULLNAME,signInResponse.userData.userDetails.get(0).full_name);
                        editor.putString(Constance.MOBILE,signInResponse.userData.userDetails.get(0).mobile);
                        editor.putString(Constance.EMAIL,signInResponse.userData.userDetails.get(0).email);
                        editor.apply();
                        Toast.makeText(SignInActivity.this, signInResponse.message, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(SignInActivity.this, signInResponse.message, Toast.LENGTH_SHORT).show();


                    }

                }else {
                    Toast.makeText(SignInActivity.this, "Some Server error occurred", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<SignInResponse> call,@NonNull Throwable t) {
                siinbtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

}
