package h4311.hexanome.insa.lyonrewards.business.act;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jonathan on 28/04/2016.
 */
public class QRCodeCitizenAct extends CitizenAct {

    @SerializedName("treasure_hunt")
    @Expose
    private Integer treasureHunt;

    @SerializedName("completed")
    @Expose
    private Boolean completed;

    @SerializedName("date")
    @Expose
    private Date completedDate;

    private String eventTitle;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

    /**
     * No args constructor for use in serialization
     */
    public QRCodeCitizenAct() {
    }

    /**
     * @param id
     * @param title
     * @param description
     * @param points
     * @param treasureHunt
     * @param completed
     * @param completedDate
     */
    public QRCodeCitizenAct(Integer id, String title, String description, Integer points, Integer treasureHunt, Boolean completed, Date completedDate) {
        super(id, title, description, points);
        this.treasureHunt = treasureHunt;
        this.completed = completed;
        this.completedDate = completedDate;
    }

    /**
     * @return The treasureHunt
     */
    public Integer getTreasureHunt() {
        return treasureHunt;
    }

    /**
     * @param treasureHunt The treasure_hunt
     */
    public void setTreasureHunt(Integer treasureHunt) {
        this.treasureHunt = treasureHunt;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public String getCompletedDateString() {
        return simpleDateFormat.format(completedDate);
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
}