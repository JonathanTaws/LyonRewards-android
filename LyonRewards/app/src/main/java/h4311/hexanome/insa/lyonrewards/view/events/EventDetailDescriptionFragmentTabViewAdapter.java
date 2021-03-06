package h4311.hexanome.insa.lyonrewards.view.events;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;

/**
 * Created by Pierre on 27/04/2016.
 */
public class EventDetailDescriptionFragmentTabViewAdapter extends RecyclerView.Adapter<EventDetailDescriptionFragmentTabViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        @BindView(R.id.event_detail_title)
        protected TextView mTitle;

        @BindView(R.id.event_detail_publish_date)
        protected TextView mPublishDate;

        @BindView(R.id.event_detail_several_dates_prefix)
        protected TextView mStartDateSeveralDatesPrefix;

        @BindView(R.id.event_detail_one_date_prefix)
        protected TextView mStartDateOneDatePrefix;

        @BindView(R.id.event_detail_end_date_prefix)
        protected TextView mEndDatePrefix;

        @BindView(R.id.event_detail_start_date)
        protected TextView mStartDate;

        @BindView(R.id.event_detail_end_date)
        protected TextView mEndDate;

        @BindView(R.id.event_detail_description)
        protected TextView mDescription;

        @BindView(R.id.event_detail_image)
        protected ImageView mImage;

        @BindView(R.id.event_detail_address)
        protected TextView mAddress;

        @BindView(R.id.event_detail_tags_container)
        protected FlowLayout mTagsContainer;

        private Event mEvent = null;
        private FragmentActivity mActivity;
        private FragmentManager mChildFragmentManager;

        @BindDimen(R.dimen.event_header_image_height)
        protected int mImageHeight;

        @Inject
        ImageLoader mImageLoader;

        public ViewHolder(View view, FragmentActivity activity, FragmentManager childFragmentManager) {
            super(view);
            mActivity = activity;
            this.mChildFragmentManager = childFragmentManager;
            ButterKnife.bind(this, view);
            ((LyonRewardsApplication) activity.getApplication()).getAppComponent().inject(this);
        }

        public void setEvent(Event event) {
            mEvent = event;

            // Image
            if (event.getImageUrl() != null && !event.getImageUrl().isEmpty()) {
                mImageLoader.loadImage(event.getImageUrl(), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        mImage.setImageBitmap(loadedImage);
                        mImage.setVisibility(View.VISIBLE);
                    }
                });
            }

            for (String tag : event.getTags()) {
                mTagsContainer.addView(new TagCardView(mActivity, tag));
            }


            mAddress.setText("Localisation : " + event.getAddress());

            // Map
            SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(this);

            FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.event_detail_mapview_container, mMapFragment);
            fragmentTransaction.commit();

            mTitle.setText(event.getTitle());
            mPublishDate.setText(event.getPublishDateString());

            mStartDate.setText(event.getStartDateString());
            if (event.getStartDate().equals(event.getEndDate())) {
                mStartDateOneDatePrefix.setVisibility(View.VISIBLE);
                mStartDateSeveralDatesPrefix.setVisibility(View.GONE);
                mEndDatePrefix.setVisibility(View.GONE);
                mEndDate.setVisibility(View.GONE);
            } else {
                mStartDateOneDatePrefix.setVisibility(View.GONE);
                mStartDateSeveralDatesPrefix.setVisibility(View.VISIBLE);
                mEndDatePrefix.setVisibility(View.VISIBLE);
                mEndDate.setVisibility(View.VISIBLE);
                mEndDate.setText(event.getEndDateString());
            }

            mDescription.setText(event.getDescription());
        }

        @Override
        public void onMapReady(GoogleMap map) {
            map.getUiSettings().setAllGesturesEnabled(false);

            LatLng centerLocation = new LatLng(mEvent.getLatitude(), mEvent.getLongitude());
            CameraUpdate center = CameraUpdateFactory.newLatLng(centerLocation);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

            map.moveCamera(center);
            map.animateCamera(zoom);

            map.addMarker(new MarkerOptions()
                    .position(centerLocation)
                    .title(mEvent.getTitle()));
        }
    }

    protected List<Event> events;
    private FragmentActivity mActivity;
    private FragmentManager mChildFragmentManager;

    public EventDetailDescriptionFragmentTabViewAdapter(List<Event> events, FragmentActivity activity, FragmentManager childFragmentManager) {
        this.events = events;
        this.mActivity = activity;
        this.mChildFragmentManager = childFragmentManager;
    }

    @Override
    public EventDetailDescriptionFragmentTabViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_detail_description, parent, false);
        return new ViewHolder(view, mActivity, mChildFragmentManager);
    }

    @Override
    public void onBindViewHolder(EventDetailDescriptionFragmentTabViewAdapter.ViewHolder holder, int position) {
        holder.setEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
