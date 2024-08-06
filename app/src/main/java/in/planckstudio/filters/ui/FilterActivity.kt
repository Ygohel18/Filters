package `in`.planckstudio.filters.ui

import `in`.planckstudio.filters.adapter.FilterAdapter
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import `in`.planckstudio.filters.MainActivity
import `in`.planckstudio.filters.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FilterActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var originalBitmap: Bitmap
    private lateinit var filterBitmap: Bitmap

    private lateinit var topAppBar: MaterialToolbar

    private lateinit var expandBtn: MaterialCardView
    private var isFitCenter = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
//        enableEdgeToEdge()

        imageView = findViewById(R.id.imageView)
        filterRecyclerView = findViewById(R.id.filter_recycler_view)
        topAppBar = findViewById(R.id.filterToolBar)

        val imageUri: Uri? = intent.getParcelableExtra("imageUri")
        if (imageUri != null) {
            displayImage(imageUri)
        } else {
            Toast.makeText(this, "Something goes wrong!", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
            finish()
        }

        expandBtn = findViewById(R.id.filter_expand_btn)
        expandBtn.setOnClickListener {
            toggleImageViewScaleType()
        }

        filterRecyclerView.layoutManager =
            LinearLayoutManager(this@FilterActivity, LinearLayoutManager.HORIZONTAL, false)

        filterRecyclerView.adapter =
            FilterAdapter(this@FilterActivity, originalBitmap) { filterBitmap ->
                this.filterBitmap = filterBitmap
                runOnUiThread {
                    Glide.with(this@FilterActivity)
                        .asBitmap()
                        .load(this.filterBitmap)
                        .into(imageView)
                }
            }

        topAppBar.setNavigationOnClickListener {
            finish()
        }

        topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.filterSave -> {
                    saveImage(imageView, this@FilterActivity, false)
                    true
                }

                R.id.filterShare -> {
                    saveImage(imageView, this@FilterActivity, true)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun displayImage(imageUri: Uri) {
        try {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }
            originalBitmap = bitmap
            this.filterBitmap = bitmap
            imageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getFilePathFromUri(context: Context, uri: Uri): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath
    }

    private fun saveUriToFile(uri: Uri): File {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val file = createTempFile()

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        return file
    }

    @Throws(IOException::class)
    private fun createTempFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = cacheDir
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun toggleImageViewScaleType(toggle: Boolean = true) {

        imageView.setImageBitmap(this.filterBitmap)

        if (!toggle) {
            if (isFitCenter) {
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            } else {
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        } else {
            if (isFitCenter) {
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            }
        }

        isFitCenter = !isFitCenter
    }

    private fun saveImage(imageView: ImageView, context: Context, share: Boolean = false) {
        // Get the bitmap from the ImageView
        val drawable = imageView.drawable as? BitmapDrawable ?: return
        var bitmap = drawable.bitmap

        // Check if fitCenter is false and center-crop if needed
        val imageViewWidth = imageView.width
        val imageViewHeight = imageView.height
//        val fitCenter = imageView.scaleType == ImageView.ScaleType.FIT_CENTER

        val fitCenter = this.isFitCenter

        if (!fitCenter) {
            val bitmapWidth = bitmap.width
            val bitmapHeight = bitmap.height

            // Calculate the scaling factor to fit the ImageView
            val scaleFactor = maxOf(
                imageViewWidth.toFloat() / bitmapWidth,
                imageViewHeight.toFloat() / bitmapHeight
            )

            // Calculate the new width and height of the bitmap
            val newWidth = (bitmapWidth * scaleFactor).toInt()
            val newHeight = (bitmapHeight * scaleFactor).toInt()

            // Center crop the bitmap
            val xOffset = (newWidth - imageViewWidth) / 2
            val yOffset = (newHeight - imageViewHeight) / 2

            val croppedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
            bitmap = Bitmap.createBitmap(
                croppedBitmap,
                xOffset,
                yOffset,
                imageViewWidth,
                imageViewHeight
            )
            croppedBitmap.recycle()
        }

        // Create a file in the external storage directory
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)

        try {
            // Save the bitmap as a JPEG file
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
            }

            // Add the image to the gallery (optional)
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, fileName, null)
            // Log success
            Log.d("SaveImage", "Image saved successfully/")

            Toast.makeText(
                this@FilterActivity,
                "Image saved successfully.",
                Toast.LENGTH_SHORT
            ).show()

            // If share is true, create a share intent
            if (share) {

                val imageUri = FileProvider.getUriForFile(
                    this,
                    "in.planckstudio.filters.provider",
                    file
                )

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/jpeg"
                intent.putExtra(Intent.EXTRA_STREAM, imageUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(Intent.createChooser(intent, "Share Image"))
            }
        } catch (e: IOException) {
            // Log the exception
            Log.e("SaveImage", "Failed to save image", e)
        }
    }
}