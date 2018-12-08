package br.pablo.arduinoproject.model;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Notification implements RealmModel {

    @PrimaryKey
    private Integer notificationId;
    private String date;
    private Long ordenation;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getOrdenation() {
        return ordenation;
    }

    public void setOrdenation(Long ordenation) {
        this.ordenation = ordenation;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }
}
