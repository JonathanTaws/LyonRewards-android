package h4311.hexanome.insa.lyonrewards.view.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;

/**
 * Created by Pierre on 29/04/2016.
 */
public class EventCardView extends LinearLayout {

    private static int MAX_LENGTH_DESCRIPTION = 100;

    private Event mEvent;

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

    public EventCardView(Context context, Event event) {
        super(context);
        mEvent = event;

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View child = inflater.inflate(R.layout.cardview_event_layout, null);
        ButterKnife.bind(this, child);

        addView(child);

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
        mProgressBarLabel.setText(String.valueOf((int) progress));
    }

}
