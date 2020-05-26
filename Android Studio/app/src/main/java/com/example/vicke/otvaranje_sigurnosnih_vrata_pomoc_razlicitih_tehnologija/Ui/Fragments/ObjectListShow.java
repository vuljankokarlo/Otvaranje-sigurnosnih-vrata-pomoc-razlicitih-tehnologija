package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Adapters.ObjectListAdapter;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ObjectListShow extends Fragment {


    private User user;

    private ExpandableListView expandableListView;
    private List<facilityObject> objectDataList;



    public static ObjectListShow newInstance() {
        return new ObjectListShow();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        user = (User)getArguments().getSerializable("user");
        objectDataList = (ArrayList<facilityObject>)getArguments().getSerializable("objectList");
        return inflater.inflate(R.layout.object_list_show, parent, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        expandableListView = getView().findViewById(R.id.expendableList);

        List<String> Headings = new ArrayList<>();
        Headings = addHeaderName(Headings, objectDataList);

        HashMap<String, SlideToActView> ChildList = new HashMap<>();

        for (int i = 0; i<Headings.size(); i++)
        {
            SlideToActView slideToActView = new SlideToActView(getContext());
            ChildList.put(Headings.get(i),slideToActView);
        }


        ObjectListAdapter MyAdapter = new ObjectListAdapter(getContext(), Headings, ChildList, user, objectDataList);
        expandableListView.setAdapter(MyAdapter);

    }

    /**
     * Populate headerList with names of objects
     * @param headerList
     * @param nameList
     * @return
     */
    private List<String> addHeaderName(List<String> headerList, List<facilityObject> nameList)
    {

        for (int i=0; i<nameList.size(); i++)
        {
            headerList.add(nameList.get(i).getObjName());
        }

        return headerList;
    }

}