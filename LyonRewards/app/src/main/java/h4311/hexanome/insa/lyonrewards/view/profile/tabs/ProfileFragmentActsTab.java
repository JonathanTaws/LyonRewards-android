package h4311.hexanome.insa.lyonrewards.view.profile.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfileFragmentActsTab extends Fragment {

    public static String getFragmentTag() {
        return ProfileFragmentActsTab.class.getName();
    }

    public static String getFragmentTitle() {
        return "Profil";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventsFragment.
     */
    public static ProfileFragmentActsTab newInstance() {
        ProfileFragmentActsTab fragment = new ProfileFragmentActsTab();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
