package com.okay.demo.contentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 *@author wzj
 *@date 2022/3/22 11:20 上午
 */
class SqlDataBase(
    context: Context?,
    name: String?,
    version: Int
) : SQLiteOpenHelper(context, name, null, version) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Book (id string,name string)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}