package com.joel.midterm_guineaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.joel.midterm_guineaandroid.api.RequestPlaceholder;
import com.joel.midterm_guineaandroid.api.RetrofitBuilder;
import com.joel.midterm_guineaandroid.pojos.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public MaterialButton LoginBtn;
    public TextView result;

    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceholder requestPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.loginBtn);
        result = findViewById(R.id.result);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceholder = retrofitBuilder.getRetrofit().create(RequestPlaceholder.class);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (username.getText() != null && password.getText() != null) {
                    Call<Login> LoginCall = requestPlaceholder.Login(new Login(null, username.getText().toString(), null, null, password.getText().toString()));

                    LoginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if (!response.isSuccessful()){
                                if  (response.code() == 404) {
                                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                    Log.e("LOG-IN ERROR", response.message());
                                } else {
                                    Toast.makeText(LoginActivity.this, "There was an error upon logging in to the API", Toast.LENGTH_SHORT).show();
                                    Log.e("LOG-IN ERROR", response.message());
                                }
                                Toast.makeText(LoginActivity.this, "There was an error upon logging in to the API", Toast.LENGTH_SHORT).show();
                                Log.e("LOG-IN ERROR", response.message());

                            }else {
                                if (response.code() == 200) {
                                    Login loginResponse = response.body();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("USERID", loginResponse.getId());
                                    intent.putExtra("USERNAME", loginResponse.getUsername());
                                    intent.putExtra("TOKEN", loginResponse.getToken());

                                    startActivity(intent);
                                    finish();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "There was an error upon logging in to the API", Toast.LENGTH_SHORT).show();
                            Log.e("LOG-IN ERROR", t.getMessage());

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Please apply all the fields in Login!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}