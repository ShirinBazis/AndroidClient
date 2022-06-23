package com.example.ex3.res.api;

import com.example.ex3.res.entities.Contact;
import com.example.ex3.res.entities.Invitation;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.entities.NewUser;
import com.example.ex3.res.entities.Transfer;
import com.example.ex3.res.entities.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    ///////////////////////
    @GET("contacts/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String id);

    @POST("contacts/{id}/messages")
    Call<Void> sendMessage(@Path("id") String id, @Body Message message);

    @POST("transfer")
    Call<Void> transfer(@Body Transfer transfer);

//    @POST("contacts/{id}")
//    Call<Void> addContact(@Path("id") int id);

}
