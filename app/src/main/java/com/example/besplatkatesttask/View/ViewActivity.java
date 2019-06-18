package com.example.besplatkatesttask.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.besplatkatesttask.Data.Advert;
import com.example.besplatkatesttask.Presenter.Interfaces.OnViewEventListener;
import com.example.besplatkatesttask.Presenter.Presenter;
import com.example.besplatkatesttask.R;
import com.example.besplatkatesttask.View.Support.AdvertsAdapter;
import com.example.besplatkatesttask.View.Support.DialogAdvertAction;

import java.io.Serializable;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ViewActivity extends AppCompatActivity implements DialogAdvertAction.OnDialogClickListener, com.example.besplatkatesttask.View.Interfaces.View {

    @BindView(R.id.rv_all_adverts)
    RecyclerView rvAllDaverts;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Unbinder unbinder;
    private OnViewEventListener onViewEventListener;
    private AdvertsAdapter advertsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setUpAdvertsRecycler();
        notifyPresenter(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("adapter_data",(Serializable) advertsAdapter.getAdvertList());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            List<Advert> advertList = (List<Advert>) savedInstanceState.getSerializable("adapter_data");
            if(advertList!=null){
              advertsAdapter.setItems(advertList);
            }
        }catch(Exception e){e.printStackTrace();}
    }

    private void notifyPresenter(Bundle state){
        onViewEventListener = Presenter.getInstance(this);
        if(state==null)onViewEventListener.onScreenRefreshed();
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    private void setUpAdvertsRecycler(){
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        rvAllDaverts.setLayoutManager(layoutManager);
        advertsAdapter = new AdvertsAdapter(new AdvertsAdapter.OnAdvertMenuClickListener() {
            @Override
            public void OnItemEditClick(Advert advert) {
               showUpdateAdvertAdvertScreen(advert);
            }

            @Override
            public void OnItemDeleteClick(Advert advert) {
               onDeleteAdvert(advert);
            }
        });
        rvAllDaverts.setAdapter(advertsAdapter);
    }

    private void createDialog(Advert advert){
        DialogAdvertAction dialogAdvertAction = new DialogAdvertAction();
        if(advert!=null){
            dialogAdvertAction.setAdvert(advert);
        }
        dialogAdvertAction.setOnDialogClickListener(this);
        dialogAdvertAction.show(getSupportFragmentManager(), DialogAdvertAction.class.getSimpleName());
    }


    // functions available for Presenter

    public void showAllAdverts(List<Advert> advertList){
       if(advertList!=null){
           advertsAdapter.setItems(advertList);
       }
       showDataLoading(false);
    }

    public void showCreateNewAdvertScreen(){
        createDialog(null);
    }

    public void showUpdateAdvertAdvertScreen(Advert advert){
        createDialog(advert);
    }

    @Override
    public void onSaveAdvert(Advert advert, boolean isUpdate) {
        if(isUpdate){
            onUpdateAdvert(advert);
        }else{
            onCreateNewAdvert(advert);
        }
    }

    public void showNewAdvert(Advert advert){
        advertsAdapter.addItem(advert);
    }

    public void refershAdvert(Advert advert){
        advertsAdapter.updateItem(advert);
    }

    public void removeAdvert(Advert advert){
        advertsAdapter.removeItem(advert);
    }

    public void showDataLoading(boolean show){
        if(show){
            rvAllDaverts.setVisibility(View.GONE);
            pbLoading.setVisibility(View.VISIBLE);
        }
        else{
            pbLoading.setVisibility(View.GONE);
            rvAllDaverts.setVisibility(View.VISIBLE);
        }
    }


    // report to Presenter

    public void onCreateNewAdvert(Advert advert){
        onViewEventListener.onCreateNewAdvert(advert);
    }

    public void onUpdateAdvert(Advert advert){
       onViewEventListener.onUpdateAdvert(advert);
    }

    public void onDeleteAdvert(Advert advert){
        onViewEventListener.onDeleteAdvert(advert);
    }



   @OnClick({R.id.btn_add_new_advert})
   void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_new_advert:
                showCreateNewAdvertScreen();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}


