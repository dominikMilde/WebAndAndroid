package hr.fer.wpu.usersapp.rest;


import retrofit.RestAdapter;

public class RestAdapterSingleton {

    private static RestAdapter instance;
    private static final String BASE_URL = "http://192.168.5.25:9090";

    public static RestAdapter getInstance() {
        if (instance == null) {
            instance = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
