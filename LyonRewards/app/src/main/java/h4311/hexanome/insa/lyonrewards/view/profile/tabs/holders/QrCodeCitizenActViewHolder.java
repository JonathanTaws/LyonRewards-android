package h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders;

import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventSuccessCardView;

/**
 * Created by Jonathan on 03/05/2016.
 */
public class QrCodeCitizenActViewHolder extends RecyclerView.ViewHolder {

    private QRCodeCitizenAct qrCodeCitizenAct;

    private EventSuccessCardView eventSuccessCardView;

    protected MainActivity mainActivity;

    public QrCodeCitizenActViewHolder(EventSuccessCardView eventSuccessCardView, MainActivity activity) {
        super(eventSuccessCardView);
        this.eventSuccessCardView = eventSuccessCardView;
        ButterKnife.bind(this, eventSuccessCardView);
        this.mainActivity = activity;
    }

    public void setQrCodeCitizenAct(QRCodeCitizenAct qrCodeCitizenAct) {
        this.qrCodeCitizenAct = qrCodeCitizenAct;
        eventSuccessCardView.setQrCodeCitizenAct(qrCodeCitizenAct);
    }
}