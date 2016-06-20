package com.android4dev.navigationview.utility;

import android.app.Application;

import com.android4dev.navigationview.injection.components.APIComponents;
import com.android4dev.navigationview.injection.components.DaggerAPIComponents;
import com.android4dev.navigationview.injection.components.DaggerNetComponent;
import com.android4dev.navigationview.injection.components.NetComponent;
import com.android4dev.navigationview.injection.modules.APIModule;
import com.android4dev.navigationview.injection.modules.AppModule;
import com.android4dev.navigationview.injection.modules.NetModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by kalpesh on 06/02/2016.
 */
public class MyApp extends Application {
    private NetComponent mNetComponent;
    private APIComponents mApiComponents;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent= DaggerNetComponent.builder()
                .netModule(new NetModule(constants.BASE_URL))
                .appModule(new AppModule(this))
                .build();

        mApiComponents= DaggerAPIComponents.builder()
                .netComponent(mNetComponent)
                .aPIModule(new APIModule())
                .build();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("SongRealm1.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public APIComponents getApiComponent() {
        return mApiComponents;
    }

}
