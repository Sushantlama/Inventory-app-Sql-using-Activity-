package com.example.inventoryapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.security.PublicKey;

public class Contract  {


    public static final class Entry implements BaseColumns {
        public static final String TABLE_NAME="students";
        public static final String COLUMN_ID=BaseColumns._ID;
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_CLASS="class";
        public static final String COLUMN_ROLLNO="rollno";
        public static final String COLUMN_GENDER="gender";
        public static final String COLUMN_IMAGE="image";


        public static final String CONTENT_AUTHORITY="com.example.inventoryapp";
        public static final Uri BASE_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
        public static  final String PATH_STUDENT="students";
        public  static final Uri Students_Uri=Uri.parse("content://com.example.inventoryapp/students");
        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_URI,PATH_STUDENT);
        public static final String editMode="edit mode";
        public static final String saveMode="save mode";
    }

}
