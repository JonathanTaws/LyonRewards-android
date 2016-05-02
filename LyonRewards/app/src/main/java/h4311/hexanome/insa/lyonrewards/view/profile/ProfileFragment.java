package h4311.hexanome.insa.lyonrewards.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_pager)
    protected MaterialViewPager profilePager;

    private ProfilePagerAdapter mProfilePagerAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

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
        profilePager.getViewPager().setAdapter(mProfilePagerAdapter);

        profilePager.getViewPager().setOffscreenPageLimit(profilePager.getViewPager().getAdapter().getCount());
        profilePager.getPagerTitleStrip().setViewPager(profilePager.getViewPager());

        Toolbar toolbar = profilePager.getToolbar();
        toolbar.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
