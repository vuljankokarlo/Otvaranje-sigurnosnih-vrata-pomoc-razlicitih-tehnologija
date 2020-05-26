package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.R;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.ObjectOpen;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service.ApiInterface;
import com.ncorti.slidetoact.SlideToActView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ObjectListAdapter extends BaseExpandableListAdapter {

    private List<facilityObject> listOfObjects;
    private List<String> header_titles;
    private HashMap<String, SlideToActView> child_titles;
    private Context ctx;
    private User activeUser;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);


    public ObjectListAdapter(Context ctx, List<String> header_titles, HashMap<String, SlideToActView> child_titles, User user, List<facilityObject> listOfObjects)
    {
        this.ctx = ctx;
        this.child_titles = child_titles;
        this.header_titles = header_titles;
        this.activeUser = user;
        this.listOfObjects = listOfObjects;
    }

    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_titles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_titles;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) this.getGroup(groupPosition);
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent_layout, null);
        }

        TextView textView = convertView.findViewById(R.id.heading_item);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(
                            "Name: " + title + "\n"
                            + "Last interaction: " + listOfObjects.get(groupPosition).getDate() + "\n"
                            + "User: " + listOfObjects.get(groupPosition).getUserName() + " " + listOfObjects.get(groupPosition).getUserSurname()
                        );

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_layout, null);
        }

        SlideToActView slideToActView = convertView.findViewById(R.id.slide_to_open);

        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NotNull final SlideToActView slideToActView) {

                ObjectOpen objectOpen = new ObjectOpen(activeUser.getEmail(), header_titles.get(groupPosition));

                /**
                 * Open object with slider
                 */
                Call<ResponseBody> call = apiInterface.openObject(activeUser.getToken(),objectOpen);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String str = "";
                        if (response.message().contains("Bad Request"))
                        {
                            str = "You can't open this object";
                        }
                        if (response.message().contains("OK"))
                        {
                            str = "Object opened";
                        }

                        Toast toast= Toast.makeText(slideToActView.getContext(),
                                str, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 250);
                        toast.show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("Response: ", "cannot connect to API");
                    }
                });

                slideToActView.resetSlider();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
