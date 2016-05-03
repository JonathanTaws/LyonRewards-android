package h4311.hexanome.insa.lyonrewards.view.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentActsTab;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentInfoTab;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentOffersTab;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentTravelsTab;
import h4311.hexanome.insa.lyonrewards.view.scrolltab.ScrolltabPagerAdapter;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfilePagerAdapter extends ScrolltabPagerAdapter {

    private static String[] tabsLabels = { "Mes informations", "Mes succès", "Mes déplacements", "Mes offres" };
    private static Class<? extends Fragment>[] tabsFragments = new Class[]{ ProfileFragmentInfoTab.class, ProfileFragmentActsTab.class, ProfileFragmentTravelsTab.class, ProfileFragmentOffersTab.class };
    private final List<Object> historyOffers;
    private final List<Object> historyTravels;

    public ProfilePagerAdapter(FragmentManager fm, List<Object> historyOffers, List<Object> historyTravels) {
        super(fm);
        this.historyOffers = historyOffers;
        this.historyTravels = historyTravels;
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
                return ProfileFragmentInfoTab.newInstance();
            case 1:
                return ProfileFragmentActsTab.newInstance();
            case 2:
                return ProfileFragmentTravelsTab.newInstance();
            case 3:
                return ProfileFragmentOffersTab.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabsFragments.length;
    }
}
