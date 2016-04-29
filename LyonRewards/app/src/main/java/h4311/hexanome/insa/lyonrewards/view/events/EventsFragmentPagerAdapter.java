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
    private static Class<? extends Fragment>[] tabsFragments = new Class[]{ EventsFragmentGrandLyonTab.class };

    public EventsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
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
