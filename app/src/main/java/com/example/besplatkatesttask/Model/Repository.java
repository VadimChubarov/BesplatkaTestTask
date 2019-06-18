package com.example.besplatkatesttask.Model;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.besplatkatesttask.Data.Advert;
import com.example.besplatkatesttask.Model.Interfaces.Model;
import com.example.besplatkatesttask.Presenter.Interfaces.OnModelEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Repository implements Model {

    private DatabaseReference remoteDatabase;
    private OnModelEventListener onModelEventListener;

    public Repository(Context context, final OnModelEventListener onModelEventListener) {
        this.onModelEventListener = onModelEventListener;
        FirebaseApp.initializeApp(context);

        remoteDatabase = FirebaseDatabase.getInstance().getReference("advertTable");
        remoteDatabase.child("advertTable").addChildEventListener(new ChildEventListener() {
            // report to Presenter
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Advert advert = dataSnapshot.getValue(Advert.class);
               onModelEventListener.onNewAdvertCreated(advert);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Advert advert = dataSnapshot.getValue(Advert.class);
                onModelEventListener.onAdvertUpdated(advert);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Advert advert = dataSnapshot.getValue(Advert.class);
                onModelEventListener.onAdvertDeleted(advert);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void onReceivedData(List<Advert> data){
        onModelEventListener.onAllAdvertsResponse(data);
    }


    // functions available for Presenter

    public void writeToRemoteDb(Advert advert){
        long advertId = new Date().getTime();
        advert.setId(advertId);
        if(advertId!=0){
            remoteDatabase.child(String.valueOf(advertId)).setValue(advert);
        }
    }

    public void readFromRemoteDb(String id){
        remoteDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Advert advert = dataSnapshot.getValue(Advert.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("error "+error);
            }
        });
    }

    public void readAllFromRemoteDb(){
        remoteDatabase.orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        List<Advert> advertList = new ArrayList<>();
                        for(DataSnapshot data : dataSnapshot.getChildren()) {
                            advertList.add(data.getValue(Advert.class));
                        }
                        Collections.reverse(advertList);
                        onReceivedData(advertList);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                else
                    onReceivedData(null);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void updateDataOnRemoteDb(String id, Map<String ,String> data){
        for (Map.Entry<String, String> dataItem : data.entrySet()){
            remoteDatabase.child(id).child(dataItem.getKey()).setValue(dataItem.getValue());
        }
    }

    public void deleteDataOnRemoteDb(String id){
        remoteDatabase.child(id).removeValue();
    }
}

