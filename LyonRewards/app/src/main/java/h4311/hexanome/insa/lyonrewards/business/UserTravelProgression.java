package h4311.hexanome.insa.lyonrewards.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pierre on 02/05/2016.
 */
public class UserTravelProgression {

    @SerializedName("bus_progress")
    @Expose
    private Float busProgress;

    @SerializedName("walk_progress")
    @Expose
    private Float walkProgress;

    @SerializedName("tram_progress")
    @Expose
    private Float tramProgress;

    @SerializedName("bike_progress")
    @Expose
    private Float bikeProgress;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserTravelProgression() {
    }

    /**
     *
     * @param walkProgress
     * @param busProgress
     * @param bikeProgress
     * @param tramProgress
     */
    public UserTravelProgression(Float busProgress, Float walkProgress, Float tramProgress, Float bikeProgress) {
        this.busProgress = busProgress;
        this.walkProgress = walkProgress;
        this.tramProgress = tramProgress;
        this.bikeProgress = bikeProgress;
    }

    /**
     *
     * @return
     * The busProgress
     */
    public Float getBusProgress() {
        return busProgress;
    }

    /**
     *
     * @param busProgress
     * The bus_progress
     */
    public void setBusProgress(Float busProgress) {
        this.busProgress = busProgress;
    }

    /**
     *
     * @return
     * The walkProgress
     */
    public Float getWalkProgress() {
        return walkProgress;
    }

    /**
     *
     * @param walkProgress
     * The walk_progress
     */
    public void setWalkProgress(Float walkProgress) {
        this.walkProgress = walkProgress;
    }

    /**
     *
     * @return
     * The tramProgress
     */
    public Float getTramProgress() {
        return tramProgress;
    }

    /**
     *
     * @param tramProgress
     * The tram_progress
     */
    public void setTramProgress(Float tramProgress) {
        this.tramProgress = tramProgress;
    }

    /**
     *
     * @return
     * The bikeProgress
     */
    public Float getBikeProgress() {
        return bikeProgress;
    }

    /**
     *
     * @param bikeProgress
     * The bike_progress
     */
    public void setBikeProgress(Float bikeProgress) {
        this.bikeProgress = bikeProgress;
    }

}
