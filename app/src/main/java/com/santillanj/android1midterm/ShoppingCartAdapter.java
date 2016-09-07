package com.santillanj.android1midterm;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jeremy on 9/3/2016.
 */
public class ShoppingCartAdapter extends ArrayAdapter<ShoppingCart>{

    private Context mContext;
    private int mResLayoutId;
    private List<ShoppingCart> mShoppingCart;

    public ShoppingCartAdapter(Context context, int resLayoutId, List<ShoppingCart> shoppy){
        super(context, resLayoutId, shoppy);

        mContext = context;
        mResLayoutId = resLayoutId;
        mShoppingCart = shoppy;
    }

    private class ViewHolder{

        TextView tvItem;
        TextView tvQuantity;
        TextView tvPrice;
        TextView tvTotal;


        public ViewHolder(View view){
            tvItem = (TextView) view.findViewById(R.id.tvItemName);
            tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvTotal = (TextView) view.findViewById(R.id.tvTotalPrice);

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(mResLayoutId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();

        }


        ShoppingCart shop = mShoppingCart.get(position);
        if (shop!=null) {
            if (holder.tvItem != null){
                holder.tvItem.setText("Item Name: "+shop.getmItem_name()+"");
            }
            if (holder.tvQuantity !=null){
                holder.tvQuantity.setText("Quantity: "+shop.getmQuantity()+"");

            }
            if (holder.tvPrice !=null){
                holder.tvPrice.setText("Price: "+(double) shop.getmPrice()+"");

            }
            if (holder.tvTotal !=null){
                holder.tvTotal.setText("Total Price: "+(double) shop.getmTotalPrice()+"");

            }


        }

        return convertView;
    }



}
