package com.orderista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.orderista.models.RegistrationResponse;
import com.orderista.network.ApiClient;
import com.orderista.network.ApiInterface;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button btn,btn1;
    EditText inputemail, inputname, inputphone, inputpassword, inputcompfirmpassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn = findViewById(R.id.registerbtn);
        btn1 = findViewById(R.id.signinbtn);
        inputemail = findViewById(R.id.inputemail);
        inputname = findViewById(R.id.inputfullname);
        inputphone = findViewById(R.id.inputmobileno);
        inputpassword = findViewById(R.id.inputpaasword);
        progressBar=findViewById(R.id.progressBar);
        inputcompfirmpassword = findViewById(R.id.inputcompassword);
        btn1.setOnClickListener(v -> {
            Intent intent=new Intent(getBaseContext(),SignInActivity.class);
            startActivity(intent);
        });
        btn.setOnClickListener(v -> {

            if (inputemail.getText().toString().trim().isEmpty()) {
                inputemail.setError("Enter Email");
            } else if (!emailValidator(inputemail.getText().toString())) {
                inputemail.setError("Enter Valid Email");

            } else if (inputname.getText().toString().trim().isEmpty()) {
                inputname.setError("Enter FullName");
            } else if (inputphone.getText().toString().trim().isEmpty()) {
                inputphone.setError("Enter Phone Number");
            } else if (inputpassword.getText().toString().trim().isEmpty()) {
                inputpassword.setError("Enter Password");
            } else if (inputcompfirmpassword.getText().toString().trim().isEmpty()) {
                inputcompfirmpassword.setError("Enter Comfirmpassword");
            } else if (!inputpassword.getText().toString().equals(inputcompfirmpassword.getText().toString())) {
                inputcompfirmpassword.setError("Password And Comfirmpassword Not Same");
            } else {
                    registerUser();
            }
        });

    }

private void registerUser()
{
    btn.setVisibility(View.INVISIBLE);
    progressBar.setVisibility(View.VISIBLE);
    HashMap<String,String>parameters=new HashMap<>();
    parameters.put("email", inputemail.getText().toString());
    parameters.put("full_name", inputname.getText().toString());
    parameters.put("mobile", inputphone.getText().toString());
    parameters.put("password", inputpassword.getText().toString());

    ApiClient.getClient().create( ApiInterface.class).registerUser(parameters).enqueue(new Callback<RegistrationResponse>() {
        @Override
        public void onResponse(@NonNull Call<RegistrationResponse> call, @NonNull Response<RegistrationResponse> response) {
            btn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            RegistrationResponse registrationResponse=response.body();
            if (registrationResponse != null){
                if (registrationResponse.success){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Success");
                    builder.setMessage(registrationResponse.message);
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", (dialog, which) -> finish());
                    builder.create().show();
                }else {
                    Toast.makeText(SignUpActivity.this, registrationResponse.message, Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(SignUpActivity.this,"Some Server error occurred", Toast.LENGTH_SHORT).show();

            }
        }



        @Override
        public void onFailure(@NonNull Call<RegistrationResponse> call, @NonNull Throwable t) {
            btn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
