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
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.inject.Inject;

/**
 * Created by Benoit on 29/04/2016.
 */

public class TrackerFragment extends Fragment implements SensorEventListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @BindView(R.id.travel_container)
    protected LinearLayout mTravelContainer;

    @BindView(R.id.current_moving_container)
    protected View mCurrentMovingTypeContainer;

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
    private TravelCardView mTravelBike;
    private TravelCardView mTravelBus;
    private TravelCardView mTravelTram;
    private TravelCardView mTravelWalk;

    private TravelCardView.TYPE mCurrentType = null;

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
            mCurrentMovingTypeContainer.setVisibility(View.GONE);
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
        //locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        pm = (PowerManager) getActivity().getApplicationContext().getSystemService(Service.POWER_SERVICE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        ButterKnife.bind(this, view);

        ((LyonRewardsApplication) getActivity().getApplication()).getAppComponent().inject(this);

        mTravelBike = new TravelCardView(getActivity(), TravelCardView.TYPE.BIKE, mConnectionManager.getConnectedUser());
        mTravelBus = new TravelCardView(getActivity(), TravelCardView.TYPE.BUS, mConnectionManager.getConnectedUser());
        mTravelTram = new TravelCardView(getActivity(), TravelCardView.TYPE.TRAM, mConnectionManager.getConnectedUser());
        mTravelWalk = new TravelCardView(getActivity(), TravelCardView.TYPE.WALK, mConnectionManager.getConnectedUser());

        mTravelContainer.addView(mTravelBike);
        mTravelContainer.addView(mTravelBus);
        mTravelContainer.addView(mTravelTram);
        mTravelContainer.addView(mTravelWalk);

        return view;
    }

    private void disableTracker() {
        mSensorManager.unregisterListener(this);

        stopLocationUpdates();

        if (wakelock.isHeld()) {
            wakelock.release();
        }
    }

    private void stopLocationUpdates() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    private boolean enableTracker() {

        //Ask user to enable location service
        try {
            int off = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (off == 0) {
                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(onGPS);
                Toast.makeText(getActivity(), "Veuillez activer la localisation pour l'analyse.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Settings.SettingNotFoundException e) {
            Toast.makeText(getActivity(), "Erreur : impossible d'activer la localisation.", Toast.LENGTH_SHORT).show();
        }

        // Create an instance of GoogleAPIClient.
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        //Start the accelerometer sensor
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mSensorManager.unregisterListener(this);
        //mSensorManager.registerListener(this, mAccelerometer, 5000000, 5000000);

        //Keep this fragment alive
        wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopLocationUpdates();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        LocationRequest locRequest = LocationRequest.create();
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locRequest.setInterval(1000);
        locRequest.setFastestInterval(200);

        // Ask permission for location
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(getActivity(), "Veuillez autoriser la géolocalisation pour l'analyse de déplacements.", Toast.LENGTH_LONG).show();
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locRequest, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // todo : check
        startLocationUpdates();
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

        if (location.hasSpeed()) {
            json.addProperty("speed", location.getSpeed());
        } else {
            json.addProperty("speed", -1);
        }

        json.addProperty("timestamp", location.getTime());

        mGpsJson.add(json);

        if (mNbGpsData >= MAX_NB_GPS_DATA) {
            JsonObject param = new JsonObject();
            param.add("gps", mGpsJson);
            param.add("accel", mAccJson);

            lyonRewardsApi.travel(mConnectionManager.getConnectedUser().getId(), param, new Callback<TravelData>() {
                @Override
                public void onResponse(Call<TravelData> call, Response<TravelData> response) {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        TravelData data = response.body();

                        Log.d("LOC", "API response : " + gson.toJson(response));

                        if (mCurrentType == null && data.getMode() != null) {
                            // // TODO: 02/05/2016

                            mCurrentMovingTypeContainer.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Log.d("API", "Error : " + response.message());
                    }



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
