package com.bcs.basegpt.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
        @GET("chat/{id}")
        Call<Chat> getPost(@Path("id") int chatId);

        @GET("chat")
        Call<Chat> getPosts();

        @POST("chat")
        Call<Chat> askAQuestion(@Body Chat chat);
}
