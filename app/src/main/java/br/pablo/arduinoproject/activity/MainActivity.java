package br.pablo.arduinoproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import br.pablo.arduinoproject.PersistenceUtil;
import br.pablo.arduinoproject.R;
import br.pablo.arduinoproject.Session;
import br.pablo.arduinoproject.adapter.NotificationAdapter;
import br.pablo.arduinoproject.model.Notification;
import br.pablo.arduinoproject.service.FirebaseSendTokenService;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.RV_notification)
    RealmRecyclerView RV_notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        sendTokenFirebase();


        Realm realm = Realm.getInstance(PersistenceUtil.configurationRealm());
        RealmResults<Notification> notifications = realm.where(Notification.class).sort("ordenation", Sort.DESCENDING).findAll();
        NotificationAdapter notificationAdapter = new NotificationAdapter(this, notifications, true, false);
        RV_notification.setAdapter(notificationAdapter);
    }



    private void sendTokenFirebase() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic("GENERAL");

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( this, instanceIdResult -> {
                String newToken = instanceIdResult.getToken();

                if (!newToken.equals("")) {
                    int userId = Session.sharedInstance().getUserID(MainActivity.this);
                    FirebaseSendTokenService.start(MainActivity.this, userId, newToken);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
