/**
 * 
 */

package com.rainbowmobiles.databasehelpers;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.rainbowmobiles.suggest.DataMembers;

/**
 * @author Boopathy Balakrishnan
 * 
 * @version 1.0
 */
public class DBAdapter {
    private static final String DBAdapter_TAG = "dbadapterLogs";

    private final Context mContext;

    private DBHelper mDBHelper;

    private SQLiteDatabase mDB;

    public DBAdapter(Context mContext) {
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        /**
         * @param context
         */
        public DBHelper(Context context) {
            super(context, DataMembers.DB_NAME, null, DataMembers.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public boolean checkAndCopyDatabase() {

        boolean isDBExists = false;
        try {
            mDB = SQLiteDatabase.openDatabase(DataMembers.DB_PATH.concat(DataMembers.DB_NAME),
                    null, SQLiteDatabase.OPEN_READONLY);
            if (mDB != null)
                isDBExists = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(DBAdapter_TAG, "DB Exists : " + isDBExists);

        if (!isDBExists) {
            try {
                mDB = mDBHelper.getWritableDatabase();
                InputStream myInput = mContext.getAssets().open(DataMembers.DB_NAME);

                OutputStream myOutput = new FileOutputStream(
                        DataMembers.DB_PATH.concat(DataMembers.DB_NAME));
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mDB != null)
            mDB.close();
        return isDBExists;
    }

    public DBAdapter open() {
        try {
            mDB = mDBHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void close() {
        mDBHelper.close();
    }

    public List<MailModel> getStudentInformation() {
        List<MailModel> retrieveData = new ArrayList<MailModel>();
        String query = " SELECT * FROM " + DataMembers.TABLE_NAME;
        open();
        Cursor cursor = mDB.rawQuery(query, null);
        cursor.requery();
        if (cursor.moveToFirst()) {
            do {
            	MailModel info = new MailModel();
                info.setUserName(cursor.getString(1));
                info.setPassword(cursor.getString(2));
                info.setId(cursor.getInt(3));
                retrieveData.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        Log.i(DBAdapter_TAG, query);
        return retrieveData;
    }


}
