package h4311.hexanome.insa.lyonrewards.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pierre on 02/05/2016.
 */
public class TravelData {

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("new_total_km")
    @Expose
    private Integer newTotalKm;

    @SerializedName("step_success")
    @Expose
    private Integer stepSuccess;

    @SerializedName("points_granted")
    @Expose
    private Integer pointsGranted;

    /**
     * No args constructor for use in serialization
     */
    public TravelData() {
    }

    /**
     * @param newTotalKm
     * @param pointsGranted
     * @param stepSuccess
     * @param mode
     */
    public TravelData(String mode, Integer newTotalKm, Integer stepSuccess, Integer pointsGranted) {
        this.mode = mode;
        this.newTotalKm = newTotalKm;
        this.stepSuccess = stepSuccess;
        this.pointsGranted = pointsGranted;
    }

    /**
     * @return The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return The newTotalKm
     */
    public Integer getNewTotalKm() {
        return newTotalKm;
    }

    /**
     * @param newTotalKm The new_total_km
     */
    public void setNewTotalKm(Integer newTotalKm) {
        this.newTotalKm = newTotalKm;
    }

    /**
     * @return The stepSuccess
     */
    public Integer getStepSuccess() {
        return stepSuccess;
    }

    /**
     * @param stepSuccess The step_success
     */
    public void setStepSuccess(Integer stepSuccess) {
        this.stepSuccess = stepSuccess;
    }

    /**
     * @return The pointsGranted
     */
    public Integer getPointsGranted() {
        return pointsGranted;
    }

    /**
     * @param pointsGranted The points_granted
     */
    public void setPointsGranted(Integer pointsGranted) {
        this.pointsGranted = pointsGranted;
    }

}