package com.petro.scope104.network;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final RetrofitInstance INSTANCE = new RetrofitInstance();
    private final Retrofit retrofit;
    public final GitHubService service;
    private RetrofitInstance(){
        Gson gson = new Gson();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(GitHubService.class);
    }
}
