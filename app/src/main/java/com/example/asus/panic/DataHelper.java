package com.example.asus.panic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ASUS on 4/21/2018.
 */

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kontak.db";
    public  static final  String TABLE_NAME ="kontak";
    private static final int DATABASE_VERSION = 1;
    private static final String COLUMN_ID = "no";
    private static final String COLUMN_NAMA = "nama";
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table kontak(no integer primary key autoincrement, nama text null, phone text null);";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO kontak (nama, phone) VALUES ('admin', '087789064161');";
        sqLiteDatabase.execSQL(sql);
        String sql2 = "INSERT INTO kontak (nama, phone) VALUES ('superadmin', '081806102177');";
        sqLiteDatabase.execSQL(sql2);
        String sql3 = "INSERT INTO kontak (nama, phone) VALUES ('sales', '081806102177');";
        sqLiteDatabase.execSQL(sql3);
    }

    public List<Kontak> listKontak(){
        String sql = "select * from kontak" ;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Kontak> storeKontak = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String nama = cursor.getString(1);
                String phone = cursor.getString(2);
                storeKontak.add(new Kontak(id, nama, phone));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeKontak;
    }
    public void editkontak(String nama,String phone,Integer id){
//        ContentValues values_edit = new ContentValues();
//        values_edit.put("nama", kontak.getNama());
//        values_edit.put("phone", kontak.getPhone());
        SQLiteDatabase db = this.getWritableDatabase();
        //db.update(TABLE_NAME, values_edit, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(kontak.getId())});
        db.execSQL("UPDATE kontak SET nama='"+nama+"',phone='"+phone+"' WHERE no="+id);
        // db.update("kontak",values," no = ?", new String[] { String.valueOf(kontak.getId())});
    }
    public void addkontak(Kontak kontak){
        ContentValues values = new ContentValues();
        values.put("nama", kontak.getNama());
        values.put("phone", kontak.getPhone());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("kontak", null, values);
    }
    public void deleteKontak(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
