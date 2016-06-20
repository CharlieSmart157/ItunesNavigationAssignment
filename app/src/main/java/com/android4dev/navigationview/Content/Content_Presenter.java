package com.android4dev.navigationview.Content;

import android.app.Activity;
import android.util.Log;

import com.android4dev.navigationview.Realm.RealmController;
import com.android4dev.navigationview.model.ListModel;
import com.android4dev.navigationview.model.Result;
import com.android4dev.navigationview.observables.IClassic_CatApi;
import com.android4dev.navigationview.observables.IPop_CatApi;
import com.android4dev.navigationview.observables.IPunk_CatApi;
import com.android4dev.navigationview.utility.constants;

import java.util.ArrayList;

import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Charlie on 17/06/2016.
 */
public class Content_Presenter implements Content_Contract.Presenter{

    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private final Content_Contract.View mContentView;
    //@Inject
    IClassic_CatApi iClassic_catApi;
    IPop_CatApi iPop_catApi;
    IPunk_CatApi iPunk_catApi;

    ArrayList<Result> results = new ArrayList<Result>();
    Activity mActivity;
    RestAdapter restapi;
    boolean connected;

    @Override
    public void start() {}

    public Content_Presenter (Content_Contract.View v){

    mContentView = v;
    mContentView.setPresenter(this);
         restapi = new RestAdapter.Builder()
                .setEndpoint(constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

    }

    public void classicPattern(){
        if(connected) {
            iClassic_catApi = restapi.create(IClassic_CatApi.class);
            _subscriptions.add(iClassic_catApi.getSongs()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<ListModel>() {
                        @Override
                        public void onCompleted() {
                            //Send Results to View
                            mContentView.setmAdapter(results);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ListModel listModel) {
                            results.clear();
                            Log.d("getResults", "" + listModel.getResults().size());
                            results.addAll(listModel.getResults());
                            Log.d("getResults2", "" + results.size());
                        }
                    })


            );
        }

        else
            mContentView.setmAdapter(RealmController.getInstance().getLocalList());

    }

    public void popPattern(){
        if(connected)
        {
        iPop_catApi = restapi.create(IPop_CatApi.class);
        _subscriptions.add(iPop_catApi.getSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ListModel>() {
                    @Override
                    public void onCompleted() {
                        //Send Results to View
                        mContentView.setmAdapter(results);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ListModel listModel) {
                        results.clear();
                        Log.d("getResults",""+ listModel.getResults().size());
                        results.addAll(listModel.getResults());
                        Log.d("getResults2",""+results.size());
                    }
                })


        );
    }
    else
            mContentView.setmAdapter(RealmController.getInstance().getLocalList());
    }

    public void punkPattern(){
        if(connected) {
            iPunk_catApi = restapi.create(IPunk_CatApi.class);
            _subscriptions.add(iPunk_catApi.getSongs()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<ListModel>() {
                        @Override
                        public void onCompleted() {
                            //Send Results to View
                            RealmController.getInstance().addLocalList(results);
                            mContentView.setmAdapter(results);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ListModel listModel) {
                            results.clear();
                            Log.d("getResults", "" + listModel.getResults().size());
                            results.addAll(listModel.getResults());
                            Log.d("getResults2", "" + results.size());
                        }
                    })


            );
        }
        else
        mContentView.setmAdapter(RealmController.getInstance().getLocalList());
    }

    @Override
    public void returnResults(int i, boolean b){
        connected = b;
        Log.i("ONLINE", ""+connected);
         switch (i) {
             case 0:
                 classicPattern();
                 break;
             case 1:
                 popPattern();
                 break;
             case 2:
                 punkPattern();
                 break;
         }
    }


}
