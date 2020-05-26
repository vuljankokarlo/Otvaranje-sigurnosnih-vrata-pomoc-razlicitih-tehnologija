package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudUserDataClass;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.TriggerList;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.TriggerType;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrudTriggerSecondary extends AppCompatActivity {

    User user;
    TriggerList triggerList;
    ArrayList<TriggerType> triggerTypes;
    TriggerType spinnerSelected;

    boolean isNewTrigger = true;

    CrudUserDataClass crudUser;

    Spinner spinner;
    EditText editText;
    CheckBox checkBox;

    Button saveTrigger;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    Call<ResponseBody> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigger_crud_screen);

        spinner = findViewById(R.id.triggerTypeDropdown);
        editText = findViewById(R.id.input_trigger_value);
        checkBox = findViewById(R.id.triggerIsActive);

        saveTrigger = findViewById(R.id.triggerButtonSave);



        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = (User) extras.getSerializable("user");
            triggerTypes = (ArrayList<TriggerType>) extras.getSerializable("listOfTriggerNames");
            crudUser = (CrudUserDataClass) extras.getSerializable("editUser");
        }

        ArrayAdapter<TriggerType> adapter = new ArrayAdapter<TriggerType>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, triggerTypes);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (extras.getSerializable("listDataItem") != null)
        {
            triggerList = (TriggerList) extras.getSerializable("listDataItem");
            isNewTrigger = false;
            spinner.setSelection(triggerList.getTrgTrtId()-1);
            editText.setText(triggerList.getTrgValue());
            if (triggerList.getTrgActivity() == 1)
            {
                checkBox.setChecked(true);
            }
            else
            {
                checkBox.setChecked(false);
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelected = null;
                spinnerSelected = (TriggerType) parent.getSelectedItem();
                if (spinnerSelected.getTriggerName().contains("App"))
                {
                    editText.requestFocus();
                    editText.setHint("");
                    editText.setText(crudUser.getUsrEmail());
                    editText.setVisibility(View.INVISIBLE);
                }
                else
                {
                    editText.setVisibility(View.VISIBLE);
                    editText.requestFocus();
                    editText.setHint("Format unosa: 385#...#");
                    if (isNewTrigger)
                    {
                        editText.setText("");
                    }
                    else
                    {
                        editText.setText(triggerList.getTrgValue());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TriggerList triggerListPOST = new TriggerList();
                triggerListPOST.setTrgTrtId(spinnerSelected.getTriggerTypeId());
                triggerListPOST.setTrgValue(editText.getText().toString());
                triggerListPOST.setTrgUsrId(crudUser.getUsrId());
                if (checkBox.isChecked())
                {
                    triggerListPOST.setTrgActivity(1);
                }
                else
                {
                    triggerListPOST.setTrgActivity(0);
                }



                if (isNewTrigger)
                {
                    call = apiInterface.crudTriggersNew(user.getToken(), triggerListPOST);
                }
                else
                {
                    call = apiInterface.crudTriggers(user.getToken(), triggerListPOST);
                }

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",triggerListPOST);
                        setResult(2, returnIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    /**
     * Disable back button
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
