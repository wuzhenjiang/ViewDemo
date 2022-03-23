package com.okay.demo.other.picture

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.okay.demo.databinding.ActivityPhotoBinding
import java.io.File

/**
 *@author wzj
 *@date 2022/3/23 10:31 上午
 */
class PhotoActivity : AppCompatActivity() {
    private lateinit var rootView: ActivityPhotoBinding
    private val takePhoto = 1
    private val fromAlbum = 2
    private lateinit var imageUri: Uri
    private lateinit var outputImage: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(rootView.root)

        rootView.takePhoto.setOnClickListener {
            outputImage = File(externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //android24 7.0
                FileProvider.getUriForFile(
                    this,
                    "com.example.cameraalbumtest.fileprovider",
                    outputImage
                )
            } else {
                Uri.fromFile(outputImage)
            }
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }

        rootView.selectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent,fromAlbum)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap =
                        BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    rootView.imageview.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum ->{
                if(resultCode == Activity.RESULT_OK && data != null){
                    data.data?.let { uri ->
                        val bitmap = getBitmapFromUri(uri)
                        rootView.imageview.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri,"r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap{
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL)
        return when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitamp(bitmap,90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitamp(bitmap,180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitamp(bitmap,270)
            else -> bitmap
        }
    }

    private fun rotateBitamp(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
        bitmap.recycle()
        return rotatedBitmap
    }
}