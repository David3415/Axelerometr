package com.example.axelerometr.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager(context: Context) {           ////Удаление, считывание, добавление в БД
    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
        db=dbHelper.writableDatabase
            }
    fun insertToDb(title: String, content: String) {
        val values = ContentValues().apply {
            put(DbCreateTableClass.COL_NAME_TITLE, title)
            put(DbCreateTableClass.COL_NAME_CONTENT, content)
        }
        db?.insert(DbCreateTableClass.TABLE_NAME, null, values)
    }

    fun readDbData(): ArrayList<String> {
        val dataList= ArrayList<String>()
        val cursor = db?.query(DbCreateTableClass.TABLE_NAME, null, null, null, null, null, null)

        with(cursor){
            while(this?.moveToNext()!!){
                val dataText=cursor?.getString(cursor.getColumnIndex(DbCreateTableClass.COL_NAME_TITLE))
                dataList.add(dataText.toString())
            }
        }
        return dataList
    }
    fun closeDB(){

    }
}