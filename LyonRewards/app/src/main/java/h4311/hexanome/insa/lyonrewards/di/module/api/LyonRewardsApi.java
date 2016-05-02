package h4311.hexanome.insa.lyonrewards.di.module.api;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.business.UserConnection;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void getOfferById(int offerId, Callback<Offer> callback) {
        Call<Offer> call = mLyonRewardsEndpoint.getOfferById(offerId);
        call.enqueue(callback);
    }

    public void getAllEvents(Callback<List<Event>> callback) {
        Call<List<Event>> allEvents = mLyonRewardsEndpoint.getAllEvents();
        allEvents.enqueue(callback);
    }

    public void getAllEventsWithUserProgression(int userId, Callback<List<Event>> callback) {
        getAllEvents(userId, false, callback);
    }



    public void getEventById(int eventId, int userId, Callback<Event> callback) {
        Call<Event> event = mLyonRewardsEndpoint.getEventById(eventId, userId);
        event.enqueue(callback);
    }

    public Event getEventById(int eventId, int userId) {
        Call<Event> event = mLyonRewardsEndpoint.getEventById(eventId, userId);
        try {
            Response<Event> execute = event.execute();
            if (execute.isSuccessful()) {
                return execute.body();
            } else {
                // todo handle error
                return null;
            }
        } catch (IOException e) {
            // todo handle error
            return null;
        }
    }

    public void getMyEvents(int userId, Callback<List<Event>> callback) {
        Call<List<Event>> allEvents = mLyonRewardsEndpoint.getAllEvents(userId, "startDate", true);
        allEvents.enqueue(callback);
    }

    public void getAllEvents(int userId, boolean userParticipatedOnly, Callback<List<Event>> callback) {
        Call<List<Event>> allEvents = mLyonRewardsEndpoint.getAllEvents(userId, "startDate", userParticipatedOnly);
        allEvents.enqueue(callback);
    }

    public void getAllEventsOngoing(int userId, boolean userParticipatedOnly, Callback<List<Event>> callback) {
        Call<List<Event>> allEvents = mLyonRewardsEndpoint.getAllEventsType(userId, "startDate", userParticipatedOnly, "ongoing");
        allEvents.enqueue(callback);
    }

    public void getAllEventsFuture(int userId, boolean userParticipatedOnly, Callback<List<Event>> callback) {
        Call<List<Event>> allEvents = mLyonRewardsEndpoint.getAllEventsType(userId, "startDate", userParticipatedOnly, "future");
        allEvents.enqueue(callback);
    }

    public void getQrCodesFromEvent(int eventId, int userId, Callback<List<QRCodeCitizenAct>> callback) {
        Call<List<QRCodeCitizenAct>> event = mLyonRewardsEndpoint.getQrCodesListFromEvent(eventId, userId);
        event.enqueue(callback);
    }

    public void getQrCodeById(int qrCodeId, int userId, Callback<QRCodeCitizenAct> callback) {
        Call<QRCodeCitizenAct> call = mLyonRewardsEndpoint.getQrCodeById(qrCodeId, userId, "qrcode");
        call.enqueue(callback);
    }

    public QRCodeCitizenAct getQrCodeById(int qrCodeId, int userId) {
        Call<QRCodeCitizenAct> call = mLyonRewardsEndpoint.getQrCodeById(qrCodeId, userId, "qrcode");
        try {
            Response<QRCodeCitizenAct> execute = call.execute();
            if (execute.isSuccessful()) {
                return execute.body();
            } else {
                // todo handle error
                return null;
            }
        } catch (IOException e) {
            // todo handle error
            return null;
        }
    }


    public void addActToUser(User user, int actId, Callback<User> callback) {
        Call<User> call = mLyonRewardsEndpoint.addActToUser(user.getId(), actId);
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

    public User loginUser(String username, String password) throws IOException {
        Call<UserConnection> call = mLyonRewardsEndpoint.login(username, password);
        Response<UserConnection> execute = call.execute();

        UserConnection userConnection = execute.body();
        if (userConnection == null) {
            return null;
        } else {
            User user = userConnection.getUser();
            user.setCurrentToken(userConnection.getToken());
            return user;
        }
    }

    public void getRankings(Callback<List<User>> callback) {
        Call<List<User>> call = mLyonRewardsEndpoint.getRankings();
        call.enqueue(callback);
    }
}
