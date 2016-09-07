package com.santillanj.android1midterm;

/**
 * Created by Jeremy on 9/3/2016.
 */
public class ShoppingCart {
    //itemName|||quantity|||price

    private String mItem_name;
    private int mQuantity;
    private double mPrice;
    private double mTotalPrice;

    public ShoppingCart() {

    }

    public ShoppingCart(String mItem_name, int mQuantity, double mPrice, double mTotalPrice) {
        this.mItem_name = mItem_name;
        this.mQuantity = mQuantity;
        this.mPrice = mPrice;
        this.mTotalPrice = mTotalPrice;
    }

    public String getmItem_name() {
        return mItem_name;
    }

    public void setmItem_name(String mItem_name) {
        this.mItem_name = mItem_name;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public double getmTotalPrice() {
        return mTotalPrice;
    }

    public void setmTotalPrice(double mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }
}
