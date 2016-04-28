package h4311.hexanome.insa.lyonrewards.business;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("points")
    @Expose
    private Integer points;

    @SerializedName("partner")
    @Expose
    private Integer partner;

    /**
     * No args constructor for use in serialization
     */
    public Offer() {
    }

    /**
     * @param id
     * @param description
     * @param partner
     * @param points
     */
    public Offer(Integer id, String description, Integer points, Integer partner) {
        this.id = id;
        this.description = description;
        this.points = points;
        this.partner = partner;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * @param points The points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * @return The partner
     */
    public Integer getPartner() {
        return partner;
    }

    /**
     * @param partner The partner
     */
    public void setPartner(Integer partner) {
        this.partner = partner;
    }

}