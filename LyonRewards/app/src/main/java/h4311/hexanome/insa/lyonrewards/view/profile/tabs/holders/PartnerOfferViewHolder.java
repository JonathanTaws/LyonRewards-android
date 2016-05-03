package h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.view.rewards.OfferCardView;

/**
 * Created by Jonathan on 03/05/2016.
 */
public class PartnerOfferViewHolder extends RecyclerView.ViewHolder {

    private Offer partnerOffer;

    private OfferCardView offerCardView;

    public PartnerOfferViewHolder(OfferCardView view) {
        super(view);
        this.offerCardView = view;
        ButterKnife.bind(this, view);
    }

    public void setPartnerOffer(Offer partnerOffer) {
        this.partnerOffer = partnerOffer;
        // TODO
        // offerCardView.set ?
    }
}
