package h4311.hexanome.insa.lyonrewards.view.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;

public class EventsFragmentGrandLyonTabViewAdapter extends RecyclerView.Adapter<EventsFragmentGrandLyonTabViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static int MAX_LENGTH_DESCRIPTION = 100;

        protected MainActivity mMainActivity;

        @BindView(R.id.card_event_title)
        protected TextView mTitle;

        @BindView(R.id.card_event_several_dates_prefix)
        protected TextView mStartDateSeveralDatesPrefix;

        @BindView(R.id.card_event_one_date_prefix)
        protected TextView mStartDateOneDatePrefix;

        @BindView(R.id.card_event_end_date_prefix)
        protected TextView mEndDatePrefix;

        @BindView(R.id.card_event_start_date)
        protected TextView mStartDate;

        @BindView(R.id.card_event_end_date)
        protected TextView mEndDate;

        @BindView(R.id.card_event_description)
        protected TextView mDescription;

        @BindView(R.id.card_event_publish_date)
        protected TextView mPublishDate;

        @BindView(R.id.card_event_progressbar_done)
        protected View mProgressBarDone;

        @BindView(R.id.card_event_progressbar_todo)
        protected View mProgressBarTodo;

        @BindView(R.id.card_progressbar_numeric_label)
        protected TextView mProgressBarLabel;

        private Event mEvent = null;

        public ViewHolder(View view, MainActivity activity) {
            super(view);
            ButterKnife.bind(this, view);
            mMainActivity = activity;
        }

        public void setEvent(Event event) {
            mEvent = event;

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

            // Progress bar
            float progress = mEvent.getUserProgression() * 100.0f;
            float todo = 100 - progress;
            mProgressBarDone.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, progress));
            mProgressBarTodo.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, todo));
            mProgressBarLabel.setText(String.format("%.2f", progress));
        }

        @OnClick(R.id.card_event_card_view)
        public void cardviewOnClick() {
            if (mEvent != null) {
                EventDetailFragment fragment = EventDetailFragment.newInstance(mEvent);
                String fragmentTitle = mEvent.getTitle();
                List<Object> args = new ArrayList<>();
                args.add(mEvent);
                mMainActivity.setFragment(fragment, EventDetailFragment.getFragmentTag(), fragmentTitle, true, args);
            }
        }
    }

    protected List<Event> events;

    protected MainActivity mActivity;

    public EventsFragmentGrandLyonTabViewAdapter(List<Event> events, MainActivity mainActivity) {
        this.events = events;
        this.mActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event_layout, parent, false);
        return new ViewHolder(view, mActivity);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


}
