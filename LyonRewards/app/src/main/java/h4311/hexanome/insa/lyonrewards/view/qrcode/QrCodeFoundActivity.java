package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTabViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jonathan on 28/04/2016.
 */
public class QrCodeFoundActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ARG_QR_CODE_VALUE = "qrCodeValue";

    @BindView(R.id.maintoolbar)
    protected Toolbar toolbar;

    @BindView(R.id.button_reclaim_points)
    protected Button buttonReclaimPoints;

    @BindView(R.id.qr_code_value)
    protected TextView qrCodeValueTextView;

    @BindView(R.id.qr_code_found_recyclerview)
    protected RecyclerView qrCodeFoundEvent;

    private RecyclerView.Adapter mAdapter;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    private List<Event> mEvents;

    public static Intent newIntent(Context context, String qrCodeValue){
        Intent intent = new Intent(context, QrCodeFoundActivity.class);
        intent.putExtra(ARG_QR_CODE_VALUE, qrCodeValue);

        return intent;
    }
    @Override
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

        mAdapter = new EventsFragmentGrandLyonTabViewAdapter(mEvents, getApplicationContext());
        qrCodeFoundEvent.setAdapter(mAdapter);

        String qrCodeValue = getIntent().getExtras().getString(ARG_QR_CODE_VALUE);
        parseQrCode(qrCodeValue);

        qrCodeValueTextView.setText(qrCodeValue);
    }

    private void parseQrCode(String qrCodeValue) {
        Pattern qrCodePattern = Pattern.compile("(\\d+)&(\\d+)");
        Matcher m = qrCodePattern.matcher(qrCodeValue);
        if(m.matches()) {
            int actId = Integer.parseInt(m.group(1));
            int eventId = Integer.parseInt(m.group(2));

            Log.d("QR_CODE_PARSER", "ActId : " + actId);
            Log.d("QR_CODE_PARSER", "EventId : " + eventId);

            lyonRewardsApi.getEventById(eventId, new Callback<Event>() {
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
        }
    }

    private void displayEvent(Event event) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // TODO Go back to QR scan
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {
        // TODO Link with API
        //lyonRewardsApi.addActToUser();
    }
}
