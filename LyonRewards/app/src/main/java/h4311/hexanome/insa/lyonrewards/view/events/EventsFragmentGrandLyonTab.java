package h4311.hexanome.insa.lyonrewards.view.events;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventsFragmentGrandLyonTab extends Fragment {

    private static final String INTENT_USER_PARTICIPATED_ONLY = "userParticipatedOnly";

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager mConnectionManager;

    @BindView(R.id.scrolltab_tab_recyclerview)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.scrolltab_tab_no_data_text)
    protected TextView mNoDataText;

    private RecyclerView.Adapter mAdapter;

    private List<Event> mContentEvents = new ArrayList<>();

    private boolean mShowOnlyEventsWithUserParticipation = false;

    public static EventsFragmentGrandLyonTab newInstance(boolean userParticipationOnly) {
        EventsFragmentGrandLyonTab fragment = new EventsFragmentGrandLyonTab();
        Bundle args = new Bundle();
        args.putBoolean(INTENT_USER_PARTICIPATED_ONLY, userParticipationOnly);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        if (getArguments() != null) {
            mShowOnlyEventsWithUserParticipation = getArguments().getBoolean(INTENT_USER_PARTICIPATED_ONLY);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new EventsFragmentGrandLyonTabViewAdapter(mContentEvents, (MainActivity) getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        lyonRewardsApi.getAllEvents(mConnectionManager.getConnectedUser().getId(), mShowOnlyEventsWithUserParticipation, new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> eventList = response.body();
                if (eventList.isEmpty()) {
                    if (mShowOnlyEventsWithUserParticipation) {
                        mNoDataText.setText("Vous n'avez participé à aucun évènement.");
                    } else {
                        mNoDataText.setText("Il n'existe aucun évènement en cours ou à venir.");
                    }
                    mNoDataText.setVisibility(View.VISIBLE);
                } else {
                    mContentEvents.addAll(eventList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // TODO : Error message
                Log.d("API", "error : " + t.getMessage());
            }
        });


    }
}