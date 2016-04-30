package h4311.hexanome.insa.lyonrewards.view.events;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventsFragmentPagerListener implements MaterialViewPager.Listener {
    private Activity mActivity;

    public EventsFragmentPagerListener(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public HeaderDesign getHeaderDesign(int page) {
        switch (page) {
            case 0:
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.colorBackground,
                        mActivity.getDrawable(R.drawable.musee_des_confulances));
            case 1:
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.colorBackground,
                        mActivity.getDrawable(R.drawable.lyon_pont));
        }

        return null;
    }
}
