package h4311.hexanome.insa.lyonrewards.view.tracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Benoit on 29/04/2016.
 */

public class TrackerFragment extends Fragment {

    @OnCheckedChanged(R.id.switch_tracker)
    public void onCheckedChanged(boolean isChecked) {
        if(isChecked)
            showNotification();
        else
            cancelNotification();
    }

    public static TrackerFragment newInstance() {
        TrackerFragment fragment = new TrackerFragment();
        return fragment;
    }

    private void showNotification() {
        NotificationManager nMng = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n  = new Notification.Builder(getActivity())
                .setContentTitle("Lyon Rewards")
                .setContentText("Analyse des déplacements en cours...")
                .setSmallIcon(R.drawable.lyon_rewards_logo_head)
                .setOngoing(true)
                .build();
        nMng.notify(1337, n);
    }

    private void cancelNotification() {
        NotificationManager nMng = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        nMng.cancel(1337);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
