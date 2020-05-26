package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Fragments.AddGuestFragment;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Fragments.LogFragment;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.Ui.Fragments.UserFragment;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.AllUser;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.EventLogData;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Role;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdminMenuFragmentAdapter extends FragmentStatePagerAdapter {

    User user;
    int numberOfTabs;
    private List<AllUser> userList;
    private List<Role> roleList;
    private List<EventLogData> eventLogData;
    private ArrayList<facilityObject> objectList;

    public AdminMenuFragmentAdapter(FragmentManager fm, int numberOfTabs, List<AllUser> userList, List<Role> roleList, User user, List<EventLogData> eventLogData, ArrayList<facilityObject> objectList)
    {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.userList = userList;
        this.roleList = roleList;
        this.user = user;
        this.eventLogData = eventLogData;
        this.objectList = objectList;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0:
                LogFragment logFragment = new LogFragment();
                Bundle bundleLog = new Bundle();
                bundleLog.putSerializable("logList", (Serializable) eventLogData);
                logFragment.setArguments(bundleLog);

                return logFragment;
            case 1:
                UserFragment userFragment = new UserFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("listOfUsers", (Serializable) userList);
                bundle.putSerializable("listOfRoles", (Serializable) roleList);
                bundle.putSerializable("listOfObjects", (Serializable) objectList);
                bundle.putSerializable("currentUser", user);
                userFragment.setArguments(bundle);

                return userFragment;
            case 2:
                AddGuestFragment addGuestFragment = new AddGuestFragment();
                Bundle bundleGuest = new Bundle();
                bundleGuest.putSerializable("listOfObjects", (Serializable) objectList);
                bundleGuest.putSerializable("user", user);
                addGuestFragment.setArguments(bundleGuest);
                return addGuestFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
