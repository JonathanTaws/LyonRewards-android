package h4311.hexanome.insa.lyonrewards.view.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;

public class EventDetailActivity extends AppCompatActivity {

    public static final String INTENT_EVENT = "h4311.hexanome.insa.lyonrewards.view.events.eventid";

    @BindView(R.id.maintoolbar)
    protected Toolbar toolbar;

    @BindView(R.id.scrolltab_material_view_pager)
    protected MaterialViewPager mViewPager;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        ((LyonRewardsApplication) getApplication()).getAppComponent().inject(this);

        toolbar.setTitle("Chargement...");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the event id
        Intent intent = getIntent();
        Event event = intent.getParcelableExtra(INTENT_EVENT);

        if (event != null) {
            toolbar.setTitle(event.getTitle());

            mViewPager.getViewPager().setAdapter(new EventDetailPagerAdapter(event, getSupportFragmentManager()));
            mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
            mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        }

        //mViewPager.setMaterialViewPagerListener(new EventsFragmentPagerListener());

        // TODO : check
        mViewPager.getToolbar().setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
