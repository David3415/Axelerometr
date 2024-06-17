package com.example.axelerometr.db

import DbCreateTableClass
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DbManager(context: Context) {           ////Удаление, считывание, добавление в БД
    val dbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    suspend fun insertToDb(gradus: String, comment: String, uri: String) = withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put(DbCreateTableClass.COL_NAME_GRADUS, gradus)
            put(DbCreateTableClass.COL_NAME_COMMENT, comment)
            put(DbCreateTableClass.COL_NAME_URI, uri)
        }
        db?.insert(DbCreateTableClass.TABLE_NAME, null, values)
    }

    suspend fun updateItem(gradus: String, comment: String, uri: String, id: Int) = withContext(Dispatchers.IO) {
        val selection = BaseColumns._ID + "=$id"
        val values = ContentValues().apply {
            put(DbCreateTableClass.COL_NAME_GRADUS, gradus)
            put(DbCreateTableClass.COL_NAME_COMMENT, comment)
            put(DbCreateTableClass.COL_NAME_URI, uri)
            put(DbCreateTableClass.COL_NAME_ID, id)
        }
        db?.update(DbCreateTableClass.TABLE_NAME, values, selection, null)
    }

    fun removeFromDb(id: String) {
        var sel = BaseColumns._ID + "=$id"
        db?.delete(DbCreateTableClass.TABLE_NAME, sel, null)
    }


    @SuppressLint("Range")
     suspend fun readDbData(): ArrayList<ListItem> = withContext(Dispatchers.IO)
    {
        openDb()
        val dataList = ArrayList<ListItem>()//лист из базы

        val cursor = db?.query(
            DbCreateTableClass.TABLE_NAME, null, null,
            null, null, null, null
        )

        while (cursor?.moveToNext()!!) {
            var dataTitle =
                cursor.getString(cursor.getColumnIndex(DbCreateTableClass.COL_NAME_COMMENT))
            val dataGradus =
                cursor.getString(cursor.getColumnIndex(DbCreateTableClass.COL_NAME_GRADUS))
            val dataUri = cursor.getString(cursor.getColumnIndex(DbCreateTableClass.COL_NAME_URI))
            val dataId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))

            var item = ListItem()
            item.gradus = dataGradus
            item.title = dataTitle
            item.uri = dataUri
            item.id = dataId

            dataList.add(item)
        }
        cursor.close()
        return@withContext dataList
    }

    fun closeDB() {
        dbHelper.close()
    }
}