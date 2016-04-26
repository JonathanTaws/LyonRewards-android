package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.JsonObject;

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
}
