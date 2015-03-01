package utils;

import com.awsickapps.helpinghands.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Kritarie on 2/28/15.
 *
 * Defines HTTP requests
 */
public interface RestApi {

    @POST("/api/users/CreateUser")
    public void createUser(String id, Callback<Integer> cb);

    @POST("/api/users/UpdateUser")
    public void updateUser(@Body User user, Callback<Integer> cb);

    @POST("/api/NeedHelp")
    public void requestHelp(String id, String ailment, double lat, double lng, Callback<Integer> cb);
}