package h4311.hexanome.insa.lyonrewards.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
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

import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragment;
import h4311.hexanome.insa.lyonrewards.view.qrcode.OnQrCodeFoundListener;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrCodeContent;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrCodeFoundActivity;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrReaderFragment;
import h4311.hexanome.insa.lyonrewards.view.rewards.RewardsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EventsFragment.OnFragmentInteractionListener, OnQrCodeFoundListener, RewardsFragment.OnFragmentInteractionListener {

    public static final String QR_SCANNER_FRAGMENT = "QR_SCANNER_FRAGMENT";
    public static final String EVENTS_FRAGMENT = "EVENTS_FRAGMENT";
    public static final String REWARDS_FRAGMENT = "REWARDS_FRAGMENT";
    public static final String REWARDS_DETAIL_FRAGMENT = "REWARDS_DETAIL_FRAGMENT";

    // Intent arg
    public static final String INTENT_USER_CONNECTED = "usertoken";

    @BindView(R.id.maintoolbar)
    protected Toolbar toolbar;

    @BindView(R.id.fab)
    protected FloatingActionButton fab;

    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager mConnectionManager;

    protected Stack<HistoryFragment> historyFragments = new Stack<>();
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ((LyonRewardsApplication) getApplication()).getAppComponent().inject(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Update nav header
        View hView =  navigationView.getHeaderView(0);
        TextView drawerUserTitle = (TextView) hView.findViewById(R.id.nav_header_user_title);
        drawerUserTitle.setText(mConnectionManager.getConnectedUser().getFirstName() + " " + mConnectionManager.getConnectedUser().getLastName());
        TextView drawerUserEmail = (TextView) hView.findViewById(R.id.nav_header_user_email);
        drawerUserEmail.setText(mConnectionManager.getConnectedUser().getEmail());

        toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);

        // Default fragment
        Fragment initialFragment = new EventsFragment();
        setFragment(initialFragment, EventsFragment.getFragmentTag(), EventsFragment.getFragmentTitle(), false);
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
                }
                if (oldFragment != null) {
                    setFragment(oldFragment, previous.getTag(), previous.getTitle(), previous.isPreviousIcon(), previous.getArgs(), false);
                }
            } else {
                super.onBackPressed();
            }
        }
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
        }

        return super.onOptionsItemSelected(item);
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
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.setDrawerIndicatorEnabled(false);
        } else {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_content_frame, fragment, tag)
                .commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
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

        if(id == R.id.nav_scan_qrcode) {
            //If fragment is already running, don't do anything
            QrReaderFragment qrReaderFragment = (QrReaderFragment)getSupportFragmentManager().findFragmentByTag(MainActivity.QR_SCANNER_FRAGMENT);
            if (qrReaderFragment != null && qrReaderFragment.isVisible()) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

            fragment = QrReaderFragment.newInstance(bundle);
            fragmentName = MainActivity.QR_SCANNER_FRAGMENT;
        }
        else if(id == R.id.nav_events) {
            fragment = EventsFragment.newInstance(bundle);
            fragmentName = EventsFragment.getFragmentTag();
            fragmentTitle = EventsFragment.getFragmentTitle();
        }
        else if (id == R.id.nav_rewards) {
            fragment = RewardsFragment.newInstance();
            fragmentName = MainActivity.REWARDS_FRAGMENT;
            fragmentTitle = "Boutique";
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
        Intent intent = QrCodeFoundActivity.newIntent(this, qrCodeContent);

        startActivity(intent);
    }

}
