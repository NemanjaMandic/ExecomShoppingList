package com.example.nemus.execomshoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by nemus on 21-Apr-17.
 */

public class ShoppingListAdapter extends CursorAdapter {
    public ShoppingListAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView amountTextView = (TextView) view.findViewById(R.id.product_amount);

        String nameColumn = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String amountColumn = cursor.getString(cursor.getColumnIndexOrThrow("amount"));

        nameTextView.setText(nameColumn);
        amountTextView.setText(amountColumn);
    }
}
