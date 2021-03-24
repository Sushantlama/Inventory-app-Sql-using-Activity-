package com.example.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import com.example.inventoryapp.data.Contract;
import com.example.inventoryapp.data.HelperDatabase;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Spinner mSpinner;

    EditText mTextName;

    EditText mNumberClass;

    EditText mNumberRollno;

    HelperDatabase mHelper;

    Button saveBtn;

    int Gender=0;

    ImageView avatar;

    String mode;

    Uri mUri;

    int id;

int requestcode=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);

        mHelper=new HelperDatabase(this);

        mSpinner=findViewById(R.id.gender);

        mTextName=findViewById(R.id.edt_name);

        mNumberClass=findViewById(R.id.edt_class);

        mNumberRollno=findViewById(R.id.edt_rollno);

        avatar=findViewById(R.id.avatar2);

        saveBtn=findViewById(R.id.save);




        mUri=  this.getIntent().getData();




        if (mUri== null){
            setTitle("save");
            mode= Contract.Entry.saveMode;

        }else {

            setTitle("edit");
            mode= Contract.Entry.editMode;
            LoaderManager.getInstance(this).initLoader(0,null,this);

        }

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mode==Contract.Entry.editMode){

                        Intent i=new Intent(EditorActivity.this,Avatar.class);
                        i.setData(ContentUris.withAppendedId(Contract.Entry.Students_Uri,ContentUris.parseId(mUri)));
                        startActivity(i);

                }
                else {

                        Intent i=new Intent(EditorActivity.this,Avatar2.class);
                        startActivityForResult(i,requestcode);

                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CheckItems()) {

                    switch (mode) {
                        case Contract.Entry.saveMode:
                            saveItem();

                            break;
                        case Contract.Entry.editMode:
                            updateItems();
                            finish();
                            break;

                    }
                }
            }
        });



        setUpSpinner();
    }
    public void saveItem(){

        insertItem();
        Intent i=new Intent(EditorActivity.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requestcode){
            if (resultCode==RESULT_OK){

                String returnedValue=data.getData().toString();
                id=Integer.parseInt(returnedValue);
                avatar.setImageResource(id);

            }
        }
    }

    public void insertItem(){
        String name=mTextName.getText().toString();
        int rollNo=Integer.parseInt(mNumberRollno.getText().toString());
        int Xlass=Integer.parseInt(mNumberClass.getText().toString());

        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.Entry.COLUMN_NAME,name);
        contentValues.put(Contract.Entry.COLUMN_CLASS,Xlass);
        contentValues.put(Contract.Entry.COLUMN_ROLLNO,rollNo);
        contentValues.put(Contract.Entry.COLUMN_GENDER,Gender);
        contentValues.put(Contract.Entry.COLUMN_IMAGE,id);

        getContentResolver().insert(Contract.Entry.CONTENT_URI,contentValues);
    }

    public void updateItems(){
        String name=mTextName.getText().toString();
        int rollNo=Integer.parseInt(mNumberRollno.getText().toString());
        int Xlass=Integer.parseInt(mNumberClass.getText().toString());

        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.Entry.COLUMN_NAME,name);
        contentValues.put(Contract.Entry.COLUMN_CLASS,Xlass);
        contentValues.put(Contract.Entry.COLUMN_ROLLNO,rollNo);
        contentValues.put(Contract.Entry.COLUMN_GENDER,Gender);

        getContentResolver().update(mUri,contentValues,null,null);
    }

    public boolean CheckItems(){
        return   TextUtils.isEmpty(mTextName.getText().toString())
              || TextUtils.isEmpty(mNumberRollno.getText().toString())
              ||TextUtils.isEmpty(mNumberClass.getText().toString())
              ||mSpinner.isSelected();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,mUri,null,null,null ,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()) {
            mTextName.setText(data.getString(data.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME)));
            mNumberClass.setText(String.valueOf(data.getInt(data.getColumnIndexOrThrow(Contract.Entry.COLUMN_CLASS))));
            mNumberRollno.setText(String.valueOf(data.getInt(data.getColumnIndexOrThrow(Contract.Entry.COLUMN_ROLLNO))));
            mSpinner.setSelection(data.getInt(data.getColumnIndexOrThrow(Contract.Entry.COLUMN_GENDER)));
            avatar.setImageResource(data.getInt(data.getColumnIndexOrThrow(Contract.Entry.COLUMN_IMAGE)));
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mTextName.setText("");
        mNumberClass.setText("");
        mNumberRollno.setText("");
        mSpinner.setSelection(0);
        avatar.setImageResource(R.drawable.first);
    }
    public void Alert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("do you want to save changes?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               if (!CheckItems()){
                   saveItem();}

            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });


        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Alert();
    }

    public void setUpSpinner(){

        ArrayAdapter genderAdapter=ArrayAdapter.createFromResource(this,R.array.gender,R.layout.support_simple_spinner_dropdown_item) ;
        genderAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinner.setAdapter(genderAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:Gender=0;break;
                    case 1:Gender=1;break;
                    case 2:Gender=2;break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }
}