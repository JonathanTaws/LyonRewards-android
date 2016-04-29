package h4311.hexanome.insa.lyonrewards.view.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;

public class EventDetailFragment extends Fragment {

    public static final String INTENT_EVENT = "h4311.hexanome.insa.lyonrewards.view.events.eventid";

    @BindView(R.id.sub_scrolltab_material_view_pager)
    protected MaterialViewPager mViewPager;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    private Event mEvent;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getFragmentTag() {
        return "EventDetailFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);
        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        if (getArguments() != null) {
            mEvent = getArguments().getParcelable(INTENT_EVENT);

            mViewPager.getViewPager().setAdapter(new EventDetailPagerAdapter(mEvent, getChildFragmentManager()));
            mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
            mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        }

        mViewPager.getToolbar().setVisibility(View.GONE);

        return view;
    }
}
