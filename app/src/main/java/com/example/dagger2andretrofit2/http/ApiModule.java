package com.example.dagger2andretrofit2.http;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implementation for our TwitchAPI interfece
 *
 * Don't forget to add this Module in App and in ApplicationComponent
 *
 * The methods in here can be used by Dagger to provide an instance of our Twitch API client
 */
@Module
public class ApiModule {
    public final String BASE_URL = "https://api.twitch.tv/kraken/";

    /**
     * Provide an instance of OkHttpClient
     * This is only necessary when we desire to add the interceptor to our Retrofit client
     * generally for logging purposes.
     *
     * You can get rid of this intire method if you are not interested in logging the request
     * @return
     */
    @Provides
    public OkHttpClient provideClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        /**
         * The BODY logging level logs request and response ines and their respective headers and
         * bodies (if present)
         */
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }

    /**
     * This method is for providing a Retrofit instance
     *
     * @param baseURL Twitch API base server URL
     * @param client This is necessary because we are using a custom interceptor for logging
     * @return Retrofit instance
     *
     * GsonConverterFactory.Create() a JSON converter which will be responsible for deserializing
     * the JSON data into a POJOs and vice versa
     */
    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Gets an instance of our Retrofit object calling the above methods then, using this Retrofit
     * object it creates an instance of our TwitchAPI interface by calling the create() method.
     *
     * @return instance of Twitch API interface
     */
    @Provides
    public TwitchAPI provideApiService() {
        return provideRetrofit(BASE_URL, provideClient()).create(TwitchAPI.class);
    }

}
