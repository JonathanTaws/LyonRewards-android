package h4311.hexanome.insa.lyonrewards.business.act;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jonathan on 28/04/2016.
 */
public class QRCodeCitizenAct extends CitizenAct {

    @SerializedName("treasure_hunt")
    @Expose
    private Integer treasureHunt;

    /**
     * No args constructor for use in serialization
     */
    public QRCodeCitizenAct() {
    }

    /**
     * @param id
     * @param title
     * @param description
     * @param treasureHunt
     * @param points
     */
    public QRCodeCitizenAct(Integer id, String title, String description, Integer points, Integer treasureHunt) {
        super(id, title, description, points);
        this.treasureHunt = treasureHunt;
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
}