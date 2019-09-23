package com.example.android.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public  static  final int DATABAE_VERSION=1;
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABAE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(notes.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + notes.TABLE_NAME);// drop older table if existed
        onCreate(db);//creating tables again
    }

    public long insertNote(String note){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(notes.COLUMN_NOTE,note);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        values.put(notes.COLUMN_TIMESTAMP,timestamp.toString());
        long id=db.insert(notes.TABLE_NAME,null,values);
        db.close();
        return  id;
}



public  notes  getNotes(long id)
{

  SQLiteDatabase db=this.getReadableDatabase();
    Cursor cursor = db.query(notes.TABLE_NAME,
            new String[]{notes.COLUMN_ID, notes.COLUMN_NOTE, notes.COLUMN_TIMESTAMP},
           notes.COLUMN_ID + "=?",
            new String[]{String.valueOf(id)}, null, null, null, null);
    if (cursor != null)
        cursor.moveToFirst();

    // prepare note object
    notes note = new notes(
            cursor.getInt(cursor.getColumnIndex(notes.COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(notes.COLUMN_NOTE)),
            cursor.getString(cursor.getColumnIndex(notes.COLUMN_TIMESTAMP)));

    cursor.close();

    return note;

}


public List<notes> getAllNotes(){
    List<notes> notes = new ArrayList<>();
String selectQuery="SELECT * FROM " + com.example.android.notes.notes.TABLE_NAME+" ORDER BY "+
        com.example.android.notes.notes.COLUMN_TIMESTAMP+" DESC";

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
        do {
            notes note = new notes();
            note.setId(cursor.getInt(cursor.getColumnIndex(com.example.android.notes.notes.COLUMN_ID)));
            note.setNote(cursor.getString(cursor.getColumnIndex(com.example.android.notes.notes.COLUMN_NOTE)));
            note.setTimestamp(cursor.getString(cursor.getColumnIndex(com.example.android.notes.notes.COLUMN_TIMESTAMP)));

            notes.add(note);
        } while (cursor.moveToNext());
    }

    db.close();


    return notes;

    }



    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + notes.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(notes note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(notes.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(notes.TABLE_NAME, values, notes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(notes note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(notes .TABLE_NAME, notes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }


}
