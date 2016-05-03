package h4311.hexanome.insa.lyonrewards.view.profile.tabs;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pierre on 03/05/2016.
 */
public class ProfileFragmentTravelsTab extends Fragment {

    private static final int MAX_NB_RESULTS = 100;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager connectionManager;

    @Inject
    protected Gson gson;


    @BindView(R.id.scrolltab_tab_recyclerview)
    protected RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private List<Object> mObjects = new ArrayList<>();

    public ProfileFragmentTravelsTab
            () {
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
    public static ProfileFragmentTravelsTab newInstance() {
        ProfileFragmentTravelsTab fragment = new ProfileFragmentTravelsTab();
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

    private static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return false;
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
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

        lyonRewardsApi.getUserHistoryTravels(connectionManager.getConnectedUser().getId(), MAX_NB_RESULTS, new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                if (response.isSuccessful()) {
                    List<JsonObject> objects = response.body();

                    Date currentDate = null;
                    int currentPoints = 0;
                    Map<String, TravelCitizenAct> currentTravelMap = new HashMap<String, TravelCitizenAct>();

                    for (JsonObject jsonObject : objects) {
                        if (jsonObject.has("citizen_act")) {
                            JsonObject citizenActJson = jsonObject.getAsJsonObject("citizen_act");

                            // todo : improve algo and code
                            if ("travel".equals(citizenActJson.get("type").getAsString())) {
                                TravelCitizenAct travelCitizenAct = gson.fromJson(citizenActJson, TravelCitizenAct.class);
                                Date date = gson.fromJson(jsonObject.get("date"), Date.class);

                                if (!currentTravelMap.containsKey(travelCitizenAct.getTitle())) {
                                    currentTravelMap.put(travelCitizenAct.getTitle(), null);
                                }

                                // First date
                                if (currentDate == null) {
                                    currentTravelMap.put(travelCitizenAct.getTitle(), travelCitizenAct);
                                    currentDate = date;
                                    currentPoints = travelCitizenAct.getPoints();
                                }

                                // Get the current travel of the current day
                                TravelCitizenAct currentTravel = currentTravelMap.get(travelCitizenAct.getTitle());

                                // Increase the number of points of current travel
                                if (isSameDay(date, currentDate)) {
                                    currentPoints += travelCitizenAct.getPoints();
                                } else {
                                    // Add all the current travels
                                    for (Map.Entry<String, TravelCitizenAct> actEntry : currentTravelMap.entrySet()) {
                                        if (actEntry.getValue() != null) {
                                            actEntry.getValue().setCompletedDate(currentDate);
                                            actEntry.getValue().setPoints(currentPoints);
                                            mObjects.add(actEntry.getValue());
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    currentTravelMap.clear();

                                    // Put the new citizen act
                                    currentTravelMap.put(travelCitizenAct.getType(), travelCitizenAct);
                                    currentDate = date;
                                    currentPoints = travelCitizenAct.getPoints();
                                }
                            }
                        }
                    }

                    // Add the last day
                    for (Map.Entry<String, TravelCitizenAct> actEntry : currentTravelMap.entrySet()) {
                        if (actEntry.getValue() != null) {
                            actEntry.getValue().setCompletedDate(currentDate);
                            actEntry.getValue().setPoints(currentPoints);
                            mObjects.add(actEntry.getValue());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    // TODO
                    Log.d("API", "error : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                // TODO
                Log.d("API", "error : " + t.getMessage());
            }
        });

        /*
        lyonRewardsApi.getMyEvents(connectionManager.getConnectedUser().getId(), new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.code() == 200) {
                    List<Event> events = response.body();

                    for(Event event : events) {

                        mObjects.add(event);
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
*/
    }
}
