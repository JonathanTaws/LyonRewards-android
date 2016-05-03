package h4311.hexanome.insa.lyonrewards.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import h4311.hexanome.insa.lyonrewards.business.act.TravelCitizenAct;

/**
 * Created by Pierre on 02/05/2016.
 */
public class TravelData {

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("new_total_km")
    @Expose
    private Float newTotalKm;

    @SerializedName("step_success")
    @Expose
    private Integer stepSuccess;

    @SerializedName("points_granted")
    @Expose
    private Integer pointsGranted;

    @SerializedName("citizen_acts")
    @Expose
    private List<TravelCitizenAct> travelCitizenActs = new ArrayList<>();

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
    public TravelData(String mode, Float newTotalKm, Integer stepSuccess, Integer pointsGranted, List<TravelCitizenAct> travelCitizenActs) {
        this.mode = mode;
        this.newTotalKm = newTotalKm;
        this.stepSuccess = stepSuccess;
        this.pointsGranted = pointsGranted;
        this.travelCitizenActs = travelCitizenActs;
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
    public Float getNewTotalKm() {
        return newTotalKm;
    }

    /**
     * @param newTotalKm The new_total_km
     */
    public void setNewTotalKm(Float newTotalKm) {
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

    public List<TravelCitizenAct> getTravelCitizenActs() {
        return travelCitizenActs;
    }

    public void setTravelCitizenActs(List<TravelCitizenAct> travelCitizenActs) {
        this.travelCitizenActs = travelCitizenActs;
    }
}