package com.example.baptiste.downloadvolley;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by baptiste on 02/06/17.
 */

public interface JsonPlaceHolder {
    @GET("/users")
    Call<List<User>> listUsers();

}
