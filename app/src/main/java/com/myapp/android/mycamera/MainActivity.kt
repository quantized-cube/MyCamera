package com.myapp.android.mycamera

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val _requestCodeEat = 1000
    private val _requestCodeEatResult = 100
    private val _requestCodeBody = 2000
    private val _requestCodeBodyResult = 200
    private val _requestCodeBang = 3000
    private val _requestCodeBangResult = 300
    private val _requestCodeMusic = 4000
    private val _requestCodeMusicResult = 400
    private var _imageUri: Uri? = null

    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == _requestCodeEatResult) {
                Toast.makeText(applicationContext, "Eat Saved", Toast.LENGTH_SHORT).show()
            } else if (requestCode == _requestCodeBodyResult) {
                Toast.makeText(applicationContext, "Body Saved", Toast.LENGTH_SHORT).show()
            } else if (requestCode == _requestCodeBangResult) {

//                val contentValues = ContentValues().apply {
//                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//                    put("_data", path)
//                }
//                contentResolver.insert(
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                Toast.makeText(applicationContext, "BanG Saved", Toast.LENGTH_SHORT).show()
            } else if (requestCode == _requestCodeMusicResult) {
                Toast.makeText(applicationContext, "Music Saved", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Unknown requestCode", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "Save Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun onEatButtonClick(view: View) {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, _requestCodeEat)
            return
        }
        Log.i("loglog", "permission")

        val fileName = makeFilename("eat")
        Log.i("loglog", "fileName")
        val values = ContentValues()
//        values.put(MediaStore.Audio.Media.RELATIVE_PATH, "DCIM/MyImages")
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
//        values.put(MediaStore.Images.Media.TITLE, fileName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.IS_PENDING, 1)
        Log.i("loglog", "Media")

//        val inputStream = FileInputStream(File(path))
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        ivCameraImage.setImageBitmap(bitmap)

//        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/mycamera/eat")
//        val storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM)!!
//        if (!storageDir.exists()) {
//            storageDir.mkdir()
//            Log.i("loglog", "mkdir")
//        }
        Log.i("loglog", "storageDir")
//        val file = File.createTempFile(
//            fileName, /* prefix */
//            ".jpg", /* suffix */
//            storageDir      /* directory */
//        )
//        val file = File(storageDir, fileName)
//        val file = File(getExternalFilesDir(Environment.DIRECTORY_DCIM), fileName)
        Log.i("loglog", "file")
//        path = file.absolutePath
        Log.i("loglog", "path")
//        values.put("_data", path)
        Log.i("loglog", "put _data")

//        val uriForFile = FileProvider.getUriForFile(this, "com.myapp.android.mycamera", file)

        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val item = contentResolver.insert(collection, values)!!

//        _imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)


        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, item)

        startActivityForResult(intent, _requestCodeEatResult)
    }

    fun onBodyButtonClick(view: View) {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, _requestCodeEat)
            return
        }



        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.myapp.android.mycamera.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, _requestCodeBodyResult)
                }
            }
        }

    }

    fun onBangButtonClick(view: View) {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, _requestCodeBang)
            return
        }

//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//        val storageDir =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/mycamera/BanG_Dream")
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM)!!
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        val fileName = makeFilename("bang_dream")
        val file = File(storageDir, fileName)
//        path = file.absolutePath
//        values.put("_data", path)
//        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        _imageUri = FileProvider.getUriForFile(
            this@MainActivity,
            getApplicationContext().getPackageName() + ".fileprovider",
            file
        )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri)
        startActivityForResult(intent, _requestCodeBangResult)
    }

    fun onMusicButtonClick(view: View) {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, _requestCodeMusic)
            return
        }

        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/mycamera/body")
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        val fileName = makeFilename("body")
        val file = File(storageDir, fileName)
        path = file.absolutePath
        values.put("_data", path)
        _imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri)
        startActivityForResult(intent, _requestCodeMusicResult)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == _requestCodeEat) {
                val btEat = findViewById<Button>(R.id.btEat)
                onEatButtonClick(btEat)
            } else if (requestCode == _requestCodeBody) {
                val btBody = findViewById<Button>(R.id.btBody)
                onBodyButtonClick(btBody)
            } else if (requestCode == _requestCodeBang) {
                val btBody = findViewById<Button>(R.id.btBang)
                onBangButtonClick(btBody)
            } else if (requestCode == _requestCodeMusic) {
                val btBody = findViewById<Button>(R.id.btMusic)
                onMusicButtonClick(btBody)
            }
        }
    }

    private fun makeFilename(pre: String): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_Hmmss")
        val now = Date()
        val nowStr = dateFormat.format(now)
        val fileName = "${pre}_${nowStr}.jpg"
        return fileName
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


}