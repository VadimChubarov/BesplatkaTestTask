package com.example.besplatkatesttask.Data;

import java.io.Serializable;
import java.util.Objects;

public class Advert implements Serializable {

    private long id;
    private String title;
    private String description;
    private String price;
    private String location;
    private String sellerName;
    private String sellerPhone;

    public Advert() {
    }

    public Advert(long id,
                  String title,
                  String description,
                  String price,
                  String location,
                  String sellerName,
                  String sellerPhone) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advert)) return false;
        Advert advert = (Advert) o;
        return id == advert.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
