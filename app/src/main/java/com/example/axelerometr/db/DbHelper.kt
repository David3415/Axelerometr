package com.example.axelerometr.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import DbCreateTableClass

class DbHelper(context:Context): SQLiteOpenHelper(context,DbCreateTableClass.TABLE_NAME,null,DbCreateTableClass.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL(DbCreateTableClass.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DbCreateTableClass.DELETE_TABLE)
        onCreate(db)
    }
}