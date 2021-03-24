package com.example.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryapp.data.Contract;

public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return  LayoutInflater.from(context).inflate(R.layout.menu_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name=view.findViewById(R.id.txt_name);
        TextView roll=view.findViewById(R.id.txt_roll);
        TextView xlass=view.findViewById(R.id.txt_class);
        ImageView avatar=view.findViewById(R.id.avatar);


        name.setText(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME)));
        roll.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_ROLLNO))));
        xlass.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_CLASS))));
        avatar.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_IMAGE)));
        avatar.setTag(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_IMAGE)));
    }
}
