package com.example.dagger2andretrofit2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dagger2andretrofit2.http.TwitchAPI;
import com.example.dagger2andretrofit2.http.apimodel.Top;
import com.example.dagger2andretrofit2.http.apimodel.Twitch;
import com.example.dagger2andretrofit2.root.App;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by laurent on 2/2/17.
 */

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = MainActivity.class.getSimpleName();
    @Inject
    TwitchAPI twitchAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App)getApplication()).getComponent().inject(this);

        Call<Twitch> call = twitchAPI.getTopGames(getResources().getString(R.string.client_id));

        /**
         * Here we have two options :
         * 1 - Enqueue the call, so we can get our response in a call-back, our request is
         * processed in an asynchronous manner.
         * 2 - Call execute to transmit them on main thread in a synchronous manner
         *
         * In this cas the 2  option would not work because the app will crash with a
         * NetworkOnMainThreadException
         */
        call.enqueue(new Callback<Twitch>() {
            /**
             * The callback object is of type Twitch which matches the Call object type of our
             * TwitchAPI interface.
             */
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                /**
                 *  Extract object from response, note that onResponse and on Failure method will
                 * be called on the main thread.
                 * If the response is not parsable then response.body() will return null
                 *
                 * Notice that all the required methods to access the data are provided by the
                 * java objects we generated from the JSON schema website
                 */
                List<Top> gameList = response.body().getTop();

                for (Top top : gameList){
                    Log.d(LOG_TAG,top.getGame().getName());
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
