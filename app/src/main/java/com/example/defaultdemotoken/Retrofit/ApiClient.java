package com.example.defaultdemotoken.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    //http://dkbraende.demoproject.info/rest/V1/"
    //http://dkbraende.demoproject.info/rest/V1/

    public static final String MAIN_URLL = "http://dkbraende.demoproject.info/rest/V1/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(MAIN_URLL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }



}
