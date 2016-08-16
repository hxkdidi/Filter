package com.kenos.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class DevAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> productList;
    private Context context;

    public DevAdapter(Context context, List<String> productList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList == null ? 0 : productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    ViewHolder holder;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String s = productList.get(i);

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item, viewGroup, false);
            holder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.image.setImageResource(R.mipmap.ic_launcher);

        return view;
    }

    private static class ViewHolder {
        ImageView image;
    }
}
