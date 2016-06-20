package com.android4dev.navigationview.injection.components;


import com.android4dev.navigationview.ContentFragment;
import com.android4dev.navigationview.injection.modules.APIModule;
import com.android4dev.navigationview.injection.scopes.UserScope;

import dagger.Component;


/**
 * Created by kalpesh on 20/01/2016.
 */

    @UserScope
    @Component(dependencies =NetComponent.class, modules = APIModule.class)
    public interface APIComponents {

    void inject(ContentFragment contentFragment);

}


