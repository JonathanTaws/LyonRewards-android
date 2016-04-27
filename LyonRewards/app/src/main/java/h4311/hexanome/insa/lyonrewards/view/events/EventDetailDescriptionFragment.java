package h4311.hexanome.insa.lyonrewards.view.events;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.Event;


public class EventDetailDescriptionFragment extends Fragment {

    private static final String ARG_EVENT = "h4311.hexanome.insa.lyonrewards.view.events.ARG_EVENT";

    private Event mEvent;

    public EventDetailDescriptionFragment() {
        // Required empty public constructor
    }

    public static EventDetailDescriptionFragment newInstance(Event event) {
        EventDetailDescriptionFragment fragment = new EventDetailDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEvent = getArguments().getParcelable(ARG_EVENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail_description, container, false);
    }



}
