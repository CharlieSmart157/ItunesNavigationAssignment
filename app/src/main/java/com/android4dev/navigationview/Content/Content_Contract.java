package com.android4dev.navigationview.Content;

import com.android4dev.navigationview.BasePresenter;
import com.android4dev.navigationview.BaseView;
import com.android4dev.navigationview.model.Result;

import java.util.ArrayList;

/**
 * Created by Charlie on 17/06/2016.
 */
public interface Content_Contract {

    interface View extends BaseView<Presenter>{
    void setmAdapter(ArrayList<Result> L);

    }

    interface Presenter extends BasePresenter{

        void returnResults(int i);
    }
}
