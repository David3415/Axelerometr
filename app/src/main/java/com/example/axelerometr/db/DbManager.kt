package com.example.axelerometr.db


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


class DbManager(context: Context) {           ////Удаление, считывание, добавление в БД
    val dbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun insertToDb(title: String, content: String) {
        val values = ContentValues().apply {
            put(DbCreateTableClass.COL_NAME_TITLE, title)
            put(DbCreateTableClass.COL_NAME_CONTENT, content)
        }
        db?.insert(DbCreateTableClass.TABLE_NAME, null, values)
    }

    fun removeFromDb(id: String) {
        val sel = BaseColumns._ID
        db?.delete(DbCreateTableClass.TABLE_NAME, sel, null)
    }


    fun readDbData(): ArrayList<ListItem> {
        openDb()
        val dataList = ArrayList<ListItem>()//лист из базы

        val cursor = db?.query(
            DbCreateTableClass.TABLE_NAME, null, null,
            null, null, null, null
        )

        while (cursor?.moveToNext()!!) {

            var dataTitle =
                cursor.getString(cursor.getColumnIndex(DbCreateTableClass.COL_NAME_TITLE))
            val dataContent =
                cursor.getString(cursor.getColumnIndex(DbCreateTableClass.COL_NAME_CONTENT))
            val dataID =
                cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))


            var item = ListItem()
            item.id = dataID
            item.title = dataTitle
            item.desc = dataContent

            dataList.add(item)
        }
        cursor.close()
        return dataList
    }

    fun closeDB() {

    }
}