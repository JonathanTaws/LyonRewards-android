package h4311.hexanome.insa.lyonrewards.business.act;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CitizenAct {

    @SerializedName("id")
    @Expose
    protected Integer id;

    @SerializedName("title")
    @Expose
    protected String title;

    @SerializedName("description")
    @Expose
    protected String description;

    @SerializedName("points")
    @Expose
    protected Integer points;

    /**
     * No args constructor for use in serialization
     */
    public CitizenAct() {
    }

    /**
     * @param id
     * @param title
     * @param description
     * @param points
     */
    public CitizenAct(Integer id, String title, String description, Integer points) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.points = points;
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
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
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
}
