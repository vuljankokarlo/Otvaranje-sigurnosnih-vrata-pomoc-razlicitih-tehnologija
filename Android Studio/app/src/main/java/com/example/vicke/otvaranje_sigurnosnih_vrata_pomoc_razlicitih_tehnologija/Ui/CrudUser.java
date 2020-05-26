package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.AllUser;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudUserDataClass;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Role;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrudUser extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    Call<Integer> call;

    private List<Role> listOfRoles;

    ArrayList<facilityObject> listOfObjects = new ArrayList<>();

    Role role;
    User user;
    CrudUserDataClass crudUser = new CrudUserDataClass();
    AllUser passedUser;

    CheckBox isActive;
    CheckBox generatePassword;
    Spinner dropdown;
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView password;

    boolean isNewUser = true;

    Button next;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_user);

        firstName = findViewById(R.id.input_first_name);
        lastName = findViewById(R.id.input_last_name);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        isActive = findViewById(R.id.checkboxIsActive);
        generatePassword = findViewById(R.id.generatePassword);

        isActive.setChecked(true);
        generatePassword.setChecked(true);

        next = findViewById(R.id.btnNext);
        cancel = findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        listOfRoles = (List<Role>)bundle.getSerializable("userRole");
        listOfObjects = (ArrayList<facilityObject>) bundle.getSerializable("listOfObjects");


        dropdown = findViewById(R.id.roleDropdown);
        ArrayAdapter<Role> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listOfRoles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = null;
                role = (Role) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (bundle.getSerializable("currentUser") != null)
        {
            passedUser = (AllUser) bundle.getSerializable("currentUser"); //ovo je kliknuti user za kojeg se uređuju stvari
            firstName.setText(passedUser.getUsrName());
            lastName.setText(passedUser.getUsrSurname());
            email.setText(passedUser.getUsrEmail());
            dropdown.setSelection(passedUser.getUsrRolId()-1);
            generatePassword.setChecked(false);
            crudUser.setUsrId(passedUser.getUsrId());
            isNewUser = false;
        }

        user = (User) bundle.getSerializable("user"); //od tud vucem token

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crudUser.setUsrName(firstName.getText().toString());
                crudUser.setUsrSurname(lastName.getText().toString());
                crudUser.setUsrEmail(email.getText().toString());
                if (generatePassword.isChecked())
                {
                    crudUser.setGeneratePassword(true);
                }
                else
                {
                    crudUser.setGeneratePassword(false);
                }
                crudUser.setUsrRolId(role.getRolId());
                if (isActive.isChecked())
                {
                    crudUser.setActivity(1);
                }
                else
                {
                    crudUser.setActivity(0);
                }




                if (isNewUser)
                {
                    call = apiInterface.crudUsersNew(user.getToken(), crudUser);
                }
                else
                {
                    call = apiInterface.crudUsers(user.getToken(), crudUser);
                }

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (isNewUser)
                        {
                            crudUser.setUsrId(response.body());
                        }
                        Intent i = new Intent(getBaseContext(), CrudTrigger.class);
                        i.putExtra("user", user);
                        i.putExtra("listOfObjects", listOfObjects);
                        i.putExtra("editUser", crudUser);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });

            }
        });
    }
}
