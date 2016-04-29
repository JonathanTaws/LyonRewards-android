package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jonathan on 29/04/2016.
 */
public class QrCodeContent implements Parcelable {

    private String value;
    private int actId;
    private int eventId;

    public QrCodeContent(String value, int actId, int eventId) {
        this.value = value;
        this.actId = actId;
        this.eventId = eventId;
    }

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final Parcelable.Creator<QrCodeContent> CREATOR = new Parcelable.Creator<QrCodeContent>() {
        public QrCodeContent createFromParcel(Parcel in) {
            return new QrCodeContent(in);
        }

        public QrCodeContent[] newArray(int size) {
            return new QrCodeContent[size];
        }
    };

    public QrCodeContent(Parcel in) {
        this.value = in.readString();
        this.actId = in.readInt();
        this.eventId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeInt(actId);
        dest.writeInt(eventId);
    }
}
