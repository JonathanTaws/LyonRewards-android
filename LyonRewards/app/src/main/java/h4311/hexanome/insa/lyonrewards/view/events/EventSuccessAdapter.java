package h4311.hexanome.insa.lyonrewards.view.events;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;

/**
 * Created by Pierre on 28/04/2016.
 */
public class EventSuccessAdapter extends RecyclerView.Adapter<EventSuccessAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EventSuccessCardView view;

        /*
        @BindView(R.id.card_qrcode_success_image)
        protected ImageView mImage;

        @BindView(R.id.card_qrcode_success_title)
        protected TextView mTitle;

        @BindView(R.id.card_qrcode_success_description)
        protected TextView mDescription;

        @BindView(R.id.card_qrcode_success_nbpoints)
        protected TextView mNbPoints;

        @BindView(R.id.card_qrcode_success_date)
        protected TextView mDate;*/

        public ViewHolder(EventSuccessCardView view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        public void setSuccess(QRCodeCitizenAct qrCodeCitizenAct) {
            /*
            mTitle.setText(qrCodeCitizenAct.getTitle());
            mDescription.setText(qrCodeCitizenAct.getDescription());
            mNbPoints.setText(String.valueOf(qrCodeCitizenAct.getPoints()));*/
            view.setQrCodeCitizenAct(qrCodeCitizenAct);
        }
    }

    protected List<QRCodeCitizenAct> successList;

    public EventSuccessAdapter(List<QRCodeCitizenAct> success) {
        this.successList = success;
    }

    @Override
    public EventSuccessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_qrcode_success, parent, false);
        return new ViewHolder(new EventSuccessCardView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(EventSuccessAdapter.ViewHolder holder, int position) {
        holder.setSuccess(successList.get(position));
    }

    @Override
    public int getItemCount() {
        return successList.size();
    }
}
