package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.JsonObject;

import java.util.List;

import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.TravelData;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.business.UserConnection;
import h4311.hexanome.insa.lyonrewards.business.UserTravelProgression;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
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

    /** --------------------------------------- OFFERS ---------------------------------------  **/

    @GET("offers")
    Call<List<Offer>> getAllOffers();

    @GET("offers/{id}")
    Call<Offer> getOfferById(@Path("id") int offerId);

    /** --------------------------------------- EVENTS ---------------------------------------  **/

    @GET("events")
    Call<List<Event>> getAllEvents();

    @GET("events/{id}")
    Call<Event> getEventById(@Path("id") int eventId, @Query("userId") int userId);

    @GET("events")
    Call<List<Event>> getAllEvents(@Query("userId") int userId, @Query("sort") String sortBy, @Query("participatedOnly") boolean userParticipatedOnly);

    @GET("events")
    Call<List<Event>> getAllEventsType(@Query("userId") int userId, @Query("sort") String sortBy, @Query("participatedOnly") boolean userParticipatedOnly, @Query("type") String type);

    /** ------------------------------------ CITIZEN ACTS ------------------------------------  **/

    @GET("events/{id}/qrcodes")
    Call<List<QRCodeCitizenAct>> getQrCodesListFromEvent(@Path("id") int eventId, @Query("userId") int userId);

    @GET("acts/{id}")
    Call<QRCodeCitizenAct> getQrCodeById(@Path("id") int qrCodeId, @Query("userId") int userId, @Query("type") String type);

    /** ---------------------------------------- USERS ---------------------------------------  **/

    @POST("users/{userId}/acts/{actId}/credit")
    Call<User> addActToUser(@Path("userId") int userId, @Path("actId") int actId);

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("users/{username}")
    Call<User> getUserByUserName(@Path("username") String username);

    @GET("users/{userId}/travelprogress")
    Call<UserTravelProgression> getUserTravelProgression(@Path("userId") int userId);

    @FormUrlEncoded
    @POST("login/")
    Call<UserConnection> login(@Field("username") String username, @Field("password") String password);

    @POST("users/{userId}/travel/")
    Call<TravelData> travel(@Path("userId") int userId, @Body JsonObject bodyData);

    @GET("users/ranking")
    Call<List<User>> getRankings();

    @GET("users/{id}/history")
    Call<List<JsonObject>> getUserHistory(@Path("id") int id, @Query("limit") int limit);
}
