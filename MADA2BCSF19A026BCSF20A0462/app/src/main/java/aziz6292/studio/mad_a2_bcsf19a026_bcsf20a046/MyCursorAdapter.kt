package com

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CursorAdapter
import android.widget.TextView
import android.widget.Toast


class MyCursorAdapter(context: Context, cursor: Cursor?) :
    CursorAdapter(context, cursor, 0) {
    override fun getCount(): Int {
        return cursor.count
    }

    override fun newView(context: Context?, cursor: Cursor?, parent:
    ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.row_item,
            parent, false)
    }

    @SuppressLint("Range")
    override fun bindView(view: View, context: Context?, cursor: Cursor) {
        val nameTv = view.findViewById<View>(R.id.tv_name) as TextView
        val phoneNoTv = view.findViewById<View>(R.id.tv_phoneno) as TextView
        val updateBtn = view.findViewById<View>(R.id.btn_update) as Button
        val deleteBtn = view.findViewById<View>(R.id.btn_delete) as Button

        val id = cursor.getString(cursor.getColumnIndex(Utils.ID_COL)).toInt()
        val name = cursor.getString(cursor.getColumnIndex(Utils.NAME_COl))
        val phoneNo = cursor.getString(cursor.getColumnIndex(Utils.PHONENO_COL))
        if(name.isEmpty() && phoneNo.isEmpty()){
            Toast.makeText(context, "No data found",
            Toast.LENGTH_SHORT).show()
        }
        else{
            nameTv.text = name
            phoneNoTv.text = phoneNo.toString()
        }
        updateBtn.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(context,
                InsertUpdateContactActivity::class.java)
            intent.putExtra(Utils.ID, id)
            intent.putExtra(Utils.NAME, name)
            intent.putExtra(Utils.PHONENO, phoneNo)
            if (context != null) {
                context.startActivity(intent)
            }
        })
        deleteBtn.setOnClickListener(View.OnClickListener {
            if (context == null){
                return@OnClickListener
            }
            val db = DatabaseHelper(context, null)
            db.deleteContact(id)
            Utils.refreshList()
        })
    }
}


