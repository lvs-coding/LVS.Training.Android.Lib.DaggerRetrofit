package com.example.dagger2andretrofit2.http;

import com.example.dagger2andretrofit2.http.apimodel.Twitch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TwitchAPI {
    /**
     * @GET tells Retrofit it's a GET request.
     * games/top is the relative path for our API endpoint
     * @return
     *
     * The return type is the Call for our Twitch type.
     * This call object represents the network call that will be made to the remote API and the
     * JSON data downloaded will be deserialized into the mapped object type which is in our case
     * is Twitch POJO.
     */
    @GET("games/top")
    Call <Twitch> getTopGames(@Header("Client-Id") String clientId);
}
