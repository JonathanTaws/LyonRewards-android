package h4311.hexanome.insa.lyonrewards.view.events;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;

/**
 * Created by Pierre on 28/04/2016.
 */
public class EventDetailPointsFragmentTabViewAdapter extends RecyclerView.Adapter<EventDetailPointsFragmentTabViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Event mEvent = null;
        private FragmentActivity mActivity;
        private FragmentManager mChildFragmentManager;

        @BindView(R.id.event_detail_success_recyclerview)
        protected RecyclerView mRecyclerView;

        private RecyclerView.Adapter mAdapter;

        // Todo business class
        private List<Object> mContentSuccess = new ArrayList<>();

        public ViewHolder(View view, FragmentActivity activity, FragmentManager childFragmentManager) {
            super(view);
            mActivity = activity;
            this.mChildFragmentManager = childFragmentManager;
            ButterKnife.bind(this, view);
        }

        public void setEvent(Event event) {
            mEvent = event;

            // RecyclerView binding
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            mAdapter = new EventSuccessAdapter(mContentSuccess);
            mRecyclerView.setAdapter(mAdapter);

            MaterialViewPagerHelper.registerRecyclerView(mActivity, mRecyclerView, null);

            mAdapter.notifyDataSetChanged();
        }

    }

    protected List<Event> events;
    private FragmentActivity mActivity;
    private FragmentManager mChildFragmentManager;

    public EventDetailPointsFragmentTabViewAdapter(List<Event> events, FragmentActivity activity, FragmentManager childFragmentManager) {
        this.events = events;
        this.mActivity = activity;
        this.mChildFragmentManager = childFragmentManager;
    }

    @Override
    public EventDetailPointsFragmentTabViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_detail_points, parent, false);
        return new ViewHolder(view, mActivity, mChildFragmentManager);
    }

    @Override
    public void onBindViewHolder(EventDetailPointsFragmentTabViewAdapter.ViewHolder holder, int position) {
        holder.setEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


}
