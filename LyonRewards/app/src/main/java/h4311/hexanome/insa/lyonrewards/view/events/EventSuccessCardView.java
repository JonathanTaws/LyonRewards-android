package h4311.hexanome.insa.lyonrewards.view.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;

/**
 * Created by Pierre on 29/04/2016.
 */
public class EventSuccessCardView extends LinearLayout {

    private boolean history;

    @BindView(R.id.card_qrcode_success_image)
    protected ImageView mImage;

    @BindView(R.id.card_qrcode_success_title)
    protected TextView mTitle;

    @BindView(R.id.card_qrcode_success_description)
    protected TextView mDescription;

    @BindView(R.id.card_qrcode_success_nbpoints)
    protected TextView mNbPoints;

    @BindView(R.id.card_qrcode_success_date)
    protected TextView mDate;

    @BindView(R.id.card_qrcode_success_cardview_title_event)
    protected TextView mTitleEvent;

    public EventSuccessCardView(Context context, boolean history) {
        super(context);
        this.history = history;

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.cardview_qrcode_success, null);
        ButterKnife.bind(this, child);

        addView(child);
    }

    public EventSuccessCardView(Context context) {
        this(context, false);
    }

    public EventSuccessCardView(Context context, QRCodeCitizenAct qrCodeReceived) {
        this(context, false);
        setQrCodeCitizenAct(qrCodeReceived);
    }

    public void setQrCodeCitizenAct(QRCodeCitizenAct qrCodeCitizenAct) {
        if (history) {
            if (qrCodeCitizenAct.getEventTitle() != null) {
                mTitleEvent.setText(qrCodeCitizenAct.getEventTitle());
                mTitleEvent.setVisibility(VISIBLE);
            }
        }

        mTitle.setText(qrCodeCitizenAct.getTitle());
        mDescription.setText(qrCodeCitizenAct.getDescription());
        mNbPoints.setText(String.valueOf(qrCodeCitizenAct.getPoints()));

        if (qrCodeCitizenAct.isCompleted()) {
            mImage.setImageResource(R.drawable.icon_success_key_finished);
            mDate.setText(qrCodeCitizenAct.getCompletedDateString());
        } else {
            mDate.setVisibility(GONE);
        }
    }
}
