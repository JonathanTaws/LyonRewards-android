package h4311.hexanome.insa.lyonrewards.view.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTab;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentActsTab;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentInfoTab;
import h4311.hexanome.insa.lyonrewards.view.scrolltab.ScrolltabPagerAdapter;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfilePagerAdapter extends ScrolltabPagerAdapter {

    private static String[] tabsLabels = { "Mes actes", "Mes informations" };
    private static Class<? extends Fragment>[] tabsFragments = new Class[]{ ProfileFragmentActsTab.class, ProfileFragmentInfoTab.class };

    public ProfilePagerAdapter(FragmentManager fm) {
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

    @Override
    public Fragment getItem(int position) {
        int index = position % getTabsLabels().length;
        switch (index) {
            case 0:
                return ProfileFragmentActsTab.newInstance();
            case 1:
                return ProfileFragmentInfoTab.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabsFragments.length;
    }
}
