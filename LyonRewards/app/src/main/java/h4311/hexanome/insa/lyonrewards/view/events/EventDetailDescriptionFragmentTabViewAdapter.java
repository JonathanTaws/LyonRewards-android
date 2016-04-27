package h4311.hexanome.insa.lyonrewards.view.events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventDetailDescriptionFragmentTabViewAdapter extends RecyclerView.Adapter<EventDetailDescriptionFragmentTabViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Event mEvent = null;

        public ViewHolder(View view) {
            super(view);
            //ButterKnife.bind(this, view);
        }

        public void setEvent(Event event) {
            /*
            mTitle.setText(event.getTitle());

            mStartDate.setText(event.getStartDateString());
            if (event.getStartDate().equals(event.getEndDate())) {
                mStartDateOneDatePrefix.setVisibility(View.VISIBLE);
                mStartDateSeveralDatesPrefix.setVisibility(View.GONE);
                mEndDatePrefix.setVisibility(View.GONE);
                mEndDate.setVisibility(View.GONE);
            } else {
                mStartDateOneDatePrefix.setVisibility(View.GONE);
                mStartDateSeveralDatesPrefix.setVisibility(View.VISIBLE);
                mEndDatePrefix.setVisibility(View.VISIBLE);
                mEndDate.setVisibility(View.VISIBLE);
                mEndDate.setText(event.getEndDateString());
            }

            String description = event.getDescription();
            if (description.length() > MAX_LENGTH_DESCRIPTION) {
                description = description.substring(0, MAX_LENGTH_DESCRIPTION).concat("...");
            }
            mDescription.setText(description);

            mPublishDate.setText(event.getPublishDateString());
*/
            mEvent = event;
        }
    }

    protected List<Event> events;
    private Context mContext;

    public EventDetailDescriptionFragmentTabViewAdapter(List<Event> events, Context context) {
        this.events = events;
        this.mContext = context;
    }

    @Override
    public EventDetailDescriptionFragmentTabViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_detail_description, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventDetailDescriptionFragmentTabViewAdapter.ViewHolder holder, int position) {
        holder.setEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
