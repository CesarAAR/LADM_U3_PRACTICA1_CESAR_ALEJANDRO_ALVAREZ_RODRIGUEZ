package mx.tecnm.tepic.ladm_u3_practica1_cesar_alejandro_alvarez_rodriguez

import android.R.attr
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_agregar.*
import java.io.IOException


class ActivityAgregar : AppCompatActivity() {
    var BaseDatos = BasedeDatos(this, "basedatos1",null,1)
    private var imagePicked: Bitmap? = null
    private val SELECT_ACTIVITY=50
    private var imageUri: Uri?=null
    private var numberImageSelected =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)


        buttonRegresar.setOnClickListener {
            finish()
        }
        imageView1.setOnClickListener {
            numberImageSelected=1
            var intent=Intent()
            startActivityForResult( Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10)
        }
        imageView2.setOnClickListener {
            numberImageSelected=2
            var intent=Intent()
            startActivityForResult( Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10)
        }
        imageView3.setOnClickListener {
            numberImageSelected=3
            var intent=Intent()
            startActivityForResult( Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10)
        }
        imageView4.setOnClickListener {
            numberImageSelected=4
            var intent=Intent()
            startActivityForResult( Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10)
        }
        imageView5.setOnClickListener {
            numberImageSelected=5
            var intent=Intent()
            startActivityForResult( Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10)
        }
        imageView6.setOnClickListener {
            numberImageSelected=6
            var intent=Intent()
            startActivityForResult( Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10)
        }

        buttonInsertar.setOnClickListener {
            //insertar()
        }

        buttonRegresar.setOnClickListener{
            finish()
        }
    }


   /* private fun limpiarCampos() {
        descrip.setText("")
        fechaent.setText("")
        fechacap.setText("")
        imageView1.setImageResource(R.drawable.ic_launcher_foreground)
        imageView2.setImageResource(R.drawable.ic_launcher_foreground)
        imageView3.setImageResource(R.drawable.ic_launcher_foreground)
        imageView4.setImageResource(R.drawable.ic_launcher_foreground)
        imageView5.setImageResource(R.drawable.ic_launcher_foreground)
        imageView6.setImageResource(R.drawable.ic_launcher_foreground)
    }*/

    private fun mensaje(s:String) {
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i-> d.dismiss()}.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 10 && resultCode === Activity.RESULT_OK && attr.data != null) {
            imageUri=data!!.data
            try {
                imagePicked = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            SetImage(imagePicked!!, numberImageSelected)
        }

    }
    private fun SetImage(imageSelected: Bitmap, ImageNumberSelected: Int) {
        numberImageSelected = when (ImageNumberSelected) {
            1 -> {
                imageView1.setImageBitmap(imageSelected)
                0
            }
            2 -> {
                imageView2.setImageBitmap(imageSelected)
                0
            }
            3 -> {
                imageView3.setImageBitmap(imageSelected)
                0
            }
            4 -> {
                imageView4.setImageBitmap(imageSelected)
                0
            }
            5 -> {
                imageView5.setImageBitmap(imageSelected)
                0
            }
            6 -> {
                imageView6.setImageBitmap(imageSelected)
                0
            }
            else -> {
                Toast.makeText(this, "NO SE PUDO COLOCAR LA IMAGEN", Toast.LENGTH_SHORT).show()
                0
            }
        }
    }
    /*private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }*/


   /* private fun onDateSelected(day: Int, month: Int, year: Int) {
        fechacap.setText("$day/$month/$year")
    }
    private fun showDatePickerDialog2() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected2(day, month+1, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected2(day: Int, month: Int, year: Int) {
        fechaent.setText("$day/$month/$year")
    }*/
}