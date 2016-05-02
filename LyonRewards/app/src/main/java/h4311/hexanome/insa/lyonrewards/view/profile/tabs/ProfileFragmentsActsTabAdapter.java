package h4311.hexanome.insa.lyonrewards.view.profile.tabs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventSuccessCardView;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfileFragmentsActsTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_QR_CODE_CITIZEN_ACT = 0;
    private static final int TYPE_EVENT = 1;
    private static final int TYPE_UNKNOWN = 2;

    public static class QrCodeCitizenActViewHolder extends RecyclerView.ViewHolder {

        private QRCodeCitizenAct qrCodeCitizenAct;

        private EventSuccessCardView eventSuccessCardView;

        protected MainActivity mainActivity;

        public QrCodeCitizenActViewHolder(EventSuccessCardView eventSuccessCardView, MainActivity activity) {
            super(eventSuccessCardView);
            this.eventSuccessCardView = eventSuccessCardView;
            ButterKnife.bind(this, eventSuccessCardView);
            this.mainActivity = activity;
        }

        public void setQrCodeCitizenAct(QRCodeCitizenAct qrCodeCitizenAct) {
            this.qrCodeCitizenAct = qrCodeCitizenAct;
            eventSuccessCardView.setQrCodeCitizenAct(qrCodeCitizenAct);
        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        private Event event;

        @BindView(R.id.event_title)
        protected TextView eventTitle;

        private View view;

        protected MainActivity mainActivity;

        public EventViewHolder(View view, MainActivity activity) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            this.mainActivity = activity;
        }

        public void setEvent(Event event) {
            this.event = event;
        }

    }

    protected List<Object> objects;

    protected MainActivity mActivity;

    public ProfileFragmentsActsTabAdapter(List<Object> objects, MainActivity mainActivity) {
        this.objects = objects;
        this.mActivity = mainActivity;
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = objects.get(position);
        if(obj instanceof QRCodeCitizenAct) {
            return TYPE_QR_CODE_CITIZEN_ACT;
        }
        else if(obj instanceof Event) {
            return TYPE_EVENT;
        }
        return TYPE_UNKNOWN;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_EVENT: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new EventViewHolder(view, mActivity);
            }
            case TYPE_QR_CODE_CITIZEN_ACT: {
                EventSuccessCardView eventSuccessCardView = new EventSuccessCardView(parent.getContext());
                return new QrCodeCitizenActViewHolder(eventSuccessCardView, mActivity);
            }
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_EVENT:
                ((EventViewHolder)holder).setEvent((Event) objects.get(position));
                break;
            case TYPE_QR_CODE_CITIZEN_ACT:
                ((QrCodeCitizenActViewHolder)holder).setQrCodeCitizenAct((QRCodeCitizenAct) objects.get(position));
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
