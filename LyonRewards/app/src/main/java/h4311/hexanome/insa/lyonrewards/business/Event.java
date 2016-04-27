package h4311.hexanome.insa.lyonrewards.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pierre on 27/04/2016.
 */
public class Event {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("publishDate")
    @Expose
    private Date publishDate;

    @SerializedName("start_date")
    @Expose
    private Date startDate;

    @SerializedName("end_date")
    @Expose
    private Date endDate;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("tags")
    @Expose
    private List<Integer> tags = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     */
    public Event() {
    }

    /**
     * @param id
     * @param title
     * @param description
     * @param publishDate
     * @param startDate
     * @param endDate
     * @param latitude
     * @param longitude
     * @param tags
     */
    public Event(Integer id, String title, String description, Date publishDate, Date startDate, Date endDate, Double latitude, Double longitude, List<Integer> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
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
     * @return The publishDate
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * @param publishDate The publishDate
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * @return The startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate The end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The tags
     */
    public List<Integer> getTags() {
        return tags;
    }

    /**
     * @param tags The tags
     */
    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

}
