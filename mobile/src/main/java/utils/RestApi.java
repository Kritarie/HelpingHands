package utils;

import com.awsickapps.helpinghands.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kritarie on 2/28/15.
 *
 * Defines HTTP requests
 */
public interface RestApi {

    @FormUrlEncoded
    @POST("/api/user/CreateUser")
    public void createUser(@Field("DeviceId") String id, Callback<Integer> cb);

    @FormUrlEncoded
    @POST("/api/user/UpdateUser")
    public void updateUser(@Field("DeviceId") String deviceId,@Field("CanHelp") List<String> canHelp,@Field("NeedHelp") List<String> needHelp , Callback<Integer> cb);

    @GET("/api/user/RequestHelp")
    public void requestHelp(@Query("DeviceId") String id,
                           @Query("Ailment") String ailment,
                           @Query("lat") double lat,
                           @Query("lng") double lng, Callback<Integer> cb);
}