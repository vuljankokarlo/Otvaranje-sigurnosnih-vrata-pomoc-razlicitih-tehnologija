package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudUserDataClass;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Profile;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.UserAccess;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrudProfileSecondary extends AppCompatActivity {

    User user;
    CrudUserDataClass crudUser;

    Profile spinnerSelectedProfile;
    facilityObject spinnerSelectedObject;

    UserAccess userAccessCrud;

    ArrayList<Profile> profileList;
    ArrayList<facilityObject> objectList;

    boolean isNewAccess = true;

    Spinner profileDropdown;
    Spinner objectDropdown;
    TextView dateFrom;
    TextView dateTo;

    Button saveAccess;

    DatePickerDialog.OnDateSetListener dateFromListener;
    DatePickerDialog.OnDateSetListener dateToListener;

    TimePickerDialog.OnTimeSetListener timeFromListener;
    TimePickerDialog.OnTimeSetListener timeToListener;

    String dateFromStr = "";
    String dateToStr = "";

    String dateFromStrShow = "";
    String dateToStrShow = "";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    Call<Integer> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_profile_secondary);

        profileDropdown = findViewById(R.id.profileAccessDropdown);
        objectDropdown = findViewById(R.id.objectAccessDropdown);

        dateFrom = findViewById(R.id.dateFromAccess);
        dateTo = findViewById(R.id.dateToAccess);

        saveAccess = findViewById(R.id.triggerButtonSave);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = (User) extras.getSerializable("user");
            crudUser = (CrudUserDataClass) extras.getSerializable("editUser");

            profileList= (ArrayList<Profile>) extras.getSerializable("listOfProfileNames");
            objectList = (ArrayList<facilityObject>) extras.getSerializable("listOfObjects");

        }

        ArrayAdapter<Profile> adapterProfile = new ArrayAdapter<Profile>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, profileList);
        adapterProfile.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        profileDropdown.setAdapter(adapterProfile);

        ArrayAdapter<facilityObject> adapterObject = new ArrayAdapter<facilityObject>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, objectList);
        adapterObject.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        objectDropdown.setAdapter(adapterObject);

        if (extras.getSerializable("listAccessItem") != null)
        {
            userAccessCrud = (UserAccess) extras.getSerializable("listAccessItem");
            isNewAccess = false;
            profileDropdown.setSelection(userAccessCrud.getProId()-1);
            objectDropdown.setSelection(userAccessCrud.getObjId()-1);
            dateFrom.setText(userAccessCrud.getValidFrom());
            dateTo.setText(userAccessCrud.getValidTo());
            //set strings for dates
        }

        profileDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelectedProfile = null;
                spinnerSelectedProfile = (Profile) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        objectDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelectedObject = null;
                spinnerSelectedObject = (facilityObject) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**
         * Listener for date start picker
         */
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFromStr = "";
                dateFrom.setText("");
                dateToStrShow = "";

                Calendar calFromDate = Calendar.getInstance();
                int year = calFromDate.get(Calendar.YEAR);
                int month = calFromDate.get(Calendar.MONTH);
                int day = calFromDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogFromDate = new DatePickerDialog(
                        CrudProfileSecondary.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateFromListener,
                        year, month, day);

                dialogFromDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogFromDate.show();
            }
        });

        dateFromListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                dateFromStr = String.format(Locale.getDefault(), "%04d-%02d-%02dT", year, month + 1, dayOfMonth);
                dateFromStrShow = String.format(Locale.getDefault(), "%04d.%02d.%02d", year, month + 1, dayOfMonth);


                Calendar calFromTime = Calendar.getInstance();
                int hour = calFromTime.get(Calendar.HOUR_OF_DAY);
                int minute = calFromTime.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog dialogFromTime = new TimePickerDialog(
                        CrudProfileSecondary.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeFromListener,
                        hour,minute,true
                );
                dialogFromTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogFromTime.show();
            }
        };

        timeFromListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                dateFromStr = String.format(Locale.getDefault(), dateFromStr + "%02d:%02d:00.0", hourOfDay, minute);
                dateFromStrShow = String.format(Locale.getDefault(), dateFromStrShow + " %02d:%02d", hourOfDay, minute);

                dateFrom.setText(dateFromStrShow);
            }
        };

        /**
         * Listener for date end picker
         */
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateToStr = "";
                dateTo.setText("");
                dateToStrShow = "";


                Calendar calToDate = Calendar.getInstance();
                int year = calToDate.get(Calendar.YEAR);
                int month = calToDate.get(Calendar.MONTH);
                int day = calToDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogToDate = new DatePickerDialog(
                        CrudProfileSecondary.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateToListener,
                        year, month, day);
                dialogToDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogToDate.show();
            }
        });

        dateToListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateToStr = String.format(Locale.getDefault(), "%04d-%02d-%02dT", year, month + 1, dayOfMonth);
                dateToStrShow = String.format(Locale.getDefault(), "%04d.%02d.%02d", year, month + 1, dayOfMonth);

                Calendar calToTime = Calendar.getInstance();
                int hour = calToTime.get(Calendar.HOUR_OF_DAY);
                int minute = calToTime.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog dialogToTime = new TimePickerDialog(
                        CrudProfileSecondary.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeToListener,
                        hour,minute,true
                );
                dialogToTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogToTime.show();
            }
        };

        timeToListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateToStr = String.format(Locale.getDefault(), dateToStr + "%02d:%02d:00.0", hourOfDay, minute);
                dateToStrShow = String.format(Locale.getDefault(), dateToStrShow + " %02d:%02d", hourOfDay, minute);
                dateTo.setText(dateToStrShow);
            }
        };

        saveAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final UserAccess accessPOST = new UserAccess();
                if (!isNewAccess)
                {
                    accessPOST.setAcsId(userAccessCrud.getAcsId());
                }
                accessPOST.setValidFrom(dateFrom.getText().toString());
                accessPOST.setValidTo(dateTo.getText().toString());
                accessPOST.setUsrId(crudUser.getUsrId());
                accessPOST.setProId(spinnerSelectedProfile.getProId());
                accessPOST.setObjId(spinnerSelectedObject.getObjId());


                if (isNewAccess)
                {
                    call = apiInterface.crudProfileNew(user.getToken(), accessPOST);
                }
                else
                {
                    call = apiInterface.crudProfile(user.getToken(), accessPOST);
                }

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {

                        if (isNewAccess)
                        {
                            accessPOST.setAcsId(response.body());
                        }

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",accessPOST);
                        setResult(2, returnIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

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