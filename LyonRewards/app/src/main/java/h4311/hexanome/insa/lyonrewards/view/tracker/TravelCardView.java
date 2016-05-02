package h4311.hexanome.insa.lyonrewards.view.tracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.User;

/**
 * Created by Pierre on 02/05/2016.
 */
public class TravelCardView extends LinearLayout {

    @BindView(R.id.card_travel_type)
    protected TextView mType;

    @BindView(R.id.card_travel_image)
    protected ImageView mImage;

    @BindView(R.id.card_travel_number_km)
    protected TextView mNumberKm;

    @BindView(R.id.card_travel_number_km_progress)
    protected TextView mNumberKmProgress;

    private User mUser;

    public enum TYPE {
        WALK,
        BIKE,
        TRAM,
        BUS
    }

    public TravelCardView(Activity activity, TYPE type, User user) {
        super(activity);
        mUser = user;

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.travel_cardview, null);
        ButterKnife.bind(this, child);

        addView(child);

        setType(type);

    }

    public void setType(TYPE type) {
        String nameType = "";
        int imageRes = 0;
        int numberKm = 0;

        switch (type) {
            case WALK:
                nameType = "pied";
                imageRes = R.drawable.ic_directions_walk_black_48dp;
                numberKm = mUser.getWalkDistance();
                break;
            case BIKE:
                nameType = "vélo";
                imageRes = R.drawable.ic_directions_bike_black_48dp;
                numberKm = mUser.getBikeDistance();
                break;
            case TRAM:
                nameType = "tram";
                imageRes = R.drawable.ic_tram_black_48dp;
                numberKm = mUser.getTramDistance();
                break;
            case BUS:
                nameType = "bus";
                imageRes = R.drawable.ic_directions_bus_black_48dp;
                numberKm = mUser.getBusDistance();
                break;
        }

        mType.setText(nameType);
        mImage.setImageResource(imageRes);
        mNumberKm.setText(String.valueOf(numberKm));
        mNumberKmProgress.setText(String.valueOf(numberKm));
    }
}
