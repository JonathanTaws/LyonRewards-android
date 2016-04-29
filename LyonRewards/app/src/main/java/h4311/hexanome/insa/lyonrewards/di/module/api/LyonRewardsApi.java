package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.business.act.CitizenAct;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class LyonRewardsApi {

    private LyonRewardsEndpoint mLyonRewardsEndpoint;

    public LyonRewardsApi(LyonRewardsEndpoint lyonRewardsEndpoint) {
        this.mLyonRewardsEndpoint = lyonRewardsEndpoint;
    }

    public void getHello(Callback<JsonObject> callback) {
        Call<JsonObject> call = mLyonRewardsEndpoint.getHello();
        call.enqueue(callback);
    }

    public void getAllOffers(Callback<List<Offer>> callback) {
        Call<List<Offer>> allOffers = mLyonRewardsEndpoint.getAllOffers();
        allOffers.enqueue(callback);
    }

    public void getAllEvents(Callback<List<Event>> callback) {
        Call<List<Event>> allEvents = mLyonRewardsEndpoint.getAllEvents();
        allEvents.enqueue(callback);
    }

    public void getEventById(int eventId, Callback<Event> callback) {
        Call<Event> event = mLyonRewardsEndpoint.getEventById(eventId);
        event.enqueue(callback);
    }

    public void addActToUser(User user, CitizenAct act, Callback<JsonObject> callback) {
        Call<JsonObject> call = mLyonRewardsEndpoint.addActToUser(user.getId(), act.getId());
        call.enqueue(callback);
    }

    public User getUserByUserName(String username) {
        Call<User> call = mLyonRewardsEndpoint.getUserByUserName(username);
        Response<User> execute;
        try {
            execute = call.execute();
        } catch (IOException e) {
            return null;
        }
        return execute.body();
    }

    public String loginUser(String username, String password) throws IOException {
        Call<JsonObject> call = mLyonRewardsEndpoint.login(username, password);
        Response<JsonObject> execute = call.execute();
        JsonObject body = execute.body();
        if (body == null) {
            return null;
        }
        JsonElement element = body.get("token");
        if (element == null) {
            return null;
        } else {
            return element.getAsString();
        }
        /*
        Call<JsonObject> call = mLyonRewardsEndpoint.login(username, password);
        Response<JsonObject> execute = call.execute();
        JsonElement jsonElement = execute.body().get("token");
        if (jsonElement == null) {
            return null;
        } else {
            return jsonElement.getAsString();
        }*/
    }

}
