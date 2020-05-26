package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui;

import android.app.Activity;
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
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudTriggerListItemData;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudUserDataClass;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.EventLogData;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.TriggerList;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.TriggerType;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrudTrigger extends AppCompatActivity {

    ArrayAdapter<CrudTriggerListItemData> triggerListAdapter;
    ListView listViewTrigger;

    ArrayList<facilityObject> listOfObjects = new ArrayList<>();

    User user;
    CrudUserDataClass crudUser;

    Button addNew;
    Button next;

    ArrayList<CrudTriggerListItemData> triggerDatForList = new ArrayList<>();

    ArrayList<TriggerList> listData = new ArrayList<>();
    ArrayList<TriggerType> listofTriggerNames = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_trigger);

        addNew = findViewById(R.id.addNewTrigger);
        next = findViewById(R.id.triggerNext);

        listViewTrigger = findViewById(R.id.triggerListView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
            crudUser = (CrudUserDataClass) extras.getSerializable("editUser");
            listOfObjects = (ArrayList<facilityObject>) extras.getSerializable("listOfObjects");
        }

        //prvi retrofit za sve triggere
        Call<ArrayList<TriggerType>> call = apiInterface.getTriggerTypes(user.getToken());
        call.enqueue(new Callback<ArrayList<TriggerType>>() {
            @Override
            public void onResponse(Call<ArrayList<TriggerType>> call, Response<ArrayList<TriggerType>> response) {
                listofTriggerNames = response.body();

                //drugi retrofit poziv za listu trigger typeova(listData)
                Call<ArrayList<TriggerList>> call2 = apiInterface.getTriggerList(user.getToken(), crudUser.getUsrId());
                call2.enqueue(new Callback<ArrayList<TriggerList>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TriggerList>> call, Response<ArrayList<TriggerList>> response) {
                        listData = response.body();
                        for(int i = 0; i < listData.size(); i++)
                        {
                            triggerDatForList.add(new CrudTriggerListItemData(listofTriggerNames.get(listData.get(i).getTrgTrtId()-1).getTriggerName(), listData.get(i).getTrgValue(), listData.get(i).getTrgActivity()));
                        }
                        triggerListAdapter = new ArrayAdapter<>(getBaseContext(), R.layout.trigger_list_item, R.id.triggerListItem, triggerDatForList);
                        listViewTrigger.setAdapter(triggerListAdapter);
                    }
                    @Override
                        public void onFailure(Call<ArrayList<TriggerList>> call, Throwable t) {

                        }
                    });
            }

            @Override
            public void onFailure(Call<ArrayList<TriggerType>> call, Throwable t) {

            }
        });

        listViewTrigger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), CrudTriggerSecondary.class);
                i.putExtra("user", user);
                i.putExtra("listDataItem", listData.get(position));
                i.putExtra("listOfTriggerNames", listofTriggerNames);
                i.putExtra("editUser", crudUser);
                startActivityForResult(i, 2);
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.add(new TriggerList());
                triggerListAdapter.notifyDataSetChanged();
                Intent i = new Intent(getBaseContext(), CrudTriggerSecondary.class);
                i.putExtra("user", user);
                i.putExtra("editUser", crudUser);
                i.putExtra("listOfTriggerNames", listofTriggerNames);
                startActivityForResult(i, 2);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrudTrigger.this, CrudProfile.class);
                i.putExtra("currentUser",user);
                i.putExtra("listOfObjects", listOfObjects);
                i.putExtra("editUser", crudUser);
                startActivity(i);
            }
        });
    }

    /**
     * Method for fetching data after trigger is added or modified
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2)
        {
                TriggerList result = (TriggerList) data.getSerializableExtra("result");
                listData.get(listData.size() - 1).setTrgUsrId(result.getTrgUsrId());
                listData.get(listData.size() - 1).setTrgValue(result.getTrgValue());
                listData.get(listData.size() - 1).setTrgActivity(result.getTrgActivity());
                //listViewTrigger.setAdapter(null);
                //listViewTrigger.setAdapter(triggerListAdapter);
            finish();
            startActivity(getIntent());
        }
    }
}
