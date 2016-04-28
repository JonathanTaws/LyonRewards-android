package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;

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

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

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

        String qrCodeValue = getIntent().getExtras().getString(ARG_QR_CODE_VALUE);
        qrCodeValueTextView.setText(qrCodeValue);
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
