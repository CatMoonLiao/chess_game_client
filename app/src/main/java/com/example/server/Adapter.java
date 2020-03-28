package com.example.server;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    Context context;
    ArrayList<Chess> arr_item;

    public Adapter(Context context, ArrayList<Chess> arr_item){
        this.context=context;
        this.arr_item = arr_item;
    }

    @Override
    public View getView(int position, View view, ViewGroup vg){

        if(view==null){
            LayoutInflater a = LayoutInflater.from(context);
            view = a.inflate(R.layout.chess_item, null);
        }
        ImageView im=  view.findViewById(R.id.chess_image);
        Chess temp=arr_item.get(position);
        if(temp.type==-1)
            im.setImageResource(R.drawable.nothing);
        else if(temp.type==0)
            im.setImageResource(R.drawable.blackchess);
        else
            im.setImageResource(R.drawable.whitechess);
        return im;

    }

    @Override
    public int getCount() {   // item總數量
        return arr_item.size();
    }

    @Override
    public  Object getItem(int position){
        return  arr_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
