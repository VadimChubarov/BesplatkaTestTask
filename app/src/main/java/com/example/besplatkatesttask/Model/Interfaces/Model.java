package com.example.besplatkatesttask.Model.Interfaces;

import com.example.besplatkatesttask.Data.Advert;

import java.util.Map;

public interface Model {
    void writeToRemoteDb(Advert advert);
    void readFromRemoteDb(String id);
    void readAllFromRemoteDb();
    void updateDataOnRemoteDb(String id, Map<String ,String> data);
    void deleteDataOnRemoteDb(String id);
}
