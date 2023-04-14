package com.mobdeve.s11.salangsang.brian.smartfridge

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mobdeve.s11.salangsang.brian.smartfridge.databinding.AddFoodDataBinding
import com.squareup.picasso.Picasso
import java.util.concurrent.Executors

class AddFoodData : AppCompatActivity() {

    private val executorService = Executors.newSingleThreadExecutor()

    private val CAMERA_REQUEST_CODE = 1

    private lateinit var viewBinding : AddFoodDataBinding
    private lateinit var myDbHelper: MyDBHelper
    private var imageUri: Uri? = null


    private val myActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            try {
                if (result.data != null) {
                    imageUri = result.data!!.data
                    Picasso.get().load(imageUri).into(viewBinding.tempImageIv)
                }
            } catch (exception: Exception) {
                Log.d("TAG", "" + exception.localizedMessage)
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            // There are no request codes
            // viewBinding.tempImageIv.load(bitmap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){

            when(requestCode){

                CAMERA_REQUEST_CODE->{

                    val bitmap = data?.extras?.get("data") as Bitmap

                    //coroutine image loader (coil)
                    viewBinding.tempImageIv.load(bitmap)
                }
            }
        }
    }







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = AddFoodDataBinding.inflate(layoutInflater)
        setContentView(this.viewBinding.root)

        //gallery access
        this.viewBinding.imageBtn.setOnClickListener(View.OnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_OPEN_DOCUMENT
            myActivityResultLauncher.launch(Intent.createChooser(i, "Select Picture"))
        })

        //camera access
        this.viewBinding.cameraBtn.setOnClickListener(View.OnClickListener {

            cameraCheckPermission()

        })



        this.viewBinding.saveBtn.setOnClickListener(View.OnClickListener { view ->
            if (areFieldsComplete()) {
                executorService.execute {
                    myDbHelper = MyDBHelper.getInstance(this@AddFoodData)!!
                    myDbHelper.insertFood(
                        Food(
                            this.viewBinding.foodNameEtv.text.toString(),
                            this.viewBinding.foodDescEtv.text.toString(),
                            this.viewBinding.foodExpEtv.text.toString(),
                            imageUri.toString()
                        )
                    )
                }

                val i = Intent()
                i.putExtra(IntentKeys.FOOD_NAME_KEY.name, this.viewBinding.foodNameEtv.text.toString())
                i.putExtra(IntentKeys.FOOD_DESCRIPTION_KEY.name, this.viewBinding.foodDescEtv.text.toString())
                i.putExtra(IntentKeys.FOOD_EXPIRY_KEY.name, this.viewBinding.foodExpEtv.text.toString())
                i.putExtra(IntentKeys.IMAGE_URI_KEY.name, imageUri.toString())
                setResult(RESULT_OK, i)

                finish()
            } else {
                Toast.makeText(view.context, "Please fill up all fields", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun cameraCheckPermission() {

        Dexter.withContext(this)
                .withPermissions(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA).withListener(

                        object : MultiplePermissionsListener{
                            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                                report?.let {

                                    if(report.areAllPermissionsGranted()){
                                        camera()
                                    }
                                }
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                p0: MutableList<PermissionRequest>?,
                                p1: PermissionToken?) {

                                showRotationalDialogForPermission()
                            }
                        }
                ).onSameThread().check()
    }



    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//      startActivityForResult(intent, CAMERA_REQUEST_CODE)
        resultLauncher.launch(intent)
    }


    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
                .setMessage("It looks like you have turned off permissions"
                +"required for this feature. It can be enable under App settings!!!")

                .setPositiveButton("Go TO SETTINGS"){_,_->

                    try{
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)

                    }catch (e:ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                }

                .setNegativeButton("CANCEL"){dialog, _->
                    dialog.dismiss()
                }.show()
    }

    private fun areFieldsComplete(): Boolean {
        return this.viewBinding.foodNameEtv.text.isNotEmpty() && this.viewBinding.foodDescEtv.text.isNotEmpty() && this.viewBinding.foodExpEtv.text.isNotEmpty() && (imageUri != null)
    }


}
