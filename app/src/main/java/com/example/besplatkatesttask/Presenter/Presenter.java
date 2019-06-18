package com.example.besplatkatesttask.Presenter;


import com.example.besplatkatesttask.Data.Advert;
import com.example.besplatkatesttask.Model.Interfaces.Model;
import com.example.besplatkatesttask.Model.Repository;
import com.example.besplatkatesttask.Presenter.Interfaces.OnModelEventListener;
import com.example.besplatkatesttask.Presenter.Interfaces.OnViewEventListener;
import com.example.besplatkatesttask.View.Interfaces.View;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Presenter implements OnModelEventListener, OnViewEventListener {

    private static Presenter presenter;
    private View view;
    private Model model;

    private Presenter (View view) {
        this.model = new Repository(view.getAppContext(),this);
        this.view = view;
        presenter = this;
    }

    public static Presenter getInstance(View view) {
        if(presenter==null) return new Presenter(view);
        else {
            presenter.setView(view);
            return presenter;
        }
    }

    public void setView(View view) {
        this.view = view;
    }



    // View events

    @Override
    public void onScreenRefreshed() {
        view.showDataLoading(true);
        model.readAllFromRemoteDb();
    }

    @Override
    public void onCreateNewAdvert(Advert advert) {
        model.writeToRemoteDb(advert);
    }

    @Override
    public void onUpdateAdvert(Advert advert) {
        Map<String,String> data = new LinkedHashMap<>();
        data.put("title",advert.getTitle());
        data.put("description",advert.getDescription());
        data.put("price",advert.getPrice());
        data.put("location",advert.getLocation());
        data.put("sellerName",advert.getSellerName());
        data.put("sellerPhone", advert.getSellerPhone());

        model.updateDataOnRemoteDb(String.valueOf(advert.getId()),data);
    }

    @Override
    public void onDeleteAdvert(Advert advert) {
        model.deleteDataOnRemoteDb(String.valueOf(advert.getId()));
    }



    // Model events

    @Override
    public void onAllAdvertsResponse(List<Advert> advertList) {
        view.showAllAdverts(advertList);
    }

    @Override
    public void onNewAdvertCreated(Advert advert) {
       view.showNewAdvert(advert);
    }

    @Override
    public void onAdvertUpdated(Advert advert) {
        view.refershAdvert(advert);
    }

    @Override
    public void onAdvertDeleted(Advert advert) {
       view.removeAdvert(advert);
    }
}
