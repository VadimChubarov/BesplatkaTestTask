package com.example.besplatkatesttask.Presenter.Interfaces;

import com.example.besplatkatesttask.Data.Advert;

public interface OnViewEventListener {

    void onScreenRefreshed();
    void onCreateNewAdvert(Advert advert);
    void onUpdateAdvert(Advert advert);
    void onDeleteAdvert(Advert advert);
}
