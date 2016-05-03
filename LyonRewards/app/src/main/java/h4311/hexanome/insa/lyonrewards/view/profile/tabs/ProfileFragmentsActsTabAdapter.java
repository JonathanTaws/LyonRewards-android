package h4311.hexanome.insa.lyonrewards.view.profile.tabs;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import h4311.hexanome.insa.lyonrewards.business.act.TravelCitizenAct;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventSuccessCardView;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders.EventViewHolder;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders.PartnerOfferViewHolder;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders.QrCodeCitizenActViewHolder;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders.TravelCitizenActViewHolder;
import h4311.hexanome.insa.lyonrewards.view.rewards.OfferCardView;
import h4311.hexanome.insa.lyonrewards.view.tracker.TravelCardView;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfileFragmentsActsTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_QR_CODE_CITIZEN_ACT = 0;
    private static final int TYPE_EVENT = 1;
    private static final int TYPE_TRAVEL_CITIZEN_ACT = 2;
    private static final int TYPE_PARTNER_OFFER = 3;
    private static final int TYPE_UNKNOWN = 4;

    protected List<Object> objects;

    protected MainActivity mActivity;

    @Inject
    protected ConnectionManager connectionManager;

    public ProfileFragmentsActsTabAdapter(List<Object> objects, MainActivity mainActivity) {
        this.objects = objects;
        this.mActivity = mainActivity;
        ((LyonRewardsApplication) mActivity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = objects.get(position);
        if(obj instanceof TravelCitizenAct) {
            return TYPE_TRAVEL_CITIZEN_ACT;
        }
        else if(obj instanceof QRCodeCitizenAct) {
            return TYPE_QR_CODE_CITIZEN_ACT;
        }
        else if(obj instanceof Event) {
            return TYPE_EVENT;
        }
        else if(obj instanceof Offer) {
            return TYPE_PARTNER_OFFER;
        }
        return TYPE_UNKNOWN;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TRAVEL_CITIZEN_ACT: {
                TravelCardView travelCardView = new TravelCardView(mActivity, connectionManager.getConnectedUser());

                return new TravelCitizenActViewHolder(travelCardView);
            }
            case TYPE_QR_CODE_CITIZEN_ACT: {
                EventSuccessCardView eventSuccessCardView = new EventSuccessCardView(parent.getContext());

                return new QrCodeCitizenActViewHolder(eventSuccessCardView, mActivity);
            }
            case TYPE_EVENT: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_event, parent, false);

                return new EventViewHolder(view, mActivity);
            }
            case TYPE_PARTNER_OFFER: {
                OfferCardView offerCardView = new OfferCardView(parent.getContext());

                return new PartnerOfferViewHolder(offerCardView);
            }
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TRAVEL_CITIZEN_ACT:
                ((TravelCitizenActViewHolder)holder).setTravelCitizenAct((TravelCitizenAct) objects.get(position));
                break;
            case TYPE_QR_CODE_CITIZEN_ACT:
                ((QrCodeCitizenActViewHolder)holder).setQrCodeCitizenAct((QRCodeCitizenAct) objects.get(position));
                break;
            case TYPE_EVENT:
                ((EventViewHolder)holder).setEvent((Event) objects.get(position));
                break;
            case TYPE_PARTNER_OFFER:
                ((PartnerOfferViewHolder)holder).setPartnerOffer((Offer) objects.get(position));
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
