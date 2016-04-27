package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.JsonObject;

import java.util.List;

import h4311.hexanome.insa.lyonrewards.business.Event;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Pierre on 26/04/2016.
 */
public class LyonRewardsApi {

    private LyonRewardsEndpoint mLyonRewardsEndpoint;

    public LyonRewardsApi(LyonRewardsEndpoint lyonRewardsEndpoint) {
        this.mLyonRewardsEndpoint = lyonRewardsEndpoint;
    }

    public void getHello(Callback<JsonObject> callback) {
        Call<JsonObject> call = mLyonRewardsEndpoint.getHello();
        call.enqueue(callback);
    }

    public void getAllEvents(Callback<List<Event>> callback) {
        Call<List<Event>> allEvents = mLyonRewardsEndpoint.getAllEvents();
        allEvents.enqueue(callback);
    }
}
