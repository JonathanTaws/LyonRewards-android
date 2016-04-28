package h4311.hexanome.insa.lyonrewards.view.events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.view.scrolltab.ScrolltabPagerAdapter;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventDetailPagerAdapter  extends ScrolltabPagerAdapter {

    private static String[] tabsLabels = { "Description", "Mes Succès" };

    private static Class<? extends Fragment>[] tabsFragments = new Class[]{ EventDetailDescriptionFragment.class, EventDetailPointsFragment.class };

    private Event mEvent;

    public EventDetailPagerAdapter(Event event, FragmentManager fm) {
        super(fm);
        mEvent = event;
    }



    @Override
    public Fragment getItem(int position) {
        int index = position % getTabsLabels().length;
        switch (index) {
            case 0:
                return EventDetailDescriptionFragment.newInstance(mEvent);
            case 1:
                return EventDetailPointsFragment.newInstance(mEvent);
        }
        return null;
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