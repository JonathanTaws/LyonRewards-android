package h4311.hexanome.insa.lyonrewards.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentActsTab;
import h4311.hexanome.insa.lyonrewards.view.profile.tabs.ProfileFragmentInfoTab;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_pager)
    protected ViewPager profilePager;

    private ProfilePagerAdapter mProfilePagerAdapter;

    public static String getFragmentTag() {
        return ProfileFragment.class.getName();
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
    public static ProfileFragment newInstance(Bundle bundle) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        mProfilePagerAdapter = new ProfilePagerAdapter(getFragmentManager());
        profilePager.setAdapter(mProfilePagerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
