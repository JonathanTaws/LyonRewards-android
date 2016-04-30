package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
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

import butterknife.BindInt;
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
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
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

    @BindView(R.id.content)
    protected View mContentView;

    @BindView(R.id.loading_spinner)
    protected View mLoadingView;

    @BindInt(R.integer.anim_duration_short)
    protected int mShortAnimationDuration;

    @BindView(R.id.qrcode_found_first_view_container)
    protected LinearLayout mFirstViewContainer;

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

    private Scene mSceneClaimButtonClicked;

    private Event eventReceived;
    private QRCodeCitizenAct qrCodeReceived;

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

            // Initially hide the content view.
            mContentView.setVisibility(View.GONE);

            mSceneClaimButtonClicked = Scene.getSceneForLayout(mCardViewQrCodeContainer, R.layout.cardview_qrcode_success, getContext());

            // Event card view
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    Event event = lyonRewardsApi.getEventById(mQrCodeContent.getEventId(), connectionManager.getConnectedUser().getId());
                    if (event == null) {
                        return false;
                    }
                    eventReceived = event;
                    QRCodeCitizenAct qrCodeCitizenAct = lyonRewardsApi.getQrCodeById(mQrCodeContent.getActId(), connectionManager.getConnectedUser().getId());
                    if (qrCodeCitizenAct == null) {
                        return false;
                    }
                    qrCodeReceived = qrCodeCitizenAct;
                    return true;
                }

                @Override
                protected void onPostExecute(final Boolean success) {
                    if (success) {
                        crossfadeViews();
                    } else {
                        // todo handle error
                    }
                }
            }.execute();

            /*
            lyonRewardsApi.getEventById(mQrCodeContent.getEventId(), connectionManager.getConnectedUser().getId(), new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if (response.isSuccessful()) {
                        Event event = response.body();
                        EventCardView eventCardView = new EventCardView(getContext(), event);
                        mCardViewEventContainer.addView(eventCardView);
                        eventReceived = true;
                        crossfadeViews();
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
                        qrCodeReceived = true;
                        crossfadeViews();
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
            });*/

        }



        return view;
    }

    private void crossfadeViews() {
        if (eventReceived == null || qrCodeReceived == null) {
            return;
        }

        mCardViewEventContainer.addView(new EventCardView(getContext(), eventReceived));
        mCardViewQrCodeContainer.addView(new EventSuccessCardView(getContext(), qrCodeReceived));

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mContentView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        mLoadingView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingView.setVisibility(View.GONE);
                    }
                });
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

        TransitionManager.go(mSceneClaimButtonClicked, TransitionInflater.from(getContext()).inflateTransition(R.transition.slide_left));

        /*
        lyonRewardsApi.addActToUser(connectionManager.getConnectedUser(), mQrCodeContent.getActId(), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {

                    User user = response.body();
                    connectionManager.setConnectedUser(user);


//                    Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
  //                  intent.putExtra(EventDetailActivity.INTENT_EVENT, event);
    //                startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // TODO
            }
        });*/
    }
}
