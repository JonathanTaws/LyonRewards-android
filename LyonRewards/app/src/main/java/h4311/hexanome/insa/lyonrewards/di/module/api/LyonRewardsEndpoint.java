package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.JsonObject;

import java.util.List;

import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.business.UserConnection;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pierre on 26/04/2016.
 */
public interface LyonRewardsEndpoint {

    @GET("hello")
    Call<JsonObject> getHello();

    @GET("offers")
    Call<List<Offer>> getAllOffers();

    @GET("events")
    Call<List<Event>> getAllEvents();

    @GET("events/{id}")
    Call<Event> getEventById(@Path("id") int eventId, @Query("userId") int userId);

    @GET("events")
    Call<List<Event>> getAllEvents(@Query("userId") int userId, @Query("participatedOnly") boolean userParticipatedOnly);

    @GET("events/{id}/qrcodes")
    Call<List<QRCodeCitizenAct>> getQrCodeFromEvent(@Path("id") int eventId, @Query("userId") int userId);

    @GET("acts/{id}")
    Call<QRCodeCitizenAct> getQrCodeById(@Path("id") int qrCodeId);

    @POST("users/{userId}/acts/{actId}/credit")
    Call<User> addActToUser(@Path("userId") int userId, @Path("actId") int actId);

    @GET("users/{username}")
    Call<User> getUserByUserName(@Path("username") String username);

    @FormUrlEncoded
    @POST("login/")
    Call<UserConnection> login(@Field("username") String username, @Field("password") String password);
}
