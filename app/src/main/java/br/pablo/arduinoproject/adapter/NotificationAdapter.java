package br.pablo.arduinoproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.pablo.arduinoproject.R;
import br.pablo.arduinoproject.model.Notification;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class NotificationAdapter  extends RealmBasedRecyclerViewAdapter<Notification, RealmViewHolder> {

    public NotificationAdapter(Context context, RealmResults<Notification> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public RealmViewHolder onCreateRealmViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.adapter_notification, viewGroup, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindRealmViewHolder(@NonNull RealmViewHolder realmViewHolder, int position) {
        try {
            final Notification notification = realmResults.get(position);

            if (notification != null) {
                NotificationViewHolder holder = (NotificationViewHolder) realmViewHolder;

                holder.TV_date.setText(notification.getDate());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("ERROR", "NotificationAdapter - onBindRealmViewHolder");
        }
    }

    public class NotificationViewHolder extends RealmViewHolder {
        @BindView(R.id.TV_date)
        TextView TV_date;

        private NotificationViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
