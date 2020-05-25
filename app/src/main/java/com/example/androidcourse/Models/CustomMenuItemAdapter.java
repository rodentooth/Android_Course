package com.example.androidcourse.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidcourse.R;

import java.util.List;

// Credits: https://stackoverflow.com/questions/15762905/how-can-i-display-a-list-view-in-an-android-alert-dialog
public class CustomMenuItemAdapter extends ArrayAdapter<MenuItem> {
        private final Context context;
        private final List<MenuItem> listItems;

    public CustomMenuItemAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        Log.d("Debug",  "Size "+  objects.size());
        this.listItems = objects;
        Log.d("Debug",  "Size "+  this.listItems.size());

        this.context = context;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_dialog_listitem_layout, parent, false);
        ImageView profilePic = rowView.findViewById(R.id.icon_image);
        TextView itemText = rowView.findViewById(R.id.item_name);
        Log.d("Debug", " Item "+ listItems.get(position));
        MenuItem powerUpItem = (MenuItem) listItems.get(position);

        itemText.setText(powerUpItem.toString());

        Bitmap bitmap = powerUpItem.item.icon;

        profilePic.setImageDrawable(new BitmapDrawable(context.getResources(), bitmap));

        return rowView;
    }
}
