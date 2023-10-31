package com.example.sarifle.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("read/")
    Call<List<Post>> getPost();
}
