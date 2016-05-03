package h4311.hexanome.insa.lyonrewards.view.tracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

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

    @BindView(R.id.card_travel_number_points)
    protected TextView mNumberPoints;

    @BindView(R.id.card_travel_progress_percent)
    protected TextView mProgressPercent;

    @BindView(R.id.card_travel_circularProgress)
    protected CircularProgressBar mCircularProgressBar;

    private User mUser;

    private int mNumberPointsValue;


    public enum TYPE {
        WALK,
        BIKE,
        TRAM,
        BUS,
        CAR
    }

    public TravelCardView(Activity activity, TYPE type, User user) {
        this(activity, user);

        setType(type);
    }

    public TravelCardView(Activity activity, User user) {
        super(activity);
        mUser = user;

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.travel_cardview, null);
        ButterKnife.bind(this, child);

        addView(child);
    }

    public void setType(TYPE type) {
        String nameType = "";
        int imageRes = 0;
        float numberKm = 0;
        int numberPoints = 0;

        switch (type) {
            case WALK:
                nameType = "pied";
                imageRes = R.drawable.ic_directions_walk_black_48dp;
                numberKm = (int) mUser.getWalkDistance();
                numberPoints = mUser.getWalkPoints();
                break;
            case BIKE:
                nameType = "vélo";
                imageRes = R.drawable.ic_directions_bike_black_48dp;
                numberKm = (int) mUser.getBikeDistance();
                numberPoints = mUser.getBikePoints();
                break;
            case TRAM:
                nameType = "tram";
                imageRes = R.drawable.ic_tram_black_48dp;
                numberKm = (int) mUser.getTramDistance();
                numberPoints = mUser.getTramPoints();
                break;
            case BUS:
                nameType = "bus";
                imageRes = R.drawable.ic_directions_bus_black_48dp;
                numberKm = (int) mUser.getBusDistance();
                numberPoints = mUser.getBusPoints();
                break;
        }

        mNumberPointsValue = numberPoints;

        mType.setText(nameType);
        mImage.setImageResource(imageRes);
        mNumberKm.setText(String.format("%.02f", numberKm));
        mNumberKmProgress.setText(String.format("%.02f", numberKm));
        mNumberPoints.setText(String.valueOf(mNumberPointsValue));
    }

    public void updateProgression(Float progress, float newTotalKm) {
        mProgressPercent.setText(String.valueOf((int) progress.floatValue()));
        mCircularProgressBar.setProgressWithAnimation(progress);

        mNumberKm.setText(String.format("%.02f", newTotalKm));
        mNumberKmProgress.setText(String.format("%.02f", newTotalKm));
    }

    public void addSuccess(float progression, float newTotalKm, int pointsGranted) {

        mNumberKm.setText(String.format("%.02f", newTotalKm));
        mNumberKmProgress.setText(String.format("%.02f", newTotalKm));
        mNumberPointsValue += pointsGranted;
        mNumberPoints.setText(String.valueOf(mNumberPointsValue));
        updateProgression(100.0f, newTotalKm);
        try {
            Thread.sleep(1500);
            mCircularProgressBar.setProgress(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateProgression(progression, newTotalKm);
    }
}
