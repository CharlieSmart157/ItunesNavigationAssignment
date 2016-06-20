package com.android4dev.navigationview.Realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android4dev.navigationview.model.Result;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Charlie on 17/06/2016.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm instance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(RealmResult.class);
        realm.commitTransaction();
    }

    //find all objects in the Result.class
    public RealmResults<RealmResult> getSongs() {

        return realm.where(RealmResult.class).findAll();
    }

    //query a single item with the given id
    public RealmResult getSong(int id) {

       // return realm.where(RealmResult.class).equalTo("trackId", id).findFirst();
        return realm.where(RealmResult.class).equalTo("trackId",id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasResults() {

        return !realm.allObjects(RealmResult.class).isEmpty();
    }


    public ArrayList<Result> getLocalList(){
        ArrayList<Result> n = new ArrayList<Result>();
        ArrayList<RealmResult>r = new ArrayList<RealmResult>();

        r.addAll(getSongs());
        for(int i=0;i<r.size();i++){
            Result a = new Result(r.get(i));
            n.add(a);
        }

        return n;
    }

    public void addLocalList(List<Result> L){
        for(int i=0; i < L.size();i++){
            RealmResult RResult = new RealmResult(L.get(i));
            if(RealmController.getInstance().getSong(RResult.getTrackId())==null)
                RResult.setDbID(RealmController.getInstance().getSongs().size());
                Log.i("ResultIterate", RResult.getTrackName());
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(RResult);
                realm.commitTransaction();


        }
    }

    //query example
    public RealmResults<RealmResult> queriedResults() {

        return realm.where(RealmResult.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
