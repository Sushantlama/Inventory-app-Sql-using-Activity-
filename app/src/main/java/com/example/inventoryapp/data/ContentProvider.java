package com.example.inventoryapp.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContentProvider extends android.content.ContentProvider {
    private static final UriMatcher sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);

//    uri mather code
    private static final int STUDENT=100;
    private static final int STUDENT_ID=101;
    HelperDatabase mHelperdb;

    static {
//        "content://com.example.inventoryapp/students"
        sUriMatcher.addURI(Contract.Entry.CONTENT_AUTHORITY, Contract.Entry.PATH_STUDENT,STUDENT);
//        "content//com.example.inventoryapp/students/#"
        sUriMatcher.addURI(Contract.Entry.CONTENT_AUTHORITY, Contract.Entry.PATH_STUDENT+"/#",STUDENT_ID);
    }

    @Override
    public boolean onCreate() {
        mHelperdb=new HelperDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=mHelperdb.getReadableDatabase();
        int match=sUriMatcher.match(uri);
        Cursor cursor;
        switch (match){
            case STUDENT:
                cursor=db.query(Contract.Entry.TABLE_NAME,null,selection,selectionArgs,null,null,sortOrder);
                break;
            case STUDENT_ID:
                selection= Contract.Entry.COLUMN_ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=db.query(Contract.Entry.TABLE_NAME,null,selection,selectionArgs,null,null,sortOrder);
                break;
            default:throw new IllegalArgumentException("the uri is not valid "+uri);

        }
        //important for notifiaction change
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db=mHelperdb.getReadableDatabase();
        int match=sUriMatcher.match(uri);
        switch (match){
            case STUDENT:
                getContext().getContentResolver().notifyChange(uri,null);
                long id=db.insert(Contract.Entry.TABLE_NAME,null,values);
                break;
            default:throw new IllegalArgumentException("the uri is not valid "+uri);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db=mHelperdb.getWritableDatabase();
        //important for notification change
        getContext().getContentResolver().notifyChange(uri,null);
        return db.delete(Contract.Entry.TABLE_NAME,selection,selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db=mHelperdb.getWritableDatabase();
        selection= Contract.Entry.COLUMN_ID+"=?";
        selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
        getContext().getContentResolver().notifyChange(uri,null);
        return  db.update(Contract.Entry.TABLE_NAME,values,selection,selectionArgs);
    }
}
