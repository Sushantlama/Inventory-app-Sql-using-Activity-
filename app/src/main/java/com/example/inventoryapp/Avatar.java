package com.example.inventoryapp;


import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inventoryapp.data.Contract;
import com.example.inventoryapp.data.HelperDatabase;

public class Avatar extends AppCompatActivity {

    Integer id=0;
    HelperDatabase mHelperdb;
    Uri mUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatars);
        mHelperdb=new HelperDatabase(this);




    }
    public void setAvatar(View view){
        String  tag= (String) view.getTag();

        switch (tag){
            case "first":id=R.drawable.first;break;
            case "second":id=R.drawable.second;break;
            case "third":id=R.drawable.third;break;
            case "four":id=R.drawable.forth;break;
            case "five":id=R.drawable.fifth;break;
            case "six":id=R.drawable.sixth;break;
            case "seven":id=R.drawable.seven;break;
            case "eight":id=R.drawable.eight;break;
            case "nine":id=R.drawable.nine;break;
            case "ten":id=R.drawable.ten;break;
            case "eleven":id=R.drawable.eleven;break;
            case "twelve":id=R.drawable.twelve;break;
            case "thirteen":id=R.drawable.thirteen;break;
            case "fourteen":id=R.drawable.fourteen;break;
            case "fifteen":id=R.drawable.fifteen;break;
            case "sixteen":id=R.drawable.sixteen;break;
            case "seventeen":id=R.drawable.seventeen;break;
            case "eighteen":id=R.drawable.eighteen;break;
            case "nineteen":id=R.drawable.nineteen;break;
            case "twenty":id=R.drawable.twenty;break;

        }


            mUri=this.getIntent().getData();
            update();
            finish();


    }
    public void update(){
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.Entry.COLUMN_IMAGE,id);
        getContentResolver().update(mUri,contentValues,null,null);
        }
    }



