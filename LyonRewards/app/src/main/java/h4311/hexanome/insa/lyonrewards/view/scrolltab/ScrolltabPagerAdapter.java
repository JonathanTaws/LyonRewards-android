package h4311.hexanome.insa.lyonrewards.view.scrolltab;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTab;

/**
 * Created by Pierre on 27/04/2016.
 */
public abstract class ScrolltabPagerAdapter extends FragmentStatePagerAdapter {

    public ScrolltabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    protected abstract String[] getTabsLabels();
    protected abstract Class<? extends Fragment>[] getTabsFragments();

    @Override
    public Fragment getItem(int position) {
        int index = position % getTabsFragments().length;
        try {
            return getTabsFragments()[index].newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // Todo : handle error
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCount() {
        return getTabsLabels().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int index = position % getTabsLabels().length;
        return getTabsLabels()[index];
    }
}