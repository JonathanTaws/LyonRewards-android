package h4311.hexanome.insa.lyonrewards.view.events;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;

/**
 * Created by Pierre on 28/04/2016.
 */
public class EventSuccessAdapter extends RecyclerView.Adapter<EventSuccessAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        // todo : business object
        public void setSuccess(Object success) {

        }

    }

    protected List<Object> success;

    public EventSuccessAdapter(List<Object> success) {
        this.success = success;
    }

    @Override
    public EventSuccessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_qrcode_success, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventSuccessAdapter.ViewHolder holder, int position) {
        holder.setSuccess(success.get(position));
    }

    @Override
    public int getItemCount() {
        return success.size();
    }
}