package h4311.hexanome.insa.lyonrewards.view.rewards;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.glxn.qrgen.android.QRCode;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Offer;

/**
 * Created by Pierre on 28/04/2016.
 */
public class OfferDetailFragment extends Fragment {

    public static final String INTENT_OFFER = "arg_offer";
    public static final String INTENT_IS_PAID = "arg_is_paid";

    //@Inject
//    protected LyonRewardsApi lyonRewardsApi;

    @BindView(R.id.card_offer_used)
    protected View mCardOfferIsUsed;

    @BindView(R.id.card_offer_used_text)
    protected TextView mCardOfferIsUsedText;

    @BindView(R.id.offer_detail_partner_name)
    protected TextView mPartnerName;

    @BindView(R.id.offer_detail_partner_description)
    protected TextView mPartnerDescription;

    @BindView(R.id.offer_detail_partner_address)
    protected TextView mPartnerAddress;

    @BindView(R.id.offer_detail_partner_image)
    protected ImageView mPartnerImage;

    @BindView(R.id.offer_detail_title)
    protected TextView mOfferTitle;

    @BindView(R.id.offer_detail_nbPoints_desc)
    protected TextView mOfferNbPoints;

    @BindView(R.id.offer_detail_description)
    protected TextView mOfferDescription;

    @BindView(R.id.offer_detail_pay_button)
    protected Button mPayButton;

    @BindView(R.id.offer_detail_qrcode_image)
    protected ImageView mQrCodeImage;

    @BindView(R.id.offer_detail_qrcode_layout)
    protected LinearLayout mQrCodeLayout;

    @BindDimen(R.dimen.partner_qrcode_size)
    protected int mQrCodeSize;

    @Inject
    ImageLoader mImageLoader;

    private Offer mOffer;
    private boolean isPaid;

    public OfferDetailFragment() {
        // Required empty public constructor
    }

    public static OfferDetailFragment newInstance(Offer offer) {
        return newInstance(offer, false);
    }

    public static OfferDetailFragment newInstance(Offer offer, boolean isPaid) {
        OfferDetailFragment fragment = new OfferDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_OFFER, offer);
        args.putBoolean(INTENT_IS_PAID, isPaid);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getFragmentTag() {
        return "QR_SCANNER_FRAGMENT";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_detail, container, false);
        ButterKnife.bind(this, view);
        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        if (getArguments() != null) {
            mOffer = getArguments().getParcelable(INTENT_OFFER);
            isPaid = getArguments().getBoolean(INTENT_IS_PAID);

            if (isPaid) {
                mCardOfferIsUsedText.setText("Cette offre vient d'être utilisée. Vous avez été débités de " + mOffer.getPoints() + " points.");
                mCardOfferIsUsed.setVisibility(View.VISIBLE);
            }

            mPartnerName.setText(mOffer.getPartner().getName());
            mPartnerDescription.setText(mOffer.getPartner().getDescription());
            mPartnerAddress.setText(mOffer.getPartner().getAddress());
            mImageLoader.displayImage(mOffer.getPartner().getImageUrl(), mPartnerImage);

            mOfferTitle.setText(mOffer.getTitle());
            mOfferDescription.setText(mOffer.getDescription());
            mOfferNbPoints.setText(mOffer.getPoints().toString());
            mPayButton.setText("Obtenir pour " + mOffer.getPoints() + " points");
        }

        return view;
    }

    @OnClick(R.id.offer_detail_pay_button)
    public void payButtonOnClick() {
        if (mOffer != null) {
            Bitmap myBitmap = QRCode.from("www.example.org").withSize(mQrCodeSize, mQrCodeSize).bitmap();
            mQrCodeImage.setImageBitmap(myBitmap);
            mCardOfferIsUsed.setVisibility(View.GONE);
            mPayButton.setVisibility(View.GONE);
            mQrCodeLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public boolean hasSameOffer(int idOffer) {
        return (mOffer != null && mOffer.getId() == idOffer);
    }

    public void setOfferAsPaid() {
        mCardOfferIsUsedText.setText("Cette offre vient d'être utilisée. Vous avez été débités de " + mOffer.getPoints() + " points.");
        mCardOfferIsUsed.setVisibility(View.VISIBLE);
        mPayButton.setVisibility(View.VISIBLE);
        mQrCodeLayout.setVisibility(View.GONE);
    }


}
