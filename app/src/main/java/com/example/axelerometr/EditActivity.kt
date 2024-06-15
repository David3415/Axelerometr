package com.example.axelerometr

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.axelerometr.constance.Constance
import com.example.axelerometr.databinding.ActivityEditBinding
import com.example.axelerometr.db.DbManager

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    val dbManager = DbManager(this)
    var tempImageUri = "empty"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val i = intent
        binding.edVal.setText(i.getStringExtra(Constance.I_VAL_KEY))
        getMyIntents()
    }

    fun getMyIntents() {
        val i = intent
        if (i != null) {
            if (i.getStringExtra(Constance.I_TITLE_KEY) != null) {
                binding.edTitle.setText(i.getStringExtra(Constance.I_TITLE_KEY))
                binding.edVal.setText(i.getStringExtra(Constance.I_VAL_KEY))
                if (i.getStringExtra(Constance.I_URI_KEY) != null) {
                    binding.mainImageLayout.visibility = View.VISIBLE
                    binding.btnAddPic.visibility=View.GONE
                    binding.btnStore.visibility=View.GONE
                    binding.imMainImage.setImageURI(Uri.parse(i.getStringExtra(Constance.I_URI_KEY)))
                }
            }
        }
    }

    fun storeVal(view: View) {
        val _edDesc: TextView = findViewById(R.id.edVal)
        val _edTitle: TextView = findViewById(R.id.edTitle)
        val myTitle = _edTitle.text.toString()
        val i = intent
        if (i != null) {
            if (i.getStringExtra(Constance.I_VAL_KEY) != null) {
                val myDesk = _edDesc.text.toString()
                if (myTitle != "" && myDesk != "") {
                    binding.edVal.setText(i.getStringExtra(Constance.I_VAL_KEY))
                    var toInsert = i.getStringExtra(Constance.I_VAL_KEY)
                    dbManager.insertToDb(
                        myTitle, toInsert.toString(), tempImageUri
                    )
                }
            }
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }

    fun onClickAddImg(view: View) {
        binding.mainImageLayout.visibility = View.VISIBLE
        binding.btnAddPic.visibility = View.GONE
    }

    fun onClickDelImage(view: View) {
        binding.mainImageLayout.visibility = View.VISIBLE
        binding.btnAddPic.visibility = View.GONE
    }

    fun onClickEditImage(view: View) {

        // val intent = Intent(Intent.ACTION_PICK)
        //val intent = Intent(Intent.ACTION_GET_CONTENT)
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, Constance.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Constance.IMAGE_REQUEST_CODE) {
            val imMainImage: ImageView = findViewById(R.id.imMainImage)
            imMainImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()//для БД
        } else if (resultCode == RESULT_OK && requestCode == Constance.CAMERA_REQUEST_CODE) {
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.imMainImage.setImageBitmap(bitmap)
            tempImageUri = bitmap.toString()//для БД
        }
    }

    fun onClickCamera(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Constance.CAMERA_REQUEST_CODE)

    }

    fun onClickBtnStore(view: View) {
        val _edVal: TextView = findViewById(R.id.edVal)
        val _edTitle: TextView = findViewById(R.id.edTitle)

        val myTitle = _edTitle.text.toString()

        val i = intent
        if (i != null) {

            if (i.getStringExtra(Constance.I_VAL_KEY) != null) {
                val myDesk = _edVal.text.toString()
                if (myTitle != "" && myDesk != "") {
                    binding.edVal.setText(i.getStringExtra(Constance.I_VAL_KEY))
                    var temp = i.getStringExtra(Constance.I_VAL_KEY)
                    dbManager.insertToDb(
                        myTitle, temp.toString(), tempImageUri
                    )
                }
            }
        }
        finish()

    }
}