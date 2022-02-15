package hr.fer.wpu.usersapp.rest;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface UserDataService {
    @GET("/userdatas")
    List<UserData> getAll();

    @POST("/userdatas")
    Void insert(@Body UserData userData);

    @DELETE("/userdatas/{uuid}")
    Void deleteByUuid(@Path("uuid") String uuid);

    @PUT("/userdatas/{uuid}")
    Void update(@Body UserData userData, @Path("uuid") String uuid);
}
