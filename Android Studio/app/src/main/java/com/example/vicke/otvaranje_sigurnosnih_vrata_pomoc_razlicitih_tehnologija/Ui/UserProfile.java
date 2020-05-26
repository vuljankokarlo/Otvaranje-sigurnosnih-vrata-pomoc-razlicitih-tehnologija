package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.EventLogData;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfile extends AppCompatActivity {

    User user;

    TextView firstName;
    TextView lastName;
    TextView email;

    ListView userLogListView;

    ArrayList<EventLogData> userLog = new ArrayList<>();

    Button profileBack;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
        }

        firstName = findViewById(R.id.profileFirstName);
        lastName = findViewById(R.id.profileLastName);
        email = findViewById(R.id.profileEmail);

        userLogListView = findViewById(R.id.userLogList);

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());

        profileBack = findViewById(R.id.profileBack);

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Call<ArrayList<EventLogData>> userEventLog = apiInterface.getUserLog(user.getToken(), user.getId());
        userEventLog.enqueue(new Callback<ArrayList<EventLogData>>() {
            @Override
            public void onResponse(Call<ArrayList<EventLogData>> call, Response<ArrayList<EventLogData>> response) {
                userLog = response.body();
                ArrayAdapter<EventLogData> arrayAdapter = new ArrayAdapter<>(getBaseContext(), R.layout.user_log_layout, R.id.userLogItem, userLog);
                userLogListView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<EventLogData>> call, Throwable t) {

            }
        });

    }
}
