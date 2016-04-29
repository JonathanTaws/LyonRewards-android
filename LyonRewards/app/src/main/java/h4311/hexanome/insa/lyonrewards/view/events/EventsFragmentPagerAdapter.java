package h4311.hexanome.insa.lyonrewards.view.events;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTab;
import h4311.hexanome.insa.lyonrewards.view.scrolltab.ScrolltabPagerAdapter;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventsFragmentPagerAdapter extends ScrolltabPagerAdapter {

    private static String[] tabsLabels = { "Fil d'actualité", "Mes évènements" };
    private static Class<? extends Fragment>[] tabsFragments = new Class[]{ EventsFragmentGrandLyonTab.class, EventsFragmentGrandLyonTab.class };

    public EventsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        int index = position % getTabsLabels().length;
        switch (index) {
            case 0:
                return EventsFragmentGrandLyonTab.newInstance(false);
            case 1:
                return EventsFragmentGrandLyonTab.newInstance(true);
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
