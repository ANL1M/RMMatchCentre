package ru.anlm.rmmatchcentre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class SQLIteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rmMatchCentreDb";
    public static final String TABLE_RESULT = "result";

    public static final String KEY_ID = "_id";
    public static final String KEY_RESULT = "result";

    public SQLIteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_RESULT + "(" + KEY_ID
                + " integer primary key," + KEY_RESULT + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_RESULT);
        onCreate(db);
    }

    public void writeForDB(ArrayList<String> arrayList){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        database.delete(this.TABLE_RESULT, null, null);

        for (int i = 0; i < arrayList.size(); i++) {
            contentValues.put(KEY_RESULT, arrayList.get(i));
            database.insert(this.TABLE_RESULT, null, contentValues);
        }

        contentValues.clear();
        database.close();
    }

    public ArrayList<String> readForDB(){

        String myResult;
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(this.TABLE_RESULT, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            myResult = cursor.getString(1);
            arrayList.add(myResult);
        }

        cursor.close();
        database.close();

        return arrayList;
    }
}
