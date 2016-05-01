/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package h4311.hexanome.insa.lyonrewards.di.module.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GcmListenerService;

import h4311.hexanome.insa.lyonrewards.R;
import h4311.hexanome.insa.lyonrewards.view.MainActivity;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    private static final String DATA_ID_OFFER = "id_offer";
    private static final String DATA_SCORE = "score";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

        Log.d(TAG, "Message: " + data.getString("score"));
        for (String key : data.keySet()) {
            Log.d(TAG, "Message: " + key);
        }

        String message = "todo";
        Log.d(TAG, "From: " + from);


        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        Intent messageReceived = new Intent(QuickstartPreferences.GCM_MESSAGE_RECEIVED);
// todo : set constant
        messageReceived.putExtra(MainActivity.ARG_ID_OFFER_READ, data.getString(DATA_ID_OFFER));
        messageReceived.putExtra(MainActivity.ARG_POINTS_OFFER_READ, data.getString(DATA_SCORE));
        LocalBroadcastManager.getInstance(this).sendBroadcast(messageReceived);

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(data.getString(DATA_ID_OFFER), data.getString(DATA_SCORE), data.getString("title"));
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     */
    private void sendNotification(String idOffer, String points, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.ARG_ID_OFFER_READ, idOffer);
        intent.setAction("TESTACT");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.lyon_rewards_logo);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.lyon_rewards_logo)
                .setLargeIcon(icon)
                .setContentTitle("Offre partenaire utilisée")
                .setContentText(title + " (- " + points + " points).")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .extend(new NotificationCompat.WearableExtender().setBackground(icon));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notif = notificationBuilder.build();

        // Hide the small icon in the right bottom corner
        int smallIconViewId = getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        if (smallIconViewId != 0) {
            if (notif.contentIntent != null)
                notif.contentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

            if (notif.headsUpContentView != null)
                notif.headsUpContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

            if (notif.bigContentView != null)
                notif.bigContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);
        }

        // todo : id notification
        notificationManager.notify(0, notif);
    }
}
