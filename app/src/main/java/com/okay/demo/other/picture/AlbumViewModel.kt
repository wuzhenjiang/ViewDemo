package com.okay.demo.other.picture

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import java.io.File

/**
 *@author wzj
 *@date 2022/3/25 4:55 下午
 */
class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    fun getUriList(): MutableList<Uri> {
        val mutableList = mutableListOf<Uri>()
        val selection =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)" + " AND " + MediaStore.MediaColumns.SIZE + ">0"
        val cursor = getApplication<Application>().contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            null,
            selection,
            arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString()),
            "${MediaStore.MediaColumns.DATE_ADDED} desc"
        )
        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    val mimeTypeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)
                    val sizeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)
                    val pathIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
                    val size = cursor.getLong(sizeIndex)
                    // 图片大小不得小于 1 KB
                    if (size < 1024) {
                        continue
                    }

                    val type = cursor.getString(mimeTypeIndex)
                    val path = cursor.getString(pathIndex)
                    if (TextUtils.isEmpty(path) || TextUtils.isEmpty(type)) {
                        continue
                    }

                    val file = File(path)
                    if (!file.exists() || !file.isFile) {
                        continue
                    }

                    val parentFile = file.parentFile ?: continue
                    mutableList.add(uri)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }
        Log.e("AlbumViewModel","${mutableList.size}")
        return mutableList
    }

}