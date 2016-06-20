package com.android4dev.navigationview.observables;

import com.android4dev.navigationview.model.ListModel;
import com.android4dev.navigationview.utility.constants;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Charlie on 15/06/2016.
 */
public interface IClassic_CatApi  {

    @GET(constants.CLASSIC_URL)
    Observable<ListModel>getSongs();

}
