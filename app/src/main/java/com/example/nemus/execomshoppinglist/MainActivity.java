package com.example.nemus.execomshoppinglist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nemus.execomshoppinglist.db.*;
import com.example.nemus.execomshoppinglist.model.ShoppingListModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ShoppingListDbHelper dbHelper;
    private ListView slView;
    private ArrayAdapter<String> slAdapter;
    private EditText listNameEditText, listAmountEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dbHelper = new ShoppingListDbHelper(this);

        slView = (ListView) findViewById(R.id.list_items);



         updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add_list:
             //  final EditText listNameEditText = new EditText(this);
            //    final EditText listAmountEditText = new EditText(this);
                View myView = getLayoutInflater().inflate(R.layout.dialog_add_list, null);
                listNameEditText = (EditText) myView.findViewById(R.id.name);
                listAmountEditText = (EditText) myView.findViewById(R.id.amount);
                final ShoppingListModel shoppingListModel = new ShoppingListModel();
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("New Shopping List")
                        .setView(myView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                              //  shoppingListModel.setName(String.valueOf(listNameEditText.getText().toString()));
                                String listName = String.valueOf(listNameEditText.getText().toString());
                                if(listName.isEmpty()){
                                    makeToast("Enter name of list");
                                }else{
                                    shoppingListModel.setName(listName);
                                }

                                String amount = String.valueOf(listAmountEditText.getText().toString());
                                if(amount.isEmpty()){
                                    makeToast("Enter amount");
                                }else{
                                    shoppingListModel.setAmount(amount);
                                }
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(ShoppingListTable.COLUMN_NAME, listName);
                                values.put(ShoppingListTable.COLUMN_AMOUNT, amount);
                                db.insertWithOnConflict(ShoppingListTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                        dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateUI(){
       ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ShoppingListTable.TABLE_NAME,
                                new String[] {ShoppingListTable.COLUMN_NAME, ShoppingListTable.COLUMN_AMOUNT},
                                         null, null, null, null, null);

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(ShoppingListTable.COLUMN_NAME);
            taskList.add(cursor.getString(index));
        }

        if(slAdapter == null){
            slAdapter = new ArrayAdapter<String>(this, R.layout.item_list, R.id.product_name, taskList);
            slView.setAdapter(slAdapter);
        }else{
            slAdapter.clear();
            slAdapter.addAll(taskList);
            slAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.product_name);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ShoppingListTable.TABLE_NAME, ShoppingListTable.COLUMN_NAME + " = ?", new String[]{task});
        db.close();
        updateUI();
    }

  private void makeToast(String message){
      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
