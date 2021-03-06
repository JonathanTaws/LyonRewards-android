package h4311.hexanome.insa.lyonrewards.view.profile.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.act.QRCodeCitizenAct;
import h4311.hexanome.insa.lyonrewards.business.act.TravelCitizenAct;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import h4311.hexanome.insa.lyonrewards.view.events.EventsFragmentGrandLyonTabViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfileFragmentActsTab extends Fragment {

    private static final int MAX_NB_RESULTS = 100;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager connectionManager;

    @BindView(R.id.scrolltab_tab_recyclerview)
    protected RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    // todo : user history with acts type
    private List<Object> mObjects = new ArrayList<>();

    public ProfileFragmentActsTab() {

    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new ProfileFragmentsActsTabAdapter(mObjects, (MainActivity) getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        lyonRewardsApi.getMyEvents(connectionManager.getConnectedUser().getId(), new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()) {
                    List<Event> events = response.body();

                    for (final Event event : events) {

                        lyonRewardsApi.getQrCodesFromEvent(event.getId(), connectionManager.getConnectedUser().getId(), new Callback<List<QRCodeCitizenAct>>() {
                            @Override
                            public void onResponse(Call<List<QRCodeCitizenAct>> call, Response<List<QRCodeCitizenAct>> response) {
                                if (response.isSuccessful()) {
                                    for (QRCodeCitizenAct qrCodeCitizenAct : response.body()) {
                                        if (qrCodeCitizenAct.isCompleted()) {
                                            qrCodeCitizenAct.setEventTitle(event.getTitle());
                                            // todo : order
                                            mObjects.add(qrCodeCitizenAct);
                                        }
                                    }
                                } else {
                                    // TODO
                                    Log.d("API", "error : " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<QRCodeCitizenAct>> call, Throwable t) {
                                // TODO
                                Log.d("API", "error : " + t.getMessage());
                            }
                        });

                        //mObjects.add(event);
                        //mObjects.add(new QRCodeCitizenAct(1, "test", "test", 12, 1, true, new Date()));
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // TODO
                Log.d("API", "error : " + t.getMessage());
            }
        });

    }
}
