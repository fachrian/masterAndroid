package fachrian.fachrian_library.lib.firebase;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import fachrian.fachrian_library.lib.LogMaster;


/**
 * Created by Fachrian on 24/06/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        LogMaster.display(this, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LogMaster.display(this, "Message data payload: " + remoteMessage.getData());
            sendBroadcast(remoteMessage.getData().get("DATA"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LogMaster.display(this, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("TITLE")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void sendBroadcast(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.get("TARGET").toString().equals("GROUP")
                    && jsonObject.get("TIPE").toString().equals("DRIVER")) {

                if (jsonObject.get("STATUS").toString().equals("kejadian baru")) {
                    Intent i = new Intent("new_kejadian")
                            .putExtra("some_msg", "I will be sent!");
                    this.sendBroadcast(i);
                    sendNotification("Ada kejadian baru untuk segera ditangani");
                } else if (jsonObject.get("STATUS").toString().equals("tugas baru")) {
                    Intent i = new Intent("new_tugas")
                            .putExtra("some_msg", "I will be sent!");
                    this.sendBroadcast(i);
                    sendNotification("Ada tugas baru yang bisa diselesaikan");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}