package h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.business.act.TravelCitizenAct;
import h4311.hexanome.insa.lyonrewards.view.tracker.TravelCardView;

/**
 * Created by Jonathan on 03/05/2016.
 */
public class TravelCitizenActViewHolder extends RecyclerView.ViewHolder {

    private TravelCitizenAct travelCitizenAct;

    private TravelCardView travelCardView;

    public TravelCitizenActViewHolder(TravelCardView view) {
        super(view);
        this.travelCardView = view;
        ButterKnife.bind(this, view);
    }

    public void setTravelCitizenAct(TravelCitizenAct travelCitizenAct) {
        this.travelCitizenAct = travelCitizenAct;
        // TODO
        // travelCardView.set ?
    }
}
