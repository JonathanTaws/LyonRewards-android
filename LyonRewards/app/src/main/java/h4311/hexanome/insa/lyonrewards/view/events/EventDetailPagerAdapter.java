package h4311.hexanome.insa.lyonrewards.view.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.view.scrolltab.ScrolltabPagerAdapter;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventDetailPagerAdapter  extends ScrolltabPagerAdapter {

    private static String[] tabsLabels = { "Description", "Points" };
    private static Class<? extends Fragment>[] tabsFragments = new Class[]{ EventsFragmentGrandLyonTab.class };
    private Event mEvent;

    public EventDetailPagerAdapter(Event event, FragmentManager fm) {
        super(fm);
        mEvent = event;
    }

    @Override
    public Fragment getItem(int position) {
        int index = position % getTabsFragments().length;
        switch (index) {
            case 0:
                return EventDetailDescriptionFragment.newInstance(mEvent);
        }
        return  null;
    }

    @Override
    protected String[] getTabsLabels() {
        return tabsLabels;
    }

    @Override
    protected Class<? extends Fragment>[] getTabsFragments() {
        return tabsFragments;
    }

}