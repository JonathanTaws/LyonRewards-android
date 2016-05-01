package h4311.hexanome.insa.lyonrewards.view.events;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Pierre on 01/05/2016.
 */
public class TagCardView extends LinearLayout {

    @BindView(R.id.card_tag_title)
    protected TextView mTagTitle;

    public TagCardView(Activity activity, String tag) {
        super(activity);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View child = inflater.inflate(R.layout.cardview_tag, null);
        ButterKnife.bind(this, child);

        addView(child);

        mTagTitle.setText(tag);
    }
}
