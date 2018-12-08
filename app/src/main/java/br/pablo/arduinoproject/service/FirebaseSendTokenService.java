package br.pablo.arduinoproject.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import br.pablo.arduinoproject.Session;

public class FirebaseSendTokenService  extends IntentService {

    public FirebaseSendTokenService() {
        super("FirebaseSendTokenService");
    }

    public static void start(Context context, int userID, String token) {
        try {
            Intent intent = new Intent(context, FirebaseSendTokenService.class);
            intent.putExtra("USER_ID", userID);
            intent.putExtra("TOKEN", token);
            context.startService(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            int userID = intent.getExtras().getInt("USER_ID");
            String token = intent.getExtras().getString("TOKEN");

            Session.sharedInstance().setUserID(this, userID);
            Session.sharedInstance().setToken(this, token);
        }
    }
}
