package h4311.hexanome.insa.lyonrewards.view.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.business.Offer;
import h4311.hexanome.insa.lyonrewards.business.act.TravelCitizenAct;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jonathan on 02/05/2016.
 */
public class ProfileFragment extends Fragment {

    private static final int MAX_NB_RESULTS = 100;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager connectionManager;

    @BindView(R.id.profile_pager)
    protected MaterialViewPager profilePager;

    private ProfilePagerAdapter mProfilePagerAdapter;

    private List<Object> historyOffers = new ArrayList<>();
    private List<Object> historyTravels = new ArrayList<>();

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

        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        mProfilePagerAdapter = new ProfilePagerAdapter(getFragmentManager(), historyOffers, historyTravels);
        profilePager.getViewPager().setAdapter(mProfilePagerAdapter);

        profilePager.getViewPager().setOffscreenPageLimit(profilePager.getViewPager().getAdapter().getCount());
        profilePager.getPagerTitleStrip().setViewPager(profilePager.getViewPager());

        // Set logo
        TextView textLogo = (TextView) profilePager.findViewById(R.id.profile_username_logo);
        textLogo.setText(connectionManager.getConnectedUser().getUsername().substring(0, 1));

        Toolbar toolbar = profilePager.getToolbar();
        toolbar.setVisibility(View.GONE);

        /*
        lyonRewardsApi.getUserHistory(connectionManager.getConnectedUser().getId(), MAX_NB_RESULTS, new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                if (response.code() == 200) {
                    List<JsonObject> objects = response.body();

                    Gson gson = new Gson();
                    for (JsonObject jsonObject : objects) {
                        if (jsonObject.has("citizen_act")) {
                            JsonObject citizenActJson = jsonObject.getAsJsonObject("citizen_act");
                            if ("travel".equals(citizenActJson.get("type"))) {
                                TravelCitizenAct travelCitizenAct = gson.fromJson(jsonObject, TravelCitizenAct.class);
                                historyTravels.add(travelCitizenAct);
                                mProfilePagerAdapter.notifyDataSetChanged();
                            }
                        } else if (jsonObject.has("partner_offer")) {
                            Offer partnerOffer = gson.fromJson(jsonObject, Offer.class);
                            historyOffers.add(partnerOffer);
                            mProfilePagerAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("API", "error : unkown json ");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                // TODO
                Log.d("API", "error : " + t.getMessage());
            }
        });
        */

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
