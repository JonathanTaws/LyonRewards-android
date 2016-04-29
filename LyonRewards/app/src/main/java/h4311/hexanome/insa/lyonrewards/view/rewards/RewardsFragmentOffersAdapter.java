package h4311.hexanome.insa.lyonrewards.view.rewards;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;

public class RewardsFragmentOffersAdapter extends RecyclerView.Adapter<RewardsFragmentOffersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_reward_title)
        protected TextView mTitle;

        @BindView(R.id.card_reward_price)
        protected TextView mPrice;

        @BindView(R.id.card_reward_partner_img)
        protected ImageView mPartnerImage;

        private Offer mOffer;
        private MainActivity mActivity;

        @Inject
        ImageLoader mImageLoader;

        private RewardsFragment.OnFragmentInteractionListener mListener;

        public ViewHolder(View view, MainActivity activity, RewardsFragment.OnFragmentInteractionListener listener) {
            super(view);
            mListener = listener;
            ButterKnife.bind(this, view);
            mActivity = activity;

            ((LyonRewardsApplication) activity.getApplication()).getAppComponent().inject(this);
        }

        public void setOffer(Offer offer) {
            mTitle.setText(offer.getTitle());
            mPrice.setText(offer.getPoints().toString());

            mImageLoader.displayImage(offer.getPartner().getImageUrl(), mPartnerImage);

            mOffer = offer;
        }

        @OnClick(R.id.card_reward_cardview)
        public void cardviewOnClick() {
            if (mOffer != null) {

                OfferDetailFragment fragment = OfferDetailFragment.newInstance(mOffer);

               mListener.onReplaceFragment(MainActivity.REWARDS_DETAIL_FRAGMENT, "Offre " + mOffer.getPartner().getName(), fragment);
            }
        }
    }

    protected List<Offer> offers;
    private MainActivity mActivity;
    private RewardsFragment.OnFragmentInteractionListener mListener;

    public RewardsFragmentOffersAdapter(List<Offer> offers, MainActivity activity) {
        this.offers = offers;
        this.mActivity = activity;
        mListener = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reward_offer, parent, false);
        return new ViewHolder(view, mActivity, mListener);
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
