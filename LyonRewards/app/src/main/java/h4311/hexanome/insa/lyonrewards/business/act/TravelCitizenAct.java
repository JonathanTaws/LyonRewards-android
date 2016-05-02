package h4311.hexanome.insa.lyonrewards.business.act;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pierre on 02/05/2016.
 */
public class TravelCitizenAct extends CitizenAct {

    @SerializedName("distance_step")
    @Expose
    private Integer distanceStep;

    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     */
    public TravelCitizenAct() {
    }

    public TravelCitizenAct(Integer id, String title, String description, Integer points, Integer distanceStep, String type) {
        super(id, title, description, points);
        this.distanceStep = distanceStep;
        this.type = type;
    }

    public Integer getDistanceStep() {
        return distanceStep;
    }

    public void setDistanceStep(Integer distanceStep) {
        this.distanceStep = distanceStep;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
