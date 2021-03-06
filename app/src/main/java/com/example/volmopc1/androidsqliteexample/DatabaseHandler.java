package com.example.volmopc1.androidsqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.version;

/**
 * Created by jitu on 1/17/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // code to add the new contact
    void addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[]{String.valueOf(2)});
    }

    // Deleting single contact
    public void deleteContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " = ?", new String[]{String.valueOf("Tommy")});
        db.close();
    }


    // code to get all contacts in a list view
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // code to get the single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tablename = TABLE_CONTACTS;
        String[] columns_to_return={KEY_ID, KEY_NAME, KEY_PH_NO};
        //new String[]{KEY_ID, KEY_NAME, KEY_PH_NO};
        String column_whereclause=KEY_ID + "=?";
        String[] value_whereclause={String.valueOf(id)};

        Cursor cursor = db.query(tablename, columns_to_return, column_whereclause, value_whereclause, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        Log.d("vvvv",cursor.getString(0));
        Log.d("vvvv",cursor.getString(1));
        Log.d("vvvv",cursor.getString(2));
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }



    Contact getallContact() {
        SQLiteDatabase db = this.getReadableDatabase();
        String tablename = TABLE_CONTACTS;
        String[] columns_to_return={KEY_ID, KEY_NAME, KEY_PH_NO};
        //new String[]{KEY_ID, KEY_NAME, KEY_PH_NO};
       // String column_whereclause=KEY_ID + "=?";
       // String[] value_whereclause={String.valueOf(id)};

        Cursor cursor = db.query(tablename, columns_to_return, null, null, null, null, null, null);
        for(int i=1;i<4;i++){
            if (cursor != null)
                cursor.moveToNext();
            Log.d("vvvv",cursor.getString(0));
            Log.d("vvvv",cursor.getString(1));
            Log.d("vvvv",cursor.getString(2));}
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }


}
