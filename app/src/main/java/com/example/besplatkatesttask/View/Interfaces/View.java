package com.example.besplatkatesttask.View.Interfaces;

import android.content.Context;

import com.example.besplatkatesttask.Data.Advert;

import java.util.List;

public interface View {

    void showAllAdverts(List<Advert> advertList);
    void showCreateNewAdvertScreen();
    void showUpdateAdvertAdvertScreen(Advert advert);
    void showDataLoading(boolean show);
    void showNewAdvert(Advert advert);
    void refershAdvert(Advert advert);
    void removeAdvert(Advert advert);
    Context getAppContext();
}
