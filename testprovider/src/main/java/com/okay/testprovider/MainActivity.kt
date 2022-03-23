package com.okay.testprovider

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import androidx.core.database.getStringOrNull
import com.okay.testprovider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity__"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            val uri = Uri.parse("content://com.okay.test/book")
            val contentValues =contentValuesOf("id" to "bookId" , "name" to "haha")
            contentResolver.insert(uri,contentValues)

            val contentValues2 =contentValuesOf("id" to "bookId2" , "name" to "haha2")
            contentResolver.insert(uri,contentValues2)
        }

        binding.query.setOnClickListener {
            val uri = Uri.parse("content://com.okay.test/book")
            val cursor = contentResolver.query(uri, null, null, null, null)
            while (cursor?.moveToNext() == true){
                val id = cursor.getStringOrNull(cursor.getColumnIndex("id"))
                val name = cursor.getStringOrNull(cursor.getColumnIndex("name"))
                Log.d(TAG,"$id  --- $name")
            }
            cursor?.close()
        }

        binding.update.setOnClickListener {
            val uri = Uri.parse("content://com.okay.test/book")
            val contentValues =contentValuesOf("name" to "qqqqq")
            contentResolver.update(uri, contentValues, "id = ?", arrayOf("bookId"))
        }

        binding.delete.setOnClickListener {
            val uri = Uri.parse("content://com.okay.test/book")
            contentResolver.delete(uri,"id = ?" , arrayOf("bookId"))
        }
    }
}