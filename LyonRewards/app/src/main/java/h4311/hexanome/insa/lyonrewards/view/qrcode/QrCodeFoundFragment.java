package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindColor;
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
    protected View mContentViewQrCodeAvailable;

    @BindView(R.id.wrong_qrcode_container)
    protected View mContentViewQrCodeUnavailable;

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

    @BindView(R.id.qrcode_found_title)
    protected TextView mFoundTitle;

    @BindView(R.id.button_reclaim_points_container)
    protected View mButtonReclaimPointsContainer;

    @BindView(R.id.card_points_granted_container)
    protected View mCardPointsGrantedContainer;

    @BindView(R.id.card_points_granted_text)
    protected TextView mCardPointsGrantedText;

    @BindView(R.id.button_reclaim_points_progress)
    protected ProgressBar mButtonReclaimPointsProgress;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager connectionManager;

    @BindColor(R.color.colorPrimary)
    protected int mPrimaryColor;

    private QrCodeContent mQrCodeContent;

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
        return "QR code détecté";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_qr_code_found, container, false);
        ButterKnife.bind(this, view);
        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        mButtonReclaimPointsProgress.getIndeterminateDrawable().setColorFilter(mPrimaryColor, android.graphics.PorterDuff.Mode.SRC_IN);
        mButtonReclaimPointsProgress.setProgressTintList(ColorStateList.valueOf(mPrimaryColor));

        if (getArguments() != null) {
            mQrCodeContent = getArguments().getParcelable(ARG_QR_CODE_VALUE);

            // Initially hide the content view.
            mContentViewQrCodeAvailable.setVisibility(View.GONE);


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
                        showQrCodeFoundView(true);
                    } else {
                        showQrCodeFoundView(false);
                    }
                }
            }.execute();
        }

        return view;
    }

    private void showQrCodeFoundView(boolean qrCodeFound) {
        View contentView;

        if (qrCodeFound) {
            // Check if the code is new for the user
            if (qrCodeReceived.isCompleted()) {
                mFoundTitle.setText(R.string.scanner_qrcode_already_found);
                mButtonReclaimPointsContainer.setVisibility(View.GONE);
            } else {
                mFoundTitle.setText(R.string.scanner_qrcode_found);
            }

            mCardViewEventContainer.addView(new EventCardView((MainActivity) getActivity(), eventReceived));
            mCardViewQrCodeContainer.addView(new EventSuccessCardView(getContext(), qrCodeReceived));

            contentView = mContentViewQrCodeAvailable;
        } else {
            contentView = mContentViewQrCodeUnavailable;
        }

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        contentView.setAlpha(0f);
        contentView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        contentView.animate()
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

    @OnClick(R.id.button_reclaim_points)
    public void buttonReclaimPointsOnClick(View button) {

        button.setVisibility(View.GONE);
        mButtonReclaimPointsProgress.setVisibility(View.VISIBLE);

        lyonRewardsApi.addActToUser(connectionManager.getConnectedUser(), mQrCodeContent.getActId(), new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    // Update the user
                    User user = response.body();
                    connectionManager.setConnectedUser(user);

                    // Get the new event, updated
                    lyonRewardsApi.getEventById(mQrCodeContent.getEventId(), connectionManager.getConnectedUser().getId(), new Callback<Event>() {
                        @Override
                        public void onResponse(Call<Event> call, Response<Event> response) {
                            if (response.isSuccessful()) {
                                eventReceived = response.body();

                                mCardViewEventContainer.removeAllViews();
                                mCardViewEventContainer.addView(new EventCardView((MainActivity) getActivity(), eventReceived));

                                // Update the current fragment
                                qrCodeReceived.setCompleted(true);
                                qrCodeReceived.setCompletedDate(new Date());

                                View viewHierarchy = new EventSuccessCardView(getContext(), qrCodeReceived);
                                Scene mSceneClaimButtonClicked = new Scene(mCardViewQrCodeContainer, viewHierarchy);
                                TransitionManager.go(mSceneClaimButtonClicked, TransitionInflater.from(getContext()).inflateTransition(R.transition.slide_left));

                                mCardPointsGrantedText.setText("Félicitations, vous venez de gagner " + qrCodeReceived.getPoints() + " points.");
                                mButtonReclaimPointsContainer.setVisibility(View.GONE);
                                mCardPointsGrantedContainer.setVisibility(View.VISIBLE);
                            } else {
                                // todo handle error
                                Log.d("API", "Error : " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Event> call, Throwable t) {
                            // todo handle error
                            Log.d("API", "Error : " + t.getMessage());
                        }
                    });
                } else {
                    // todo handle error
                    Log.d("API", "Error : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // TODO
            }
        });

    }
}
