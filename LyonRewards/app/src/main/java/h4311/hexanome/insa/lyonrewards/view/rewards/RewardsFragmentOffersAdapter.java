package h4311.hexanome.insa.lyonrewards.view.rewards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Offer;

public class RewardsFragmentOffersAdapter extends RecyclerView.Adapter<RewardsFragmentOffersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_reward_title)
        protected TextView mTitle;

        @BindView(R.id.card_reward_price)
        protected TextView mPrice;

        @BindView(R.id.card_reward_partner_img)
        protected ImageView mPartnerImage;

        @Inject
        protected ImageLoader mImageLoader;

        private Offer mOffer;
        private LyonRewardsApplication mApplication;

        public ViewHolder(View view, LyonRewardsApplication application) {
            super(view);
            ButterKnife.bind(this, view);
            mApplication = application;
            mApplication.getAppComponent().inject(this);
            mApplication = application;
        }

        public void setOffer(Offer offer) {
            mTitle.setText(offer.getTitle());
            mPrice.setText(offer.getPoints().toString());

            mImageLoader.loadImage(offer.getPartner().getImageUrl(), new SimpleImageLoadingListener() {
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    mPartnerImage.setImageBitmap(loadedImage);
                }
            });

            mOffer = offer;
        }

        @OnClick(R.id.card_reward_cardview)
        public void cardviewOnClick() {
            if (mOffer != null) {
                Intent intent = new Intent(mApplication, OfferDetailActivity.class);
                intent.putExtra(OfferDetailActivity.INTENT_OFFER, mOffer);
                mApplication.startActivity(intent);
            }
        }
    }

    protected List<Offer> offers;
    private LyonRewardsApplication mApplication;

    public RewardsFragmentOffersAdapter(List<Offer> offers, LyonRewardsApplication application) {
        this.offers = offers;
        mApplication = application;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reward_offer, parent, false);
        return new ViewHolder(view, mApplication);
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
