package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.JsonObject;

import java.util.List;

import h4311.hexanome.insa.lyonrewards.business.Event;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pierre on 26/04/2016.
 */
public interface LyonRewardsEndpoint {

    @GET("hello")
    Call<JsonObject> getHello();

    @GET("events")
    Call<List<Event>> getAllEvents();
}
