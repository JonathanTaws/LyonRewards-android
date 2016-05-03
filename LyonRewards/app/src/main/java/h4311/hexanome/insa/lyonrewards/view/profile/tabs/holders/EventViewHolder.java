package h4311.hexanome.insa.lyonrewards.view.profile.tabs.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;

/**
 * Created by Jonathan on 03/05/2016.
 */
public class EventViewHolder extends RecyclerView.ViewHolder {

    private Event event;

    @BindView(R.id.event_title)
    protected TextView eventTitle;

    private View view;

    protected MainActivity mainActivity;

    public EventViewHolder(View view, MainActivity activity) {
        super(view);
        ButterKnife.bind(this, view);
        this.mainActivity = activity;
    }

    public void setEvent(Event event) {
        this.event = event;
        eventTitle.setText(event.getTitle());
    }

}
