package com.example.andres.myapplication.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.andres.myapplication.Persistence.DatabaseContract;

public class StudentsProvider extends ContentProvider {

    public static final int STUDENT_LIST = 1;
    public static final int STUDENT_ID = 2;

    private static final UriMatcher sUriMatcher;

    static{
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(StudentsContract.AUTHORITY, "students", STUDENT_LIST);
        sUriMatcher.addURI(StudentsContract.AUTHORITY, "students/#", STUDENT_ID);

    }

    private DatabaseContract.Students.StudentsDbHelper mDbHelper;
    private static final String DBNAME = "Students";



    public StudentsProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case STUDENT_LIST:
                return StudentsContract.CONTENT_TYPE;
            case STUDENT_ID:
                return StudentsContract.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DatabaseContract.Students.StudentsDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        boolean useAuthorityUri = false;

        switch (sUriMatcher.match(uri)){
            case STUDENT_LIST:
                builder.setTables(DatabaseContract.Students.TABLE_NAME);
                break;
            case STUDENT_ID:
                builder.setTables(DatabaseContract.Students.TABLE_NAME);
                builder.appendWhere(DatabaseContract.Students._ID + " = " +
                        uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }
        Cursor cursor =
        builder.query(
                db,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
