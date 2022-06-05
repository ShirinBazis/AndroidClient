package com.example.ex3.res.api;

import com.example.ex3.res.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @GET("contacts")
    Call<List<Contact>> getContacts();

    @POST("contacts")
    Call<Void> addContact(@Body Contact contact);

//    @POST("contacts/{id}")
//    Call<Void> addContact(@Path("id") int id);

}
