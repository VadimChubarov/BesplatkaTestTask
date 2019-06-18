package com.example.besplatkatesttask.View.Support;


import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.besplatkatesttask.Data.Advert;
import com.example.besplatkatesttask.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertsAdapter extends RecyclerView.Adapter<AdvertsAdapter.RecyclerViewHolder> {

    private List<Advert> advertList = new ArrayList<>();
    private OnAdvertMenuClickListener onAdvertMenuClickListener;

    public AdvertsAdapter(OnAdvertMenuClickListener onAdvertMenuClickListener) {
      this.onAdvertMenuClickListener = onAdvertMenuClickListener;
    }

    public void setItems(Collection<Advert> items) {
        clearItems();
        advertList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Advert item) {
        advertList.add(0,item);
        notifyItemInserted(0);
    }

    public void updateItem(Advert advert){
            int position = advertList.indexOf(advert);
            if(advert.getId()==advertList.get(position).getId()){
                advertList.set(position,advert);
            }
    }

    public void removeItem(Advert advert){
        int position = advertList.indexOf(advert);
        if(advert.getId()==advertList.get(position).getId()){
            advertList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clearItems(){
        advertList.clear();
        notifyDataSetChanged();
    }

    public List<Advert> getAdvertList() {
        return advertList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advert_recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(advertList.get(position));
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            MenuItem.OnMenuItemClickListener onMenuItemClickListener = item -> {
                switch(item.getItemId()){
                    case 0:
                        onAdvertMenuClickListener.OnItemEditClick(advertList.get(position));
                        break;
                    case 1:
                        onAdvertMenuClickListener.OnItemDeleteClick(advertList.get(position));
                        break;
                }
                return false;
            };

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "edit advert");
                menu.add(0, 1, 0, "delete advert" );

                for(int i = 0; i < menu.size(); i++){
                    menu.getItem(i).setOnMenuItemClickListener(onMenuItemClickListener);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.advert_item_layout)
        LinearLayout itemLayout;
        @BindView(R.id.tv_advert_title)
        TextView title;
        @BindView(R.id.tv_advert_description)
        TextView description;
        @BindView(R.id.tv_advert_price)
        TextView price;
        @BindView(R.id.tv_city)
        TextView city;
        @BindView(R.id.tv_advert_seller_name)
        TextView name;
        @BindView(R.id.tv_advert_seller_phone)
        TextView phone;

        private List<String> colorList;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void bind(Advert advert) {
           title.setText(advert.getTitle());
           description.setText(advert.getDescription());
           price.setText(advert.getPrice());
           city.setText(advert.getLocation());
           name.setText(advert.getSellerName());
           phone.setText(advert.getSellerPhone());

           Random random = new Random();
           colorList = Arrays.asList(itemView.getResources().getStringArray(R.array.colors));
           itemLayout.setBackgroundColor(Color.parseColor(colorList.get(random.nextInt(colorList.size()))));
        }
    }

    public interface OnAdvertMenuClickListener {
        void OnItemEditClick(Advert advert);
        void OnItemDeleteClick(Advert advert);
    }
}
