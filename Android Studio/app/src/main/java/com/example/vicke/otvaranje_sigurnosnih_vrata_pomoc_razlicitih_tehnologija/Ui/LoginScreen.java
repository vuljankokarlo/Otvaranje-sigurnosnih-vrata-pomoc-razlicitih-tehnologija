package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Login;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = LoginScreen.class.getSimpleName();
    EditText email;
    EditText password;
    Button login;
    User logedUser = new User();

    ProgressBar progressBar;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        progressBar = findViewById(R.id.loginAnim);


        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(180, TimeUnit.SECONDS);
        client.connectTimeout(180, TimeUnit.SECONDS);

            int Permission_All = 1;

            String[] Permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS};
            if (!hasPremission(this, Permissions)){
                ActivityCompat.requestPermissions(this, Permissions, Permission_All);
            }

            email = findViewById(R.id.input_email);
            password = findViewById(R.id.input_password);
            login = findViewById(R.id.btn_login);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String usrEmail = email.getText().toString();
                    String loginPassword = password.getText().toString();
                    Login(usrEmail, loginPassword);
                }
            });
        }

    /**
     * Login method
     * @param usrEmail
     * @param loginPassword
     */
    private void Login(String usrEmail, String loginPassword)
        {
            final Login login = new Login(usrEmail, loginPassword);
            Call<User> call = apiInterface.login(login);

            progressBar.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful())
                    {
                        Log.d("-----------------ROLA:", response.body().getRole()+"---------------------");
                        logedUser.setId(response.body().getId());
                        logedUser.setEmail(response.body().getEmail());
                        logedUser.setFirstName(response.body().getFirstName());
                        logedUser.setLastName(response.body().getLastName());
                        logedUser.setToken("Bearer "+response.body().getToken());
                        logedUser.setRole(response.body().getRole());

                        progressBar.setVisibility(View.GONE);

                        Intent i = new Intent(LoginScreen.this, MainActivity.class);
                        i.putExtra("token","Bearer "+response.body().getToken());
                        i.putExtra("currentUser", logedUser);
                        startActivity(i);
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginScreen.this, "Response unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginScreen.this, "Failed connection", Toast.LENGTH_SHORT).show();
                }
            });
        }

    /**
     * Method for premission check
     * @param context
     * @param premissions
     * @return
     */
        public static boolean hasPremission(Context context, String... premissions){

            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context != null && premissions != null)
            {
                for(String premission:premissions)
                {
                    if(ActivityCompat.checkSelfPermission(context, premission) != PackageManager.PERMISSION_GRANTED)
                    {
                        return false;
                    }
                }
            }
            return true;
        }
}
