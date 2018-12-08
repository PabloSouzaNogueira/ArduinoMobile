package br.pablo.arduinoproject;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

public class ArduinoProjectApplication extends Application {

    private static ArduinoProjectApplication instanceApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Session.init(this);
        Realm.init(this);
        instanceApplication = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static synchronized ArduinoProjectApplication getInstance() {
        return instanceApplication;
    }
}
