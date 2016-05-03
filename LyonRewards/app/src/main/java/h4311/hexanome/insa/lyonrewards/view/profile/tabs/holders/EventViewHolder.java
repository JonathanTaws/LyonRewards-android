package h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
 * Created by Jonathan on 03/05/2016.
 */
public class EventViewHolder extends RecyclerView.ViewHolder {

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
