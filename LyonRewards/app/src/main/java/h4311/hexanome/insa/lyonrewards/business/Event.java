package h4311.hexanome.insa.lyonrewards.business;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pierre on 27/04/2016.
 */
public class Event implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("publish_date")
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

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("tags")
    @Expose
    private List<String> tags = new ArrayList<>();

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("progress")
    @Expose
    private Float userProgression;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
     * @param address
     * @param userProgression
     */
    public Event(Integer id, String title, String description, Date publishDate, Date startDate, Date endDate, Double latitude, Double longitude, String imageUrl, List<String> tags, String address, Float userProgression) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.address = address;
        this.userProgression = userProgression;
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
     * @return The publishDate as a String
     */
    public String getPublishDateString() {
        return simpleDateFormat.format(publishDate);
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
     * @return The startDate as a String
     */
    public String getStartDateString() {
        return simpleDateFormat.format(startDate);
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
     * @return The endDate as a String
     */
    public String getEndDateString() {
        return simpleDateFormat.format(endDate);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param tags The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getUserProgression() {
        return userProgression;
    }

    public void setUserProgression(Float userProgression) {
        this.userProgression = userProgression;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(description);
        out.writeLong(publishDate.getTime());
        out.writeLong(startDate.getTime());
        out.writeLong(endDate.getTime());
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeString(imageUrl);
        out.writeList(tags);
        out.writeString(address);
        out.writeFloat(userProgression);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Event(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.publishDate = new Date(in.readLong());
        this.startDate = new Date(in.readLong());
        this.endDate = new Date(in.readLong());
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.imageUrl = in.readString();
        in.readList(this.tags, String.class.getClassLoader());
        this.address = in.readString();
        this.userProgression = in.readFloat();
    }



}
