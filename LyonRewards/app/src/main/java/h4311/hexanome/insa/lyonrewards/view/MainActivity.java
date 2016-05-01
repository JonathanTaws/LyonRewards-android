package h4311.hexanome.insa.lyonrewards.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.di.module.gcm.QuickstartPreferences;
import h4311.hexanome.insa.lyonrewards.di.module.gcm.RegistrationIntentService;
import h4311.hexanome.insa.lyonrewards.view.events.EventDetailFragment;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragment;
import h4311.hexanome.insa.lyonrewards.view.qrcode.OnQrCodeFoundListener;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrCodeContent;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrCodeFoundFragment;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrReaderFragment;
import h4311.hexanome.insa.lyonrewards.view.rankings.RankingsFragment;
import h4311.hexanome.insa.lyonrewards.view.rewards.OfferDetailFragment;
import h4311.hexanome.insa.lyonrewards.view.rewards.RewardsFragment;
import h4311.hexanome.insa.lyonrewards.view.tracker.TrackerFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EventsFragment.OnFragmentInteractionListener, OnQrCodeFoundListener, RewardsFragment.OnFragmentInteractionListener, ConnectionManager.ConnectedUserChangedListener {

    public static final String TRACKER_FRAGMENT = "TRACKER_FRAGMENT";
    public static final String EVENTS_FRAGMENT = "EVENTS_FRAGMENT";
    public static final String REWARDS_FRAGMENT = "REWARDS_FRAGMENT";
    public static final String REWARDS_DETAIL_FRAGMENT = "REWARDS_DETAIL_FRAGMENT";

    // Intent arg
    public static final String INTENT_USER_CONNECTED = "usertoken";


    @BindView(R.id.maintoolbar)
    protected Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager mConnectionManager;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    private BroadcastReceiver mGcmMessageBroadcastReceiver;
    private boolean isGcmMessageReceiverRegistered;

    protected Stack<HistoryFragment> historyFragments = new Stack<>();
    private ActionBarDrawerToggle toggle;

    public static final String ARG_ID_OFFER_READ = "idOfferRead";
    public static final String ARG_POINTS_OFFER_READ = "scoreOfferRead";

    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ((LyonRewardsApplication) getApplication()).getAppComponent().inject(this);

        mConnectionManager.addSubscriberAndNotify(this);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                Log.d("GCM", "sentToken : " + sentToken);
            }
        };

        mGcmMessageBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // // TODO: 30/04/2016
                Log.d("GCM", "Message received and notify !");
                try {
                    String idOfferRead = intent.getStringExtra(ARG_ID_OFFER_READ);
                    int idOffer = Integer.parseInt(idOfferRead);

                    // Update user
                    int nbPoints = Integer.parseInt(intent.getStringExtra(ARG_POINTS_OFFER_READ));
                    mConnectionManager.debitCredit(nbPoints);

                    HistoryFragment lastHistory = historyFragments.peek();

                    if (currentFragment instanceof OfferDetailFragment) {
                        OfferDetailFragment offerDetailFragment = (OfferDetailFragment) currentFragment;
                        if (offerDetailFragment.hasSameOffer(idOffer)) {
                            offerDetailFragment.setOfferAsPaid();
                        } else {
                            displayPaidOfferFragment(idOffer);
                        }
                    } else {
                        displayPaidOfferFragment(idOffer);
                    }
                } catch (NullPointerException | NumberFormatException e) {
                    // Ignore message
                    Log.d("GCM", "Message received with wrong format.");
                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        // todo check play services ?
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

        navigationView.setNavigationItemSelectedListener(this);

        String extraOfferIdString = getIntent().getStringExtra(ARG_ID_OFFER_READ);
        if (extraOfferIdString != null) {
            displayPaidOfferFragment(Integer.parseInt(extraOfferIdString));
        } else {
            // Default fragment
            Fragment initialFragment = new EventsFragment();
            setFragment(initialFragment, EventsFragment.getFragmentTag(), EventsFragment.getFragmentTitle(), false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mGcmMessageBroadcastReceiver);
        isReceiverRegistered = false;
        isGcmMessageReceiverRegistered = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConnectionManager.removeSubscriber(this);
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
        if (!isGcmMessageReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mGcmMessageBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.GCM_MESSAGE_RECEIVED));
            isGcmMessageReceiverRegistered = true;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (historyFragments.size() > 1) {
                historyFragments.pop();
                Fragment oldFragment = null;
                HistoryFragment previous = historyFragments.peek();
                String previousTag = previous.getTag();
                if (previousTag.equals(EventsFragment.getFragmentTag())) {
                    oldFragment = new EventsFragment();
                } else if (previousTag.equals(REWARDS_FRAGMENT)) {
                    oldFragment = RewardsFragment.newInstance();
                } else if (previousTag.equals(OfferDetailFragment.getFragmentTag())) {
                    oldFragment = OfferDetailFragment.newInstance((Offer) previous.getArgs().get(0));
                } else if (previousTag.equals(EventDetailFragment.getFragmentTag())) {
                    oldFragment = EventDetailFragment.newInstance((Event) previous.getArgs().get(0));
                } else if (previousTag.equals(QrReaderFragment.getFragmentTag())) {
                    oldFragment = QrReaderFragment.newInstance(new Bundle());
                } else if (previousTag.equals(QrCodeFoundFragment.getFragmentTag())) {
                    oldFragment = QrCodeFoundFragment.newInstance((QrCodeContent) previous.getArgs().get(0));
                }
                if (oldFragment != null) {
                    setFragment(oldFragment, previous.getTag(), previous.getTitle(), previous.isPreviousIcon(), previous.getArgs(), false);
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    private void displayPaidOfferFragment(int idOffer) {

        lyonRewardsApi.getOfferById(idOffer, new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                if (response.isSuccessful()) {
                    Offer offer = response.body();
                    Fragment fragment = OfferDetailFragment.newInstance(offer, true);
                    String fragmentTitle = "Offre " + offer.getPartner().getName();
                    List<Object> args = new ArrayList<>();
                    args.add(offer);
                    setFragment(fragment, OfferDetailFragment.getFragmentTag(), fragmentTitle, true, args, true);
                } else {
                    // todo handle error
                }
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                // todo handle error
            }
        });

        //oldFragment = OfferDetailFragment.newInstance((Offer) previous.getArgs().get(0));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            case android.R.id.home:
                if (historyFragments.peek().isPreviousIcon()) {
                    onBackPressed();
                    return true;
                }
                break;

            case R.id.action_scan_qrcode:
                drawer.closeDrawer(GravityCompat.START);
                showScanQrCodeFragment();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showScanQrCodeFragment() {
        // If fragment is already running, don't do anything
        QrReaderFragment qrReaderFragment = (QrReaderFragment) getSupportFragmentManager().findFragmentByTag(QrReaderFragment.getFragmentTag());
        if (qrReaderFragment != null && qrReaderFragment.isVisible()) {
            return;
        }

        QrReaderFragment fragment = QrReaderFragment.newInstance(new Bundle());
        setFragment(fragment, QrReaderFragment.getFragmentTag(), "Scanner un QR code", false);
    }

    @Override
    public void onReplaceFragment(String newFragmentTag, String newFragmentName, Fragment newFragment) {
        setFragment(newFragment, newFragmentTag, newFragmentName, false);
    }

    public void setFragment(Fragment fragment, String tag, String title, boolean previousIcon, List<Object> args) {
        setFragment(fragment, tag, title, previousIcon, args, true);
    }

    public void setFragment(Fragment fragment, String tag, String title, boolean previousIcon, List<Object> args, boolean history) {
        if (history) {
            HistoryFragment historyFragment = new HistoryFragment(tag, title, previousIcon, args);
            historyFragments.push(historyFragment);
        }

        if (previousIcon) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.syncState();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_content_frame, fragment, tag)
                .commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        currentFragment = fragment;
    }

    public void setFragment(Fragment fragment, String tag, String title, boolean previousIcon) {
        setFragment(fragment, tag, title, previousIcon, null, true);
    }

    public void setFragment(Fragment fragment, String tag, String title, boolean previousIcon, boolean history) {
        setFragment(fragment, tag, title, previousIcon, null, history);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String fragmentName = null;
        String fragmentTitle = "todo";

        Bundle bundle = new Bundle();

        if (id == R.id.nav_scan_qrcode) {
            //If fragment is already running, don't do anything
            QrReaderFragment qrReaderFragment = (QrReaderFragment) getSupportFragmentManager().findFragmentByTag(QrReaderFragment.getFragmentTag());
            if (qrReaderFragment != null && qrReaderFragment.isVisible()) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

            fragment = QrReaderFragment.newInstance(bundle);
            fragmentName = QrReaderFragment.getFragmentTag();
        } else if (id == R.id.nav_tracker) {
            fragment = TrackerFragment.newInstance();
            fragmentName = MainActivity.TRACKER_FRAGMENT;
            fragmentTitle = "Mes déplacements";
        }
        else if (id == R.id.nav_events) {
            fragment = EventsFragment.newInstance(bundle);
            fragmentName = EventsFragment.getFragmentTag();
            fragmentTitle = EventsFragment.getFragmentTitle();
        } else if (id == R.id.nav_rewards) {
            fragment = RewardsFragment.newInstance();
            fragmentName = MainActivity.REWARDS_FRAGMENT;
            fragmentTitle = "Boutique";
        }
        else if(id == R.id.nav_rankings) {
            fragment = RankingsFragment.newInstance(bundle);
            fragmentName = RankingsFragment.getFragmentTag();
            fragmentTitle = RankingsFragment.getFragmentTitle();
        }
        else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_activity_content_frame);
            fragment = currentFragment;
        }

        drawer.closeDrawer(GravityCompat.START);

        setFragment(fragment, fragmentName, fragmentTitle, false);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO : Action ?

    }

    @Override
    public void onQrCodeFound(QrCodeContent qrCodeContent) {
        Fragment fragment = QrCodeFoundFragment.newInstance(qrCodeContent);
        List<Object> args = new ArrayList<>();
        args.add(qrCodeContent);
        setFragment(fragment, QrCodeFoundFragment.getFragmentTag(), QrCodeFoundFragment.getFragmentTitle(), true, args, true);
    }

    @Override
    public void updateConnectedUser(User user) {
        // Update nav header
        View hView = navigationView.getHeaderView(0);
        TextView drawerUserTitle = (TextView) hView.findViewById(R.id.nav_header_user_title);
        drawerUserTitle.setText(user.getFirstName() + " " + user.getLastName());
        TextView drawerUserEmail = (TextView) hView.findViewById(R.id.nav_header_user_email);
        drawerUserEmail.setText(user.getEmail());
        TextView drawerUserPoints = (TextView) hView.findViewById(R.id.nav_header_user_nb_points);
        drawerUserPoints.setText(String.valueOf(user.getCurrentPoints()));
    }

}
