package h4311.hexanome.insa.lyonrewards.view.rewards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Offer;

public class RewardsFragmentOffersAdapter extends RecyclerView.Adapter<RewardsFragmentOffersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_reward_title)
        protected TextView mTitle;

        @BindView(R.id.card_reward_price)
        protected TextView mPrice;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setOffer(Offer offer) {
            mTitle.setText(offer.getTitle());
            mPrice.setText(offer.getPoints());
        }
    }

    protected List<Offer> offers;

    public RewardsFragmentOffersAdapter(List<Offer> offers) {
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
