package h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.act.TravelCitizenAct;
import h4311.hexanome.insa.lyonrewards.view.tracker.TravelCardView;

/**
 * Created by Jonathan on 03/05/2016.
 */
public class TravelCitizenActViewHolder extends RecyclerView.ViewHolder {

    private TravelCitizenAct travelCitizenAct;

    @BindView(R.id.card_travel_type)
    protected TextView mType;

    @BindView(R.id.card_travel_number_points)
    protected TextView mNumberPoints;

    @BindView(R.id.card_date)
    protected TextView mDate;

    public TravelCitizenActViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setTravelCitizenAct(TravelCitizenAct travelCitizenAct) {
        this.travelCitizenAct = travelCitizenAct;

        mType.setText(travelCitizenAct.getTitle());
        mNumberPoints.setText(String.valueOf(travelCitizenAct.getPoints()));
        mDate.setText(travelCitizenAct.getCompletedDateString());
    }
}
