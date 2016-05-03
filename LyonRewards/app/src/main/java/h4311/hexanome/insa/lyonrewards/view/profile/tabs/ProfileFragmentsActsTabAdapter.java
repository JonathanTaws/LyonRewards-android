package h4311.hexanome.insa.lyonrewards.view.profile.tabs;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventSuccessCardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        @BindView(R.id.event_success_container)
        protected LinearLayout mSuccessContainer;

        @Inject
        protected LyonRewardsApi lyonRewardsApi;

        @Inject
        protected ConnectionManager connectionManager;

        private View view;

        protected MainActivity mainActivity;

        public EventViewHolder(View view, MainActivity activity) {
            super(view);
            ButterKnife.bind(this, view);
            ((LyonRewardsApplication) activity.getApplication()).getAppComponent().inject(this);
            this.mainActivity = activity;
        }

        public void setEvent(Event event) {
            this.event = event;
            eventTitle.setText(event.getTitle());

            // Get the success
            lyonRewardsApi.getQrCodesFromEvent(event.getId(), connectionManager.getConnectedUser().getId(), new Callback<List<QRCodeCitizenAct>>() {
                @Override
                public void onResponse(Call<List<QRCodeCitizenAct>> call, Response<List<QRCodeCitizenAct>> response) {
                    if (response.isSuccessful()) {
                        for (QRCodeCitizenAct qrCodeCitizenAct : response.body()) {
                            if (qrCodeCitizenAct.isCompleted()) {
                                mSuccessContainer.addView(new EventSuccessCardView(mainActivity, qrCodeCitizenAct));
                            }
                        }
                    } else {
                        // todo : handle error
                        Log.d("API", "Error : " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<QRCodeCitizenAct>> call, Throwable t) {
                    // todo : handle error
                    Log.d("API", "Error : " + t.getMessage());
                }
            });
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
                        .inflate(R.layout.profile_act_event_layout, parent, false);
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
