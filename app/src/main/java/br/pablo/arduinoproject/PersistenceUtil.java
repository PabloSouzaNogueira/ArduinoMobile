package br.pablo.arduinoproject;

import io.realm.RealmConfiguration;

public class PersistenceUtil {

    public static RealmConfiguration configurationRealm() {
        return new RealmConfiguration.Builder()
                .name("dblocal.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

}
