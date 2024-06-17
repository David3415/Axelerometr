package com.example.axelerometr

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.axelerometr.constance.Constance
import com.example.axelerometr.databinding.ActivityEditBinding
import com.example.axelerometr.db.DbManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    val dbManager = DbManager(this)
    var tempImageUri = "empty"
    var id = 0
    var isEditState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val i = intent
        binding.edGradus.setText(i.getStringExtra(Constance.I_GRADUS_KEY))
        getMyIntents()
    }

    fun getMyIntents() = with(binding) {
        val i = intent
        if (i != null) {
            if (i.getStringExtra(Constance.I_COMMENT_KEY) != null) {
                edGradus.setText(i.getStringExtra(Constance.I_GRADUS_KEY))
                edComment.setText(i.getStringExtra(Constance.I_COMMENT_KEY))
                isEditState = true
                id = i.getIntExtra(Constance.I_ID_KEY, 0)
                if (i.getStringExtra(Constance.I_URI_KEY) != null) {
                    mainImageLayout.visibility = View.VISIBLE
                    edGradus.isEnabled = false
                    edComment.isEnabled = false
                    btnAddPic.visibility = View.GONE
                    btnSave.visibility = View.GONE
                    imEdit.visibility = View.GONE
                    imCamera.visibility = View.GONE
                    imDelete.visibility = View.GONE
                    Log.d("MyLog", "${i.getStringExtra(Constance.I_URI_KEY)}")
                    imMainImage.setImageURI(Uri.parse(i.getStringExtra(Constance.I_URI_KEY)))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }

    fun onClickAddImg(view: View) = with(binding) {
        mainImageLayout.visibility = View.VISIBLE
        btnAddPic.visibility = View.GONE
    }

    fun onClickDelImage(view: View) = with(binding) {
        mainImageLayout.visibility = View.VISIBLE
        btnAddPic.visibility = View.GONE
    }

    fun onClickEditImage(view: View) {
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

    fun onClickSave(view: View) {
        val _gradus: TextView = findViewById(R.id.edGradus)
        val _comment: TextView = findViewById(R.id.edComment)
        val comment = _comment.text.toString()

        val i = intent
        if (i != null) {
            if (i.getStringExtra(Constance.I_GRADUS_KEY) != null) {
                if (comment != "" && _gradus.toString() != "") {
                    binding.edGradus.setText(i.getStringExtra(Constance.I_GRADUS_KEY))
                    var gradus = i.getStringExtra(Constance.I_GRADUS_KEY)
                    CoroutineScope(Dispatchers.Main).launch {
                        if (isEditState) {
                            dbManager.openDb()
                            dbManager.updateItem(gradus.toString(), comment, tempImageUri, id)
                        } else {
                            dbManager.insertToDb(gradus.toString(), comment, tempImageUri)
                        }
                    }
                    finish()
                }
            }
        }
    }

    fun onClickEdit(view: View) = with(binding) {
        btnSave.visibility = View.VISIBLE
        imEdit.visibility = View.VISIBLE
        imDelete.visibility = View.VISIBLE
        imCamera.visibility = View.VISIBLE
        binding.edComment.isEnabled = true
    }
}