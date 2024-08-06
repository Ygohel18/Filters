package `in`.planckstudio.filters

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import `in`.planckstudio.filters.ui.FilterActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(DelicateCoroutinesApi::class)
class MainActivity : AppCompatActivity() {

    private lateinit var mGalleryBtn: MaterialButton
    private lateinit var mCameraBtn: MaterialButton
    private lateinit var currentPhotoPath: String
    private lateinit var previewImage: ImageView

    private val REQUEST_IMAGE_PICK = 1001
    private val REQUEST_IMAGE_CAPTURE = 1002

    private val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allPermissionsGranted = permissions.values.all { it }
            if (allPermissionsGranted) {
                // Permissions granted, proceed with action
            } else {
                // Handle permission denied case
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mGalleryBtn = findViewById(R.id.filterGalleryBtn)
        mCameraBtn = findViewById(R.id.filterCameraBtn)
        previewImage = findViewById(R.id.holderSquareImg)

        Glide.with(this)
            .load(R.drawable.filter_preview)
            .into(previewImage)

        mGalleryBtn.setOnClickListener {
            if (checkPermissions()) {
                openGallery()
            } else {
                requestPermissions()
            }
        }

        mCameraBtn.setOnClickListener {
            if (checkPermissions()) {
                openCamera()
            } else {
                requestPermissions()
            }
        }

        previewImage.setOnClickListener {
            val baseUriString = getString(R.string.attribute_url_filter)

            val utmSource = "crafty_app"
            val utmMedium = "filter_activity"
            val utmCampaign = "user_engagement"

            val uriString =
                "$baseUriString?utm_source=$utmSource&utm_medium=$utmMedium&utm_campaign=$utmCampaign"
            val uri: Uri = Uri.parse(uriString)

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(Intent.createChooser(intent, title))
        }
    }

    private fun checkPermissions(): Boolean {
        return requiredPermissions.all { permission ->
            checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(requiredPermissions)
    }

    private fun openGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Handle error
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "${BuildConfig.APPLICATION_ID}.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val intent = Intent(this, FilterActivity::class.java)
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    data?.data?.let { uri ->
                        intent.putExtra("imageUri", uri)
                    }
                }

                REQUEST_IMAGE_CAPTURE -> {
                    val photoUri = FileProvider.getUriForFile(
                        this,
                        "${BuildConfig.APPLICATION_ID}.provider",
                        File(currentPhotoPath)
                    )
                    intent.putExtra("imageUri", photoUri)
                }
            }
            startActivity(intent)
        }
    }
}
