package com.example.inventoryapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Avatar2 extends AppCompatActivity {
    int id=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar2);
    }
    public void click(View view){
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
        Intent data=new Intent();
        String text=String.valueOf(id);
        data.setData(Uri.parse(text));
        setResult(RESULT_OK,data);
        finish();
    }
}
