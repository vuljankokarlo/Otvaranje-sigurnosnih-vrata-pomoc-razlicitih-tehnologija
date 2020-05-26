package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.CoreComponentFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Adapters.ObjectListAdapter;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Fragments.ObjectListShow;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.ObjectOpen;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;
import com.ncorti.slidetoact.SlideToActView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User currentUser;
    NavigationView navigationView;
    SlideToActView slideToActView;

    private List<facilityObject> objectDataList;
    private List<facilityObject> objectDataListCopy;

    ArrayList<ObjectOpen> closeAllList = new ArrayList<>();



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = (User) extras.getSerializable("currentUser");
        }

        Call<List<facilityObject>> call = apiInterface.getObjects(currentUser.getToken());

        call.enqueue(new Callback<List<facilityObject>>() {
            @Override
            public void onResponse(Call<List<facilityObject>> call, Response<List<facilityObject>> response) {

                List<facilityObject> FacilityObjects = response.body();

                objectDataList = new ArrayList<>(FacilityObjects);
                objectDataListCopy = new ArrayList<>();

                for (int i = 0; i < objectDataList.size(); i++)
                {
                    facilityObject fO = new facilityObject();

                    fO.setObjId(objectDataList.get(i).getObjId());
                    fO.setObjAction(objectDataList.get(i).getObjAction());
                    fO.setObjActivity(objectDataList.get(i).getObjActivity());
                    fO.setObjAuto(objectDataList.get(i).getObjAuto());
                    fO.setObjGps(objectDataList.get(i).getObjGps());
                    fO.setObjName(objectDataList.get(i).getObjName());
                    fO.setObjObtTypeId(objectDataList.get(i).getObjObtTypeId());
                    fO.setObjOpen(objectDataList.get(i).getObjOpen());
                    fO.setEventLogId(objectDataList.get(i).getEventLogId());
                    fO.setDate(objectDataList.get(i).getDate());
                    fO.setTriggerValue(objectDataList.get(i).getTriggerValue());
                    fO.setUserName(objectDataList.get(i).getUserName());
                    fO.setUserSurname(objectDataList.get(i).getUserSurname());
                    fO.setTriggerName(objectDataList.get(i).getTriggerName());
                    fO.setObjectName(objectDataList.get(i).getObjectName());
                    fO.setEventStatusName(objectDataList.get(i).getEventStatusName());

                    objectDataListCopy.add(fO);
                }


                for (int i = 0; i < objectDataList.size(); i++)
                {
                    if (objectDataList.get(i).getObjActivity() == 0)
                    {
                        objectDataList.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < objectDataListCopy.size(); i++)
                {
                    ObjectOpen objectOpen = new ObjectOpen(currentUser.getEmail(), objectDataListCopy.get(i).getObjName());
                    closeAllList.add(objectOpen);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction t = fragmentManager.beginTransaction();
                ObjectListShow objectListShow = new ObjectListShow();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", currentUser);
                bundle.putSerializable("objectList", (Serializable) objectDataList);
                objectListShow.setArguments(bundle);
                t.add(R.id.objectListShowFrame, objectListShow);
                t.commit();
            }

            @Override
            public void onFailure(Call<List<facilityObject>> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slideToActView = findViewById(R.id.slide_to_close_all);

        if (currentUser.getRole() == 1)
        {
            navigationView = findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_admin_options).setVisible(true);

            slideToActView.setVisibility(View.VISIBLE);

            slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                @Override
                public void onSlideComplete(@NotNull SlideToActView slideToActView) {
                    Call<ResponseBody> call = apiInterface.closeAll(currentUser.getToken(), closeAllList);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                    slideToActView.resetSlider();
                }
            });
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getResources().getString(R.string.phoneNumber);
                dialNumber(String.valueOf(phoneNumber));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            finish();
            startActivity(getIntent());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile)
        {
            Intent i = new Intent(MainActivity.this, UserProfile.class);
            i.putExtra("user",currentUser);
            startActivity(i);

        }
        else if (id == R.id.nav_admin_options)
        {
            Intent i = new Intent(MainActivity.this, AdminMenu.class);
            i.putExtra("currentUser",currentUser);
            i.putExtra("objectList", (Serializable) objectDataListCopy);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method that dials the number of Mobilisis info center
     * @param phoneNumber
     */
    private void dialNumber(String phoneNumber) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        this.startActivity(intent);
    }

}
