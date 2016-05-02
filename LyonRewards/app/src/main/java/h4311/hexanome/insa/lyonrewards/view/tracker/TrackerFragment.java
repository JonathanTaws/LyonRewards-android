package h4311.hexanome.insa.lyonrewards.view.tracker;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import android.app.PendingIntent;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Benoit on 29/04/2016.
 */

public class TrackerFragment extends Fragment implements SensorEventListener, LocationListener {

    private SensorManager mSensorManager;
    private LocationManager locationManager;
    private WakeLock wakelock;
    private PowerManager pm;

    @OnCheckedChanged(R.id.switch_tracker)
    public void onCheckedChanged(boolean isChecked) {
        Switch switch_tracker = (Switch)getActivity().findViewById(R.id.switch_tracker);
        if(isChecked) {
            boolean tryLog = enableTracker();
            switch_tracker.setChecked(tryLog);
            if (switch_tracker.isChecked()) {
                showNotification();
            }
        } else {
            disableTracker();
            cancelNotification();
        }
    }

    public static TrackerFragment newInstance() {
        TrackerFragment fragment = new TrackerFragment();
        return fragment;
    }

    private void showNotification() {
        NotificationManager nMng = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, 0);

        Notification n  = new Notification.Builder(getActivity())
                .setContentTitle("Lyon Rewards")
                .setContentText("Analyse des déplacements en cours...")
                .setSmallIcon(R.drawable.lyon_rewards_logo_head)
                .setOngoing(true)
                .setContentIntent(contentIntent)
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
        //Init sensors to track
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        pm = (PowerManager) getActivity().getApplicationContext().getSystemService(Service.POWER_SERVICE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void disableTracker() {
        mSensorManager.unregisterListener(this);
        //Disable location updates only if permission was granted
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }
        if (wakelock.isHeld())
            wakelock.release();
    }

    private boolean enableTracker() {
        //Ask permission for location
         if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(getActivity(), "Veuillez autoriser la localisation pour l'analyse",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        //Ask user to enable location service
        try {
            int off = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if(off == 0) {
                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(onGPS);
                Toast.makeText(getActivity(), "Veuillez activer la localisation pour l'analyse",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch(Settings.SettingNotFoundException e) {
            Toast.makeText(getActivity(), "Erreur : impossible d'activer la localisation",
                    Toast.LENGTH_SHORT).show();
        }

        //Start the accelerometer sensor
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.unregisterListener(this);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        //Start the location sensor
        locationManager.removeUpdates(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000, -1, this);

        //Keep this fragment alive
        wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("ACC", "Update received : " + event.values[0] + " - " + event.values[1] + " - " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing here
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        Log.d("LOC", "update received : " + String.valueOf(location.getLatitude()) + " - " + String.valueOf(location.getLongitude()));
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
