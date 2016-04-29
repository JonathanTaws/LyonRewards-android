package h4311.hexanome.insa.lyonrewards.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import h4311.hexanome.insa.lyonrewards.business.act.Partner;

/**
 * Created by Pierre on 29/04/2016.
 */
public class UserConnection {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("user")
    @Expose
    private User user;

    public UserConnection() {
    }

    public UserConnection(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
