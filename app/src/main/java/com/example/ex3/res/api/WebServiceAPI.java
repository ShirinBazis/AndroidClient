package com.example.ex3.res.api;

import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Invitation;
import com.example.ex3.res.entities.NewUser;
import com.example.ex3.res.entities.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @GET("contacts")
    Call<List<Contact>> getContacts();

    @POST("contacts")
    Call<Void> addContact(@Body Contact contact);

    @POST("invitations")
    Call<Void> sendInvitation(@Body Invitation invitation);

    @POST("users/Login")
    Call<ResponseBody> login(@Body User user);

    @POST("users/Register")
    Call<Void> register(@Body NewUser newUser);

//    @POST("contacts/{id}")
//    Call<Void> addContact(@Path("id") int id);

}
