package com.example.inventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventoryapp.data.Contract;
import com.example.inventoryapp.data.HelperDatabase;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor> {

    HelperDatabase mHelperdb;

    ListView listView;

    MyCursorAdapter mAdapter;

    ImageView img;
    LinearLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainlayout=findViewById(R.id.mainlayout);

        Button btn=findViewById(R.id.add);

        mHelperdb=new HelperDatabase(this);

        listView=findViewById(R.id.list_view);

        mAdapter=new MyCursorAdapter(this,null);

        listView.setAdapter(mAdapter);

        img=findViewById(R.id.empty);

        listView.setEmptyView(img);

        LoaderManager.getInstance(this).initLoader(0,null,this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,EditorActivity.class);
                intent.setData(ContentUris.withAppendedId(Contract.Entry.Students_Uri,id));
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_insert_dummy_data:
                insertItem();
                 return true;

            case R.id.action_delete_all:
               AlertDelete();
               return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void    insertItem(){

        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.Entry.COLUMN_NAME,"sushant");
        contentValues.put(Contract.Entry.COLUMN_CLASS,10);
        contentValues.put(Contract.Entry.COLUMN_ROLLNO,29);
        contentValues.put(Contract.Entry.COLUMN_GENDER,1);
        getContentResolver().insert(Contract.Entry.CONTENT_URI,contentValues);

    }


    public void deleteAll(){
    getContentResolver().delete(Contract.Entry.CONTENT_URI,null,null);
    }

    public void AlertDelete(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete item ?");
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });
       builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               deleteAll();
           }
       }) ;
       AlertDialog AlertBox=builder.create();
       AlertBox.show();

    }




    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, Contract.Entry.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    mAdapter.swapCursor(null);
    }
}
