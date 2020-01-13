package com.example.schoolapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Admin on 04/06/2018.
 */

public class MyDBHandler extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RECORDDB.sqlite";
    public static final String TABLE_SCHOOL = "ecole";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_FORMATION = "form";
    public static final String COLUMN_SPEC = "spec";
    public static final String COLUMN_LOGO = "logo";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SCHOOL_TABLE = "CREATE TABLE " +
                TABLE_SCHOOL + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT, " + COLUMN_SPEC +" TEXT, "+ COLUMN_ADDRESS +" TEXT, "+ COLUMN_FORMATION + " TEXT, " + COLUMN_LOGO + " IMAGE " + ")";
        db.execSQL(CREATE_SCHOOL_TABLE);

    }

    public  void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    public  void insertData(String name,String address,String form, String spec,byte[] logo){
        SQLiteDatabase database = getWritableDatabase();
        String sql = " INSERT INTO "+ TABLE_SCHOOL +" VALUES(NULL,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();



        statement.bindString(1,name);
        statement.bindString(2,address);
        statement.bindString(3,form);
        statement.bindString(4,spec);
        statement.bindBlob(5,logo);


        statement.executeInsert();

    }
    public void  updateData(int id,String name,String address,String form, String spec,byte[] logo){
        SQLiteDatabase database = getWritableDatabase();

        String sql="UPDATE "+ TABLE_SCHOOL +" SET SchoolID=? ,Schoolname=?,SchoolAddress=?,SchoolForm=?,SchoolSpec=?," +
                "SchoolLogo=?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindDouble(1,(double) id);
        statement.bindString(2,name);
        statement.bindString(3,address);
        statement.bindString(4,form);
        statement.bindString(5,spec);
        statement.bindBlob(6,logo);

        statement.executeInsert();
        database.close();
    }

    public void deleteData(int id){
        SQLiteDatabase database=getWritableDatabase();
        String sql =" DELETE FROM " + TABLE_SCHOOL + " WHERE id=? ";

        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();

    }

    public Cursor getData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return  database.rawQuery(sql, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOL);
        onCreate(db);

    }

    public void addHandler(School school) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, school.get_id());
        values.put(COLUMN_NAME, school.getSchoolname());
        values.put(COLUMN_ADDRESS, school.getSchoolAddress());
        values.put(COLUMN_FORMATION, school.getSchoolForm());
        values.put(COLUMN_SPEC, school.getSchoolSpec());
        //values.put(COLUMN_LOGO, school.getSchoolLogo());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SCHOOL, null, values);
        db.close();
    }

    public School findHandler(String name) {
        String query = "Select * FROM " + TABLE_SCHOOL + " WHERE " +
                COLUMN_NAME + " = '" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        School school = new School();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            school.set_id(Integer.parseInt(cursor.getString(0)));
            school.setSchoolname(cursor.getString(1));
            school.setSchoolAddress(cursor.getString(2));
            school.setSchoolForm(cursor.getString(3));
            school.setSchoolSpec(cursor.getString(4));
          //  school.setSchoolLogo(cursor.getBlob(5));
            cursor.close();
        } else {
            school = null;
        }
        db.close();
        return school;
    }

    public String loadHandler() {
        String result = "";
        String query = "Select*FROM " + TABLE_SCHOOL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            String result_4 = cursor.getString(4);

            //byte[] result_6 = cursor.getBlob(6);
            result += String.valueOf(result_0) + " " + result_1 +" "+ result_2 +" "+result_3+""+ result_4+
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean deleteHandler(int ID) {

        boolean result = false;
        String query = "Select*FROM " + TABLE_SCHOOL + " WHERE " + COLUMN_ID + " = '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        School school = new School();
        if (cursor.moveToFirst()) {
            school.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_SCHOOL, COLUMN_ID + "=?",
                    new String[] {
                            String.valueOf(school.get_id())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int ID, String name, String add, String form,String spec/*, byte[] logo*/) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, name);
        args.put(COLUMN_ADDRESS, add);
        args.put(COLUMN_FORMATION, form);
        args.put(COLUMN_SPEC, spec);
        //args.put(COLUMN_LOGO, logo);
        return db.update(TABLE_SCHOOL, args, COLUMN_ID + "=" + ID, null) > 0;
    }
}