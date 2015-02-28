package utils;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by kritarie on 2/28/15.
 *
 * Retrofit REST client builder
 */
public class RestClient {

    private static RestApi REST_CLIENT;
    private static String ROOT =
            "http://seedle.azurewebsites.net/";

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static RestApi get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(RestApi.class);
    }
}
