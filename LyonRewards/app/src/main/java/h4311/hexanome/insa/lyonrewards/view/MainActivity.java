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

import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragment;
import h4311.hexanome.insa.lyonrewards.view.qrcode.OnQrCodeFoundListener;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrCodeFoundActivity;
import h4311.hexanome.insa.lyonrewards.view.qrcode.QrReaderFragment;
import h4311.hexanome.insa.lyonrewards.view.rewards.RewardsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EventsFragment.OnFragmentInteractionListener, OnQrCodeFoundListener, RewardsFragment.OnFragmentInteractionListener {

    public static final String QR_SCANNER_FRAGMENT = "QR_SCANNER_FRAGMENT";
    public static final String EVENTS_FRAGMENT = "EVENTS_FRAGMENT";
    public static final String REWARDS_FRAGMENT = "REWARDS_FRAGMENT";
    public static final String REWARDS_DETAIL_FRAGMENT = "REWARDS_DETAIL_FRAGMENT";

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

    protected Stack<HistoryFragment> historyFragments = new Stack<>();
    protected String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((LyonRewardsApplication) getApplication()).getAppComponent().inject(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Default fragment
        Fragment initialFragment = new EventsFragment();
        setFragment(initialFragment, EventsFragment.getFragmentTag(), EventsFragment.getFragmentTitle());
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
                    setFragment(oldFragment, previous.getTag(), previous.getTitle(), previous.getArgs(), false);
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

            // Respond to the action bar's Up/Home button
            /*case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;*/
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReplaceFragment(String newFragmentTag, String newFragmentName, Fragment newFragment) {
        setFragment(newFragment, newFragmentTag, newFragmentName);
    }

    private void setFragment(Fragment fragment, String tag, String title, List<Object> args) {
        setFragment(fragment, tag, title, args, true);
    }

    private void setFragment(Fragment fragment, String tag, String title, List<Object> args, boolean history) {
        if (history) {
            HistoryFragment historyFragment = new HistoryFragment(tag, title, args);
            historyFragments.push(historyFragment);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_content_frame, fragment)
                .commit();

        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    private void setFragment(Fragment fragment, String tag, String title) {
        setFragment(fragment, tag, title, null, true);
    }

    private void setFragment(Fragment fragment, String tag, String title, boolean history) {
        setFragment(fragment, tag, title, null, history);
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
        }
        else if(id == R.id.nav_events) {
            fragment = EventsFragment.newInstance(bundle);
            fragmentName = MainActivity.EVENTS_FRAGMENT;
        }
        else if (id == R.id.nav_rewards) {
            fragment = RewardsFragment.newInstance();
            fragmentName = MainActivity.REWARDS_FRAGMENT;
            fragmentTitle = "Boutique";
        }

        drawer.closeDrawer(GravityCompat.START);

        setFragment(fragment, fragmentName, fragmentTitle);

        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO : Action ?

    }


    @Override
    public void onQrCodeFound(String value) {
        Intent intent = QrCodeFoundActivity.newIntent(this, value);

        startActivity(intent);
//        Fragment fragment = QrCodeFoundActivity.newInstance(value);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.main_activity_content_frame, fragment)
//                .commit();
    }



}
