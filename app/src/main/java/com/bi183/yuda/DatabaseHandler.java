package com.bi183.yuda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    // membuat database dengan nama PerpustakaanOnline.db
    public DatabaseHandler(Context context) {
        super(context, "E-Book.db", null, 1);
    }

    // membuat tabel dengan nama buku dan terdapat 9 kolom data
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table buku(id integer primary key autoincrement, judul text not null, penulis text not null, penerbit text not null, tahun text not null, tebal text not null, isbn text not null, img blob not null)");
    }

    // upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists buku");
        onCreate(db);
    }

    // fungsi insert/create data
    public Boolean insertData(String judul, String penulis, String penerbit, String tahun, String tebal, String isbn, String imgLoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // convert data gambar menjadi byte
            FileInputStream fs = new FileInputStream(imgLoc);
            byte[] imgByte = new byte[fs.available()];
            fs.read(imgByte);
            // set nilai data yang akan disimpan
            ContentValues contentValues = new ContentValues();
            contentValues.put("judul", judul);
            contentValues.put("penulis", penulis);
            contentValues.put("penerbit", penerbit);
            contentValues.put("tahun", tahun);
            contentValues.put("tebal", tebal);
            contentValues.put("isbn", isbn);
            contentValues.put("img", imgByte);
            // insert data ke dalam database table buku
            db.insert("buku", null, contentValues);
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // fungsi update data dengan gambar
    public Boolean updateDataImage(Integer id, String judul, String penulis, String penerbit, String tahun, String tebal, String isbn, String imgLoc) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // convert data gambar menjadi byte
            FileInputStream fs = new FileInputStream(imgLoc);
            byte[] imgByte = new byte[fs.available()];
            fs.read(imgByte);
            // set nilai data yang akan disimpan
            ContentValues contentValues = new ContentValues();
            contentValues.put("judul", judul);
            contentValues.put("penulis", penulis);
            contentValues.put("penerbit", penerbit);
            contentValues.put("tahun", tahun);
            contentValues.put("tebal", tebal);
            contentValues.put("isbn", isbn);
            contentValues.put("img", imgByte);
            // update data ke dalam database table buku sesuai dengan id yang diberikan
            db.update("buku", contentValues, "id=?", new String[]{String.valueOf(id)});
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // fungsi update data tanpa gambar
    public Boolean updateDataText(Integer id, String judul, String penulis, String penerbit, String tahun, String tebal, String isbn) {
        SQLiteDatabase db = this.getWritableDatabase();
        // set nilai data yang akan disimpan
        ContentValues contentValues = new ContentValues();
        contentValues.put("judul", judul);
        contentValues.put("penulis", penulis);
        contentValues.put("penerbit", penerbit);
        contentValues.put("tahun", tahun);
        contentValues.put("tebal", tebal);
        contentValues.put("isbn", isbn);
        // update data ke dalam database table buku sesuai dengan id yang diberikan
        Integer isUpdate = db.update("buku", contentValues, "id=?", new String[]{String.valueOf(id)});
        if (isUpdate > 0) {
            return true;
        } else {
            return false;
        }
    }
    // fungsi delete data
    public Boolean deleteData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // hapus data di database sesuai dengan id yang diberikan
        Integer isDelete = db.delete("buku", "id=?", new String[]{String.valueOf(id)});
        if (isDelete > 0) {
            return true;
        } else {
            return false;
        }
    }

    // fungsi read data gambar dari database
    public Bitmap getImage(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        // mengambil data gambar dari database sesuai dengan id yang diberikan
        Cursor cursor = db.rawQuery("select * from buku where id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            byte[] img = cursor.getBlob(7);
            bt = BitmapFactory.decodeByteArray(img, 0, img.length);
        }
        return bt;
    }

    // fungsi read data text dari database
    public Cursor getBukuData(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // mengambil data judul, penulis, penerbit, tahun, tebal dan isbn dari database sesuai dengan id yang diberikan
        Cursor res = db.rawQuery("select judul, penulis, penerbit, tahun, tebal, isbn from buku where id=?", new String[]{String.valueOf(id)});
        return res;
    }

    // fungsi membuat arraylist dari semua data yang diambil pada database
    public ArrayList<Book> getList() {
        Book book = null;
        ArrayList<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from buku", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getBlob(7));
            bookList.add(book);
            cursor.moveToNext();
        }
        return bookList;
    }
}
