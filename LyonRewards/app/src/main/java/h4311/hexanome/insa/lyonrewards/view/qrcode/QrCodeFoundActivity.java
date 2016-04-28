package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Jonathan on 28/04/2016.
 */
public class QrCodeFoundActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ARG_QR_CODE_VALUE = "qrCodeValue";

    @BindView(R.id.maintoolbar)
    protected Toolbar toolbar;

    @BindView(R.id.button_reclaim_points)
    Button buttonReclaimPoints;

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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
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
    }
}
