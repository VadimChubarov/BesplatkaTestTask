package com.example.besplatkatesttask.Presenter.Interfaces;

import com.example.besplatkatesttask.Data.Advert;

import java.util.List;

public interface OnModelEventListener {

    void onNewAdvertCreated(Advert advert);
    void onAllAdvertsResponse(List<Advert> advertList);
    void onAdvertUpdated(Advert advert);
    void onAdvertDeleted(Advert advert);
}
