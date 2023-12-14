package com

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DatabaseHelper(context: Context, factory:
SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, Utils.DATABASE_NAME, factory,
        Utils.DATABASE_VERSION) {
    companion object{
    }
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + Utils.TABLE_NAME + "("
        + Utils.ID_COL + "INTEGER PRIMARY KEY AUTOINCREMENT," +
        Utils.NAME_COl + "TEXT," +
        Utils.PHONENO_COL + "TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, v1: Int, v2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME)
        onCreate(db)
    }
    fun addContact(name : String, phoneNo : String ): Long{
        val values = ContentValues()
        values.put(Utils.NAME_COl, name)
        values.put(Utils.PHONENO_COL, phoneNo)

        val db = this.writableDatabase
        val v = db.insert(Utils.TABLE_NAME, null, values)
        db.close()
        return v
    }
    fun updateContact(id: Int, name : String, phoneNo : String ){
        val values = ContentValues()
        values.put(Utils.NAME_COl, name)
        values.put(Utils.PHONENO_COL, phoneNo)
        val db = this.writableDatabase
        db.update(Utils.TABLE_NAME, values, Utils.ID_COL+"=?",
        arrayOf(id.toString()))
        db.close()
    }
    fun deleteContact(id: Int){
        val db = this.writableDatabase
        db.delete(Utils.TABLE_NAME, Utils.ID_COL+"=?",
        arrayOf(id.toString()))
        db.close()
    }
    fun readContacts(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + Utils.TABLE_NAME, null)
    }
}
