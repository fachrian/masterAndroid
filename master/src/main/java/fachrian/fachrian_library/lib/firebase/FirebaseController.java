package fachrian.fachrian_library.lib.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class FirebaseController {
    public static String getToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("token = "+token);
        return token;
//        if (token != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(token);
//                return jsonObject.get("token").toString();
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//        return null;
    }

    public static void setTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }
}
