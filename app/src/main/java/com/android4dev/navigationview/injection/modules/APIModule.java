package com.android4dev.navigationview.injection.modules;


import com.android4dev.navigationview.injection.scopes.UserScope;
import com.android4dev.navigationview.observables.IClassic_CatApi;
import com.android4dev.navigationview.observables.IPop_CatApi;
import com.android4dev.navigationview.observables.IPunk_CatApi;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

;

@Module
public class APIModule {

    @Provides
    @UserScope
    public IClassic_CatApi providesIClassic_CatInterface(RestAdapter retrofit) {
        return retrofit.create(IClassic_CatApi.class);
    }

    @Provides
    @UserScope
    public IPop_CatApi providesIPop_CatInterface(RestAdapter retrofit) {
        return retrofit.create(IPop_CatApi.class);
    }

    @Provides
    @UserScope
    public IPunk_CatApi providesIPunk_CatInterface(RestAdapter retrofit) {
        return retrofit.create(IPunk_CatApi.class);
    }
}
