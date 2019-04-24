package com.example.teatime;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teatime.model.Tea;

import java.util.ArrayList;

/**
 * TeaMenuAdapter is backed by an ArrayList of {@link Tea} objects which populate
 * the GridView in MenuActivity
 */

public class TeaMenuAdapter extends ArrayAdapter<Tea> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();
    public TeaMenuAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Tea currentTea = getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) convertView.findViewById(R.id.tea_grid);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();;
        }

        holder.imageTitle.setText(currentTea.getTeaName());
        holder.image.setImageResource(currentTea.getImageResourceId());
        return convertView;
    }

}