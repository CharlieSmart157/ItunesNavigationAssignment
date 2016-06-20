package com.android4dev.navigationview;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android4dev.navigationview.Content.Content_Contract;
import com.android4dev.navigationview.Content.Content_Presenter;
import com.android4dev.navigationview.Realm.RealmController;
import com.android4dev.navigationview.model.Result;
import com.android4dev.navigationview.model.ResultAdapter;
import com.android4dev.navigationview.observables.IClassic_CatApi;
import com.android4dev.navigationview.utility.MyApp;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Admin on 04-06-2015.
 */
public class ContentFragment extends Fragment implements Content_Contract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    IClassic_CatApi iClassic_catApi;
    Content_Presenter mPresenter;
    private RecyclerView lvSongList;
    private ResultAdapter mAdapter;
    private CompositeSubscription _subscriptions = new CompositeSubscription();
    ArrayList<Result> results = new ArrayList<Result>();
    Activity mActivity;
    int mode;
    boolean connected;
    SwipeRefreshLayout mSwipeRefresher;
    Realm realm;
    BroadcastReceiver broadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View v = inflater.inflate(R.layout.content_fragment,container,false);
        mSwipeRefresher = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefresher.setOnRefreshListener(this);
        ((MyApp) mActivity.getApplication()).getApiComponent().inject(this);
        mPresenter = new Content_Presenter(this);
        mode = getArguments().getInt("mode");
        this.realm = RealmController.with(getActivity()).getRealm();
       // realm = Realm.getInstance(getActivity());

        installListener();
        initializeRecyclerView(v);
       // classicPattern();
        return v;
    }



    public void initializeRecyclerView(View v){
        Log.d("INIT","initialising");
        lvSongList = (RecyclerView)v.findViewById(R.id.recycler_view);
        lvSongList.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvSongList.setItemAnimator(new DefaultItemAnimator());
        mPresenter.returnResults(mode);
    }

    public void setmAdapter(ArrayList<Result> L){

        mAdapter = new ResultAdapter(getContext(),L,R.layout.card_row );
        lvSongList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }



    @Override
    public void setPresenter(Content_Contract.Presenter presenter) {

    }

    @Override
    public void onRefresh() {
        Log.d("REFRESH_LOG", "onRefresh called from SwipeRefreshLayout");
        mPresenter.returnResults(mode);
       mSwipeRefresher.setRefreshing(false);

        Snackbar snackbar = Snackbar
                .make(mSwipeRefresher, "Refreshed", Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.show();


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void installListener() {

        if (broadcastReceiver == null) {

            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();


                    if (state == NetworkInfo.State.CONNECTED) {



                    } else {


                            Snackbar snackbar = Snackbar
                                    .make(mSwipeRefresher, "Offline", Snackbar.LENGTH_LONG)
                                    .setAction("Dismiss", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mSwipeRefresher.setRefreshing(true);
                                            onRefresh();
                                        }
                                    });
                            snackbar.show();


                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(broadcastReceiver,intentFilter);
        }
    }
}
