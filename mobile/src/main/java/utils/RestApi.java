package utils;

import com.awsickapps.helpinghands.User;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by kritarie on 2/28/15.
 *
 * Defines HTTP requests
 */
public interface RestApi {

    @POST("/api/users/CreateUser")
    public int createUser(@Body String id);

}