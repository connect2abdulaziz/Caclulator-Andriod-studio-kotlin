package com

import android.database.Cursor

class Utils {

    companion object {
        val ID = "ID"
        val NAME = "NAME"
        val PHONENO = "PHONE NO"

        val DATABASE_NAME = "PHONE_BOOK"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "Contacts"
        val ID_COL = "_id"
        val NAME_COl = "Name"
        val PHONENO_COL = "PhoneNo"
        lateinit var myCursorAdapter:MyCursorAdapter
        lateinit var dbHelper:DatabaseHelper
        fun refreshList() {
            val cursor: Cursor?=dbHelper.readContacts()
            myCursorAdapter.changeCursor(cursor)
        }
    }
}