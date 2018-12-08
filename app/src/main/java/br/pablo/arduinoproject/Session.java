package br.pablo.arduinoproject;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private Context context;
    private static Session instance = null;

    private Session(Context context) {
        this.context = context;
    }

    public static Session sharedInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new Session(context);
        }
    }

    public int getUserID(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("ArduinoProject", Context.MODE_PRIVATE);
        return sharedPref.getInt("userID", 0);
    }

    public void setUserID(Context context, int userID) {
        SharedPreferences sharedPref = context.getSharedPreferences("ArduinoProject", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userID", userID);
        editor.apply();
    }

    public String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("ArduinoProject", Context.MODE_PRIVATE);
        return sharedPref.getString("token","");
    }


    public void setToken(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("ArduinoProject", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

}
