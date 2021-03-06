package h4311.hexanome.insa.lyonrewards.view.rewards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RewardsFragment extends Fragment {

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager mConnectionManager;

    @BindView(R.id.rewards_offers_recyclerview)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.rewards_nb_points)
    protected TextView mNbPoints;

    private RecyclerView.Adapter mAdapter;

    private List<Offer> mContentOffers = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public RewardsFragment() {
        // Required empty public constructor
    }

    public static RewardsFragment newInstance() {
        RewardsFragment fragment = new RewardsFragment();
        return fragment;
    }

    public static String getFragmentTag() {
        return "REWARD_FRAGMENT";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("FRAGMENT", "RewardsFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        ButterKnife.bind(this, view);
        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNbPoints.setText(String.valueOf(mConnectionManager.getConnectedUser().getCurrentPoints()));

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new RewardsFragmentOffersAdapter(mContentOffers, (MainActivity) getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        lyonRewardsApi.getAllOffers(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                mContentOffers.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                // TODO : Error message
                Log.d("API", "error : " + t.getMessage());
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateCustom() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("FRAGMENT", "RewardsFragment on resume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("FRAGMENT", "RewardsFragment onSaveInstanceState");
    }

    public interface OnFragmentInteractionListener {
        void onReplaceFragment(String newFragmentTag, String newFragmentName, Fragment newFragment);
    }
}
