package com.okay.demo.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.net.Uri

class MyContentProvider : ContentProvider() {

    private var dbHelper: SqlDataBase? = null
    private val authorities: String = "com.okay.test"
    private val bookDir = 0
    private val bookItem = 1
//    private val categoryDir = 2
//    private val categoryItem = 3
    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authorities, "book", bookDir)
        matcher.addURI(authorities, "book/#", bookItem)
//        matcher.addURI(authorities, "category", categoryDir)
//        matcher.addURI(authorities, "category/#", categoryItem)
        matcher
    }

    override fun onCreate() = context?.let {
        dbHelper = SqlDataBase(it, "haha", 1)
        true
    } ?: false

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) =
        dbHelper?.let {
            val writableDatabase = it.writableDatabase
            when (uriMatcher.match(uri)) {
                bookDir -> {
                    writableDatabase.delete("Book", selection, selectionArgs)
                }
                bookItem -> {
                    val id = uri.pathSegments[0]
                    writableDatabase.delete("Book", "id = ?", arrayOf(id))
                }
                else -> 0
            }
        } ?: 0

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?) = dbHelper?.let {
        val writableDatabase = it.writableDatabase
        when (uriMatcher.match(uri)) {
            bookDir, bookItem -> {
                val bookId = writableDatabase.insert("Book", null, values)
                Uri.parse("content://$authorities/book/$bookId")
            }
            else -> null
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ) = dbHelper?.let {
        val writableDatabase = it.writableDatabase
        when (uriMatcher.match(uri)) {
            bookDir -> {
                writableDatabase.query(
                    "Book",
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            bookItem -> {
                val id = uri.pathSegments[0]
                writableDatabase.query("Book", null, "id = ?", arrayOf(id), null, null, null)
            }
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) =  dbHelper?.let{
        val writableDatabase = it.writableDatabase
        when (uriMatcher.match(uri)) {
            bookDir -> {
                writableDatabase.update("Book", values, selection,selectionArgs)
            }
            bookItem -> {
                val id = uri.pathSegments[0]
                writableDatabase.update("Book", values,"id = ?", arrayOf(id))
            }
            else -> 0
        }
    }?:0
}