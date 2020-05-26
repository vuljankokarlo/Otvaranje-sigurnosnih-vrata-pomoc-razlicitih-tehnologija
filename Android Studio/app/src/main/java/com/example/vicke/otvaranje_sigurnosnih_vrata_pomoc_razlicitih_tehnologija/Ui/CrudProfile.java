package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudProfileListItemData;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudUserDataClass;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Profile;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.UserAccess;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrudProfile extends AppCompatActivity {

    ArrayAdapter<CrudProfileListItemData> profileListAdapter;
    ListView listViewProfile;

    ArrayList<facilityObject> listOfObjects = new ArrayList<>();

    User user;
    CrudUserDataClass crudUser;

    Button addNew;
    Button done;

    ArrayList<CrudProfileListItemData> profileDataForList = new ArrayList<>();

    ArrayList<UserAccess> listDataAccess = new ArrayList<>();
    ArrayList<Profile> listofProfiles = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_profile);

        addNew = findViewById(R.id.addNewProfile);
        done = findViewById(R.id.profileFinish);

        listViewProfile = findViewById(R.id.profileListView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("currentUser");
            crudUser = (CrudUserDataClass) extras.getSerializable("editUser");
            listOfObjects = (ArrayList<facilityObject>) extras.getSerializable("listOfObjects");
        }

        //prvi retrofit za sve profile
        Call<ArrayList<Profile>> call = apiInterface.getProfiles(user.getToken());
        call.enqueue(new Callback<ArrayList<Profile>>() {
            @Override
            public void onResponse(Call<ArrayList<Profile>> call, Response<ArrayList<Profile>> response) {
                listofProfiles = response.body();

                //drugi retrofit poziv za listu accessa (listData)
                Call<ArrayList<UserAccess>> call2 = apiInterface.getAccess(user.getToken(), crudUser.getUsrId());
                call2.enqueue(new Callback<ArrayList<UserAccess>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserAccess>> call, Response<ArrayList<UserAccess>> response) {
                        listDataAccess = response.body();
                        for(int i = 0; i < listDataAccess.size(); i++)
                        {
                            profileDataForList.add(new CrudProfileListItemData(listDataAccess.get(i).getValidFrom(), listDataAccess.get(i).getValidTo(), listofProfiles.get(listDataAccess.get(i).getProId()-1).getProName(), listOfObjects.get(listOfObjects.get(i).getObjId()-1).getObjName()));
                        }
                        profileListAdapter = new ArrayAdapter<>(getBaseContext(), R.layout.trigger_list_item, R.id.triggerListItem, profileDataForList);
                        listViewProfile.setAdapter(profileListAdapter);
                    }
                    @Override
                    public void onFailure(Call<ArrayList<UserAccess>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<Profile>> call, Throwable t) {

            }
        });

        listViewProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), CrudProfileSecondary.class);
                i.putExtra("user", user);
                i.putExtra("listAccessItem", listDataAccess.get(position));
                i.putExtra("listOfProfileNames", listofProfiles);
                i.putExtra("listOfObjects", listOfObjects);
                i.putExtra("editUser", crudUser);
                startActivityForResult(i, 2);
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDataAccess.add(new UserAccess());
                profileListAdapter.notifyDataSetChanged();
                Intent i = new Intent(getBaseContext(), CrudProfileSecondary.class);
                i.putExtra("user", user);
                i.putExtra("editUser", crudUser);
                i.putExtra("listOfProfileNames", listofProfiles);
                i.putExtra("listOfObjects", listOfObjects);
                startActivityForResult(i, 2);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrudProfile.this, AdminMenu.class);
                i.putExtra("currentUser",user);
                i.putExtra("objectList", (Serializable) listOfObjects);
                startActivity(i);
            }
        });
    }

    /**
     * Method for fetching data after profile is created or modified
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2)
        {
            UserAccess result = (UserAccess) data.getSerializableExtra("result");
            listDataAccess.get(listDataAccess.size() - 1).setAcsId(result.getAcsId());
            listDataAccess.get(listDataAccess.size() - 1).setObjId(result.getObjId());
            listDataAccess.get(listDataAccess.size() - 1).setProId(result.getProId());
            listDataAccess.get(listDataAccess.size() - 1).setUsrId(result.getUsrId());
            listDataAccess.get(listDataAccess.size() - 1).setValidFrom(result.getValidFrom());
            listDataAccess.get(listDataAccess.size() - 1).setValidTo(result.getValidTo());
            //listViewTrigger.setAdapter(null);
            //listViewTrigger.setAdapter(triggerListAdapter);
            finish();
            startActivity(getIntent());
        }
    }
}