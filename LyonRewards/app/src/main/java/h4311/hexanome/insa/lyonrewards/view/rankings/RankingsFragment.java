package h4311.hexanome.insa.lyonrewards.view.rankings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.User;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTabViewAdapter;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentPagerAdapter;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentPagerListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jonathan on 30/04/2016.
 */
public class RankingsFragment extends Fragment {

    @BindView(R.id.rankings_recycler_view)
    protected RecyclerView mRecyclerView;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    private RecyclerView.Adapter mAdapter;

    private List<User> mUsers;

    public RankingsFragment() {
        mUsers = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_rankings, container, false);
        ButterKnife.bind(this, view);

        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RankingsViewAdapter(mUsers, (MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


        lyonRewardsApi.getRankings(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.code() == 200) {
                    List<User> users = response.body();
                    mUsers.addAll(users);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // TODO
                Log.d("API", "Error : " + t.getMessage());
            }
        });
    }
}
