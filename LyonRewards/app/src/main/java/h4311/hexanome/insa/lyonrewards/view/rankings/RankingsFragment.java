package h4311.hexanome.insa.lyonrewards.view.rankings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentPagerAdapter;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentPagerListener;

/**
 * Created by Jonathan on 30/04/2016.
 */
public class RankingsFragment extends Fragment {

    public RankingsFragment() {
        // Required empty public constructor
    }

    public static String getFragmentTag() {
        return RankingsFragment.class.getName();
    }

    public static String getFragmentTitle() {
        return "Classements";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventsFragment.
     */
    public static RankingsFragment newInstance(Bundle bundle) {
        RankingsFragment fragment = new RankingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
