package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.events.EventCardView;
import h4311.hexanome.insa.lyonrewards.view.events.EventSuccessCardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jonathan on 28/04/2016.
 */
public class QrCodeFoundFragment extends Fragment {

    private static final String ARG_QR_CODE_VALUE = "qrCodeContent";

    @BindView(R.id.button_reclaim_points)
    protected Button buttonReclaimPoints;

    @BindView(R.id.cardview_event_container)
    protected LinearLayout mCardViewEventContainer;

    @BindView(R.id.cardview_qrcode_success_container)
    protected LinearLayout mCardViewQrCodeContainer;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager connectionManager;

    private QrCodeContent mQrCodeContent;

    public QrCodeFoundFragment() {
        // Required empty public constructor
    }

    public static QrCodeFoundFragment newInstance(QrCodeContent qrCodeContent) {
        QrCodeFoundFragment fragment = new QrCodeFoundFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QR_CODE_VALUE, qrCodeContent);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getFragmentTag() {
        return "QrCodeFoundFragment";
    }

    public static String getFragmentTitle() {
        return "QR Code détecté";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_qr_code_found, container, false);
        ButterKnife.bind(this, view);
        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        if (getArguments() != null) {
            mQrCodeContent = getArguments().getParcelable(ARG_QR_CODE_VALUE);

            // Event card view

            lyonRewardsApi.getEventById(mQrCodeContent.getEventId(), connectionManager.getConnectedUser().getId(), new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if (response.isSuccessful()) {
                        Event event = response.body();
                        EventCardView eventCardView = new EventCardView(getContext(), event);
                        mCardViewEventContainer.addView(eventCardView);
                    } else {
                        // Todo error
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    // TODO Handle error
                }
            });

            lyonRewardsApi.getQrCodeById(mQrCodeContent.getActId(), new Callback<QRCodeCitizenAct>() {
                @Override
                public void onResponse(Call<QRCodeCitizenAct> call, Response<QRCodeCitizenAct> response) {
                    if (response.isSuccessful()) {
                        QRCodeCitizenAct qrCodeCitizenAct = response.body();
                        mCardViewQrCodeContainer.addView(new EventSuccessCardView(getContext(), qrCodeCitizenAct));
                    }
                    else {
                        // TODO Handle error
                        Log.d("API", "getQrCodeById onResponse error");
                    }
                }

                @Override
                public void onFailure(Call<QRCodeCitizenAct> call, Throwable t) {
                    // TODO Handle error
                    Log.d("API", "getQrCodeById " + t.getMessage());
                }
            });

        }



        return view;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_found);
        ButterKnife.bind(this);

        ((LyonRewardsApplication) getApplication()).getAppComponent().inject(this);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonReclaimPoints.setOnClickListener(this);

        mEvents = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        qrCodeFoundEvent.setLayoutManager(layoutManager);

        /*
        mAdapter = new EventsFragmentGrandLyonTabViewAdapter(mEvents, this);
        qrCodeFoundEvent.setAdapter(mAdapter);

        QrCodeContent qrCodeContent = getIntent().getExtras().getParcelable(ARG_QR_CODE_VALUE);

        qrCodeValueTextView.setText(qrCodeContent.getValue());

        lyonRewardsApi.getEventById(qrCodeContent.getEventId(), connectionManager.getConnectedUser().getId(), new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                // TODO Check if necessary
                if(response.code() == 200) {
                    Event event = response.body();
                    mEvents.add(event);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                // TODO Handle error
            }
        });

    }*/

    @OnClick(R.id.button_reclaim_points)
    public void buttonReclaimPointsOnClick() {

        lyonRewardsApi.addActToUser(connectionManager.getConnectedUser(), mQrCodeContent.getActId(), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {

                    User user = response.body();
                    connectionManager.setConnectedUser(user);

/*
                    Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
                    intent.putExtra(EventDetailActivity.INTENT_EVENT, event);
                    startActivity(intent);*/
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // TODO
            }
        });
    }
}
