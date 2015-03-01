package utils;

import com.awsickapps.helpinghands.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Kritarie on 2/28/15.
 *
 * Defines HTTP requests
 */
public interface RestApi {

    @FormUrlEncoded
    @POST("/api/user/CreateUser")
    public void createUser(@Field("DeviceId") String id, Callback<Integer> cb);

    @POST("/api/user/UpdateUser")
    public void updateUser(@Body User user, Callback<Integer> cb);

    @FormUrlEncoded
    @POST("/api/RequestHelp")
    public void requestHelp(@Field("DeviceId") String id,
                           @Field("Ailment") String ailment,
                           @Field("lat") double lat,
                           @Field("lng") double lng, Callback<Integer> cb);
}