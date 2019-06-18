package com.example.besplatkatesttask.View.Support;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.besplatkatesttask.Data.Advert;
import com.example.besplatkatesttask.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DialogAdvertAction extends DialogFragment {
    @LayoutRes
    private static final int LAYOUT_RES = R.layout.advert_action_dialog;

    @BindView(R.id.advert_action_dialog_layout)
    LinearLayout vgDialogLayout;
    @BindView(R.id.et_advert_title)
    EditText etAdvertTitle;
    @BindView(R.id.et_advert_description)
    EditText etAdvertDescription;
    @BindView(R.id.et_advert_price)
    EditText etAdvertPrice;
    @BindView(R.id.et_advert_seller_name)
    EditText etAdvertSellerName;
    @BindView(R.id.et_advert_seller_phone)
    EditText etAdvertSellerPhone;
    @BindView(R.id.et_city)
    EditText etAdvertCity;
    @BindView(R.id.btn_save_advert)
    Button BtnSaveAdvert;

    private Advert advert;
    private boolean isUpdate;
    private OnDialogClickListener onDialogClickListener;
    private Unbinder unbinder;

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT_RES, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(advert!=null){
            setUpAllFields(advert);
            isUpdate = true;
        }else {
            advert = new Advert();
        }
        return view;
    }

    private void setUpAllFields(Advert advert){
        etAdvertTitle.setText(advert.getTitle());
        etAdvertDescription.setText(advert.getDescription());
        etAdvertCity.setText(advert.getLocation());
        etAdvertPrice.setText(advert.getPrice());
        etAdvertSellerName.setText(advert.getSellerName());
        etAdvertSellerPhone.setText(advert.getSellerPhone());
    }

    private void saveAllFields(){
        advert.setTitle(etAdvertTitle.getText().toString());
        advert.setDescription(etAdvertDescription.getText().toString());
        advert.setPrice(etAdvertPrice.getText().toString());
        advert.setLocation(etAdvertCity.getText().toString());
        advert.setSellerName(etAdvertSellerName.getText().toString());
        advert.setSellerPhone(etAdvertSellerPhone.getText().toString());
    }

    private void resetAllFieldsColor(){
        for(int i = 0; i < vgDialogLayout.getChildCount(); i++){
            try{
                EditText editText = (EditText) vgDialogLayout.getChildAt(i);
                editText.getBackground().clearColorFilter();
            }
            catch(ClassCastException e){}
        }
    }

    private boolean isAllFieldsValid(){
        resetAllFieldsColor();
        boolean isValid = true;
        if(etAdvertTitle.getText().toString().isEmpty()){
            isValid = false;
            etAdvertTitle.setBackgroundColor(Color.RED);
        } else  etAdvertTitle.setBackgroundColor(Color.WHITE);

        if(etAdvertDescription.getText().toString().isEmpty()){
            isValid = false;
            etAdvertDescription.setBackgroundColor(Color.RED);
        } else etAdvertDescription.setBackgroundColor(Color.WHITE);

        if(!etAdvertPrice.getText().toString().matches("[0-9]+(\\.{1}[0-9]{1,2}){0,1}")){
            isValid = false;
            etAdvertPrice.setBackgroundColor(Color.RED);
        } else etAdvertPrice.setBackgroundColor(Color.WHITE);

        if(!etAdvertCity.getText().toString().matches("[A-Za-z А-Яа-я]+")){
            isValid = false;
            etAdvertCity.setBackgroundColor(Color.RED);
        }else  etAdvertCity.setBackgroundColor(Color.WHITE);

        if(!etAdvertSellerName.getText().toString().matches("[A-Za-z А-Яа-я]+")){
            isValid = false;
            etAdvertSellerName.setBackgroundColor(Color.RED);
        }else  etAdvertSellerName.setBackgroundColor(Color.WHITE);

        if(!etAdvertSellerPhone.getText().toString().matches("^\\+[1-9]{1}[0-9]{11,14}$")){
            isValid = false;
            etAdvertSellerPhone.setBackgroundColor(Color.RED);
        }else etAdvertSellerPhone.setBackgroundColor(Color.WHITE);

        return isValid;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(STYLE_NO_TITLE);
        return dialog;
    }

    @OnClick({R.id.btn_save_advert, R.id.btn_cancel})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_save_advert:
                if(isAllFieldsValid()){
                    saveAllFields();
                    if(onDialogClickListener!=null)
                        onDialogClickListener.onSaveAdvert(advert,isUpdate);
                    dismiss();
                }
                break;
        }
    }



    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public interface OnDialogClickListener {
        void onSaveAdvert(Advert advert, boolean isUpdate);
    }
}
