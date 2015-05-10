/**
 * 
 */
package com.rainbowmobiles.databasehelpers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rainbowmobiles.suggest.DataMembers;

/**
 * This class is used to copy database
 * 
 * @author Boopathy Balakrishnan
 * 
 * @version 1.0
 * 
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DBADAPTER_TAG = "dbadapterLogs";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * @param context
     * 
     */
    public DataBaseHelper(Context context) {
        super(context, DataMembers.DB_NAME, null, DataMembers.DB_VERSION);
        this.myContext = context;
        String myPackage = myContext.getApplicationContext().getPackageName();
        Log.i(DBADAPTER_TAG, myPackage);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {

            /*
             * 
             * By calling this method and empty database will be created into
             * the default system patof your application so we are gonna be able
             * to overwrite thatdatabase with our database.
             */
            this.getWritableDatabase();

            try {

                copyDataBase();

            } catch (Exception e) {

                throw new Error("Error copying database");

            }
        }
    }

    /**
     * 
     */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DataMembers.DB_NAME);

        // Path to the just created empty db
        String outFileName = DataMembers.DB_PATH + DataMembers.DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * @checkDB
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DataMembers.DB_PATH + DataMembers.DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            Log.i(DBADAPTER_TAG, myPath);
        } catch (SQLiteException e) {

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /*
     * This method is used to OPEN the database
     */
    public void openDataBase() {
        String myPath = DataMembers.DB_PATH + DataMembers.DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    /*
     * This method is used to CLOSE the database
     */
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }
}
