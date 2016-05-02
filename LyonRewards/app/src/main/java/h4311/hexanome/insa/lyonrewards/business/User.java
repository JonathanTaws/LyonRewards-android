package h4311.hexanome.insa.lyonrewards.business;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jonathan on 28/04/2016.
 */
public class User implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("global_points")
    @Expose
    private int globalPoints;

    @SerializedName("last_tfh_points")
    @Expose
    private int last24hPoints;

    @SerializedName("last_month_points")
    @Expose
    private int lastMonthPoints;

    @SerializedName("current_points")
    @Expose
    private int currentPoints;

    @SerializedName("bike_distance")
    @Expose
    private int bikeDistance;

    @SerializedName("walk_distance")
    @Expose
    private int walkDistance;

    @SerializedName("tram_distance")
    @Expose
    private int tramDistance;

    @SerializedName("bus_distance")
    @Expose
    private int busDistance;

    private String currentToken;

    /**
     * No args constructor for use in serialization
     */
    public User() {

    }

    public User(int id, String username, String password, String firstName, String lastName, String email, int globalPoints, int currentPoints, int last24hPoints, int lastMonthPoints, int bikeDistance, int walkDistance, int tramDistance, int busDistance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.globalPoints = globalPoints;
        this.currentPoints = currentPoints;
        this.last24hPoints = last24hPoints;
        this.lastMonthPoints = lastMonthPoints;
        this.bikeDistance = bikeDistance;
        this.walkDistance = walkDistance;
        this.tramDistance = tramDistance;
        this.busDistance = busDistance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGlobalPoints() {
        return globalPoints;
    }

    public void setGlobalPoints(int globalPoints) {
        this.globalPoints = globalPoints;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public int getLast24hPoints() {
        return last24hPoints;
    }

    public void setLast24hPoints(int last24hPoints) {
        this.last24hPoints = last24hPoints;
    }

    public int getLastMonthPoints() {
        return lastMonthPoints;
    }

    public void setLastMonthPoints(int lastMonthPoints) {
        this.lastMonthPoints = lastMonthPoints;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            User user = (User) obj;
            // Check equality without taking into account the token
            return email.equals(user.email) && firstName.equals(user.firstName) && lastName.equals(user.lastName)
                    && password.equals(user.password) && username.equals(user.username) && currentPoints == user.currentPoints
                    && globalPoints == user.globalPoints && last24hPoints == user.last24hPoints && lastMonthPoints == user.lastMonthPoints;
        }
        return false;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.globalPoints = in.readInt();
        this.currentPoints = in.readInt();
        this.currentToken = in.readString();
        this.last24hPoints = in.readInt();
        this.lastMonthPoints = in.readInt();
        this.bikeDistance = in.readInt();
        this.walkDistance = in.readInt();
        this.tramDistance = in.readInt();
        this.busDistance = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeInt(globalPoints);
        dest.writeInt(currentPoints);
        dest.writeString(currentToken);
        dest.writeInt(last24hPoints);
        dest.writeInt(lastMonthPoints);
        dest.writeInt(bikeDistance);
        dest.writeInt(walkDistance);
        dest.writeInt(tramDistance);
        dest.writeInt(busDistance);
    }

    public int getBikeDistance() {
        return bikeDistance;
    }

    public void setBikeDistance(int bikeDistance) {
        this.bikeDistance = bikeDistance;
    }

    public int getWalkDistance() {
        return walkDistance;
    }

    public void setWalkDistance(int walkDistance) {
        this.walkDistance = walkDistance;
    }

    public int getTramDistance() {
        return tramDistance;
    }

    public void setTramDistance(int tramDistance) {
        this.tramDistance = tramDistance;
    }

    public int getBusDistance() {
        return busDistance;
    }

    public void setBusDistance(int busDistance) {
        this.busDistance = busDistance;
    }
}
