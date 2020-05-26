package com.ciwan.eventreminder.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.ciwan.eventreminder.models.Event;
import com.ciwan.eventreminder.models.Reminder;

import java.util.ArrayList;


public class EventDatabase extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "cDatabase";

    // Table name
    private static final String TABLE_EVENTS = "EventTable";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DETAIL = "detail";
    private static final String KEY_START_DATE = "startDate";
    private static final String KEY_END_DATE = "endDate";
    private static final String KEY_EVENT_TYPE = "eventType";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_RF = "rf";

    public EventDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_EVENTS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DETAIL + " TEXT,"
                + KEY_START_DATE + " TEXT,"
                + KEY_END_DATE + " TEXT,"
                + KEY_EVENT_TYPE + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_RF + " TEXT"
                + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new Reminder
    public int addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME , event.getName());
        values.put(KEY_DETAIL , event.getDetail());
        values.put(KEY_START_DATE , event.getStartDate());
        values.put(KEY_END_DATE , event.getEndDate());
        values.put(KEY_EVENT_TYPE , event.getEventType());
        values.put(KEY_ADDRESS, event.getAddress());
        values.put(KEY_RF, event.getRepeatFreq());

        // Inserting Row
        long ID = db.insert(TABLE_EVENTS, null, values);
        db.close();
        return (int) ID;
    }

    // Getting single Reminder
    public Reminder getEvent(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[]
                        {
                                KEY_ID,
                                KEY_NAME,
                                KEY_DETAIL,
                                KEY_START_DATE,
                                KEY_END_DATE,
                                KEY_EVENT_TYPE,
                                KEY_ADDRESS,
                                KEY_RF
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Reminder reminder = new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));

        return reminder;
    }

    // Getting all Reminders
    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> eventList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setName(cursor.getString(1));
                event.setDetail(cursor.getString(2));
                event.setStartDate(cursor.getString(3));
                event.setEndDate(cursor.getString(4));
                event.setEventType(cursor.getString(5));
                event.setAddress(cursor.getString(6));
                event.setRepeatFreq(cursor.getString(7));
                // Adding Reminders to list
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        return eventList;
    }

    // Getting Reminders Count
    public int getEventsCount(){
        String countQuery = "SELECT * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();

        return cursor.getCount();
    }

    // Updating single Reminder
    public int updateEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME , event.getName());
        values.put(KEY_DETAIL , event.getDetail());
        values.put(KEY_START_DATE , event.getStartDate());
        values.put(KEY_END_DATE , event.getEndDate());
        values.put(KEY_EVENT_TYPE , event.getEventType());
        values.put(KEY_ADDRESS, event.getAddress());
        values.put(KEY_RF, event.getRepeatFreq());

        // Updating row
        return db.update(TABLE_EVENTS, values, KEY_ID + "=?",
                new String[]{String.valueOf(event.getId())});
    }

    // Deleting single Reminder
    public void deleteEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + "=?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }

    public void deleteEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }
//TODO Delete reminder that has already passed
    //todo prevent user from selecting past date and time
    //todo custom ringtone for app
}

