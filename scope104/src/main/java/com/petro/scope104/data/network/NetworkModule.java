package com.petro.scope104.data.network;

import com.google.gson.Gson;
import com.petro.scope104.data.UserDataSource;
import com.petro.scope104.data.network.livedataadapter.LiveDataCallAdapterFactory;
import com.petro.scope104.data.qualifier.Network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class NetworkModule {
    @Provides
    @Singleton
    public static Retrofit provideRetrofit(Gson gson) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        return new Retrofit.Builder().client(client).baseUrl("https://randomuser.me/").addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(new LiveDataCallAdapterFactory()).build();
    }

    @Provides
    @Singleton
    public static Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public static GitHubService provideGitHubService(Retrofit retrofit) {
        return retrofit.create(GitHubService.class);
    }

    @Provides
    @Network
    @Singleton
    public static UserDataSource userNetworkDataSource(UserDataSourceNetworkImpl impl) {
        return impl;
    }
}