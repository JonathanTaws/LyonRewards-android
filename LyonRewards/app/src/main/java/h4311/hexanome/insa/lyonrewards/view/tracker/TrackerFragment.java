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
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import h4311.hexanome.insa.lyonrewards.LyonRewardsApplication;
import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.business.TravelData;
import h4311.hexanome.insa.lyonrewards.di.module.api.LyonRewardsApi;
import h4311.hexanome.insa.lyonrewards.di.module.auth.ConnectionManager;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.PendingIntent;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.inject.Inject;

/**
 * Created by Benoit on 29/04/2016.
 */

public class TrackerFragment extends Fragment implements SensorEventListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @BindView(R.id.travel_container)
    protected LinearLayout mTravelContainer;

    @Inject
    protected LyonRewardsApi lyonRewardsApi;

    @Inject
    protected ConnectionManager mConnectionManager;

    private SensorManager mSensorManager;
    private LocationManager locationManager;
    private WakeLock wakelock;
    private PowerManager pm;

    private JsonArray mGpsJson = new JsonArray();
    private JsonArray mAccJson = new JsonArray();
    private int mNbGpsData = 0;

    private static int MAX_NB_GPS_DATA = 2;
    private GoogleApiClient mGoogleApiClient;

    @OnCheckedChanged(R.id.switch_tracker)
    public void onCheckedChanged(boolean isChecked) {
        Switch switch_tracker = (Switch) getActivity().findViewById(R.id.switch_tracker);
        if (isChecked) {
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

        Notification n = new Notification.Builder(getActivity())
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
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        pm = (PowerManager) getActivity().getApplicationContext().getSystemService(Service.POWER_SERVICE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        ButterKnife.bind(this, view);

        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        mTravelContainer.addView(new TravelCardView(getActivity(), TravelCardView.TYPE.BIKE));
        mTravelContainer.addView(new TravelCardView(getActivity(), TravelCardView.TYPE.BUS));
        mTravelContainer.addView(new TravelCardView(getActivity(), TravelCardView.TYPE.TRAM));
        mTravelContainer.addView(new TravelCardView(getActivity(), TravelCardView.TYPE.WALK));

        return view;
    }

    private void disableTracker() {
        mSensorManager.unregisterListener(this);
        //Disable location updates only if permission was granted
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //locationManager.removeUpdates(this);
        }

        stopLocationUpdates();
        if (wakelock.isHeld())
            wakelock.release();
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
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
            if (off == 0) {
                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(onGPS);
                Toast.makeText(getActivity(), "Veuillez activer la localisation pour l'analyse",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Settings.SettingNotFoundException e) {
            Toast.makeText(getActivity(), "Erreur : impossible d'activer la localisation",
                    Toast.LENGTH_SHORT).show();
        }

        //Start the accelerometer sensor
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mSensorManager.unregisterListener(this);
        //mSensorManager.registerListener(this, mAccelerometer, 5000000, 5000000);

        //Start the location sensor
        //locationManager.removeUpdates(this);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, -1, this);

        // Create an instance of GoogleAPIClient.
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        mGoogleApiClient.connect();



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
        // /!\ use : System.currentTimeMillis() and not  event.timestamp
        Log.d("ACC", "Update received : " + event.values[0] + " - " + event.values[1] + " - " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing here
    }


/*
    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }*/

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        Log.d("LOC", "StartLocationUpdates");
        LocationRequest locRequest = LocationRequest.create();
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locRequest.setInterval(1000);
        locRequest.setFastestInterval(200);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d("LOC", "StartLocationUpdates : request");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }
        Log.d("LOC", "update received : " + String.valueOf(location.getLatitude()) + " - " + String.valueOf(location.getLongitude()));
        mNbGpsData++;

        JsonObject json = new JsonObject();
        json.addProperty("latitude", location.getLatitude());
        json.addProperty("longitude", location.getLongitude());
        json.addProperty("accuracy", location.getAccuracy());
        json.addProperty("timestamp", location.getTime());
        mGpsJson.add(json);
        if (mNbGpsData >= MAX_NB_GPS_DATA) {
            JsonObject param = new JsonObject();
            param.add("gps", mGpsJson);
            param.add("accel", mAccJson);
            // Todo : API call

            lyonRewardsApi.travel(mConnectionManager.getConnectedUser().getId(), param, new Callback<TravelData>() {
                @Override
                public void onResponse(Call<TravelData> call, Response<TravelData> response) {
                    // todo : handle result and update ui
                }

                @Override
                public void onFailure(Call<TravelData> call, Throwable t) {
                    // todo : handle error
                    Log.d("API", "Error : " + t.getMessage());
                }
            });


            Log.d("LOC", "data : " + param.toString());
            mGpsJson = new JsonArray();
            mAccJson = new JsonArray();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("LOC", "connection failed : " + connectionResult.getErrorMessage());
    }
}
