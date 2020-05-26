package com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.service;

import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.AllUser;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.CrudUserDataClass;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.EventLogData;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.GuestData;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Login;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.ObjectOpen;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Profile;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.Role;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.TriggerList;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.TriggerType;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.User;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.UserAccess;
import com.example.vicke.otvaranje_sigurnosnih_vrata_pomoc_razlicitih_tehnologija.api.model.facilityObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://172.16.0.200:5000";

    @GET("/api/AvailableObjects/lastOpened")
    Call<List<facilityObject>> getObjects(@Header("Authorization") String authToken);

    @POST("/api/triggeraccess")
    Call<ResponseBody> openObject(@Header("Authorization") String authToken,@Body ObjectOpen objectOpen);

    @POST("/api/TriggerAccess/Close")
    Call<ResponseBody> closeAll(@Header("Authorization") String authToken, @Body ArrayList<ObjectOpen> objectOpens);



    @POST("/api/Users/authenticate")
    Call<User> login(@Body Login login);



    @GET("/api/Users/All")
    Call<List<AllUser>> getUsers(@Header("Authorization") String authToken);

    @POST("api/Users/register")
    Call<Integer> crudUsersNew(@Header("Authorization") String authToken, @Body CrudUserDataClass crudUserDataClass);

    @POST("api/Users/update")
    Call<Integer> crudUsers(@Header("Authorization") String authToken, @Body CrudUserDataClass crudUserDataClass);



    @GET("api/AcTriggerTypes")
    Call<ArrayList<TriggerType>> getTriggerTypes(@Header("Authorization") String authToken);

    @GET("api/Trigger")
    Call<ArrayList<TriggerList>> getTriggerList(@Header("Authorization") String authToken, @Query("id") int userID);


    @POST("api/Trigger")
    Call<ResponseBody> crudTriggersNew(@Header("Authorization") String authToken, @Body TriggerList triggerLists);

    @POST("api/Trigger/update")
    Call<ResponseBody> crudTriggers(@Header("Authorization") String authToken, @Body TriggerList triggerLists);

    @GET("/api/Roles")
    Call<List<Role>> getRoles(@Header("Authorization") String authToken);



    @GET("api/Access")
    Call<ArrayList<UserAccess>> getAccess(@Header("Authorization") String authToken, @Query("id") int userID);

    @GET("api/AcProfils")
    Call<ArrayList<Profile>> getProfiles(@Header("Authorization") String authToken);

    @POST("api/Access")
    Call<Integer> crudProfileNew(@Header("Authorization") String authToken, @Body UserAccess userAccess);

    @POST("api/Access/update")
    Call<Integer> crudProfile(@Header("Authorization") String authToken, @Body UserAccess userAccess);



    @GET("/api/EventLogs")
    Call<List<EventLogData>> getEventLogData(@Header("Authorization") String authToken);

    @GET("api/EventLogs")
    Call<ArrayList<EventLogData>> getUserLog(@Header("Authorization") String authToken, @Query("id") int userID);



    @POST("/api/Users/register")
    Call<ResponseBody> setGuest(@Header("Authorization") String authToken, @Body GuestData guestData);


}
