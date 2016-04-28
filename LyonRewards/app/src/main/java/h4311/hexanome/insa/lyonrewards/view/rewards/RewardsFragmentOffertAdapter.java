package h4311.hexanome.insa.lyonrewards.view.rewards;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.view.events.EventDetailActivity;

/**
 * Created by Pierre on 28/04/2016.
 */
public class RewardsFragmentOffertAdapter extends RecyclerView.Adapter<RewardsFragmentOffertAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setOffer(Offer offer) {

        }
    }

    protected List<Offer> offers;

    public RewardsFragmentOffertAdapter(List<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reward_offer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setOffer(offers.get(position));
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

}
