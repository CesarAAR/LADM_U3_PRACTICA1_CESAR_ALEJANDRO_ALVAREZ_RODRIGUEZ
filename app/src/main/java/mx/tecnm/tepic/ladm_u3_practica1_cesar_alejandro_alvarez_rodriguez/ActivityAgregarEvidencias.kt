package mx.tecnm.tepic.ladm_u3_practica1_cesar_alejandro_alvarez_rodriguez

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_agregar_evidencias.*
import kotlinx.android.synthetic.main.activity_agreggar_actividad.*
import java.io.ByteArrayOutputStream
import java.util.*


class ActivityAgregarEvidencias : AppCompatActivity() {
    var BaseDatos = BasedeDatos(this, "basedatos1",null,1)
    private var imagePicked: Bitmap? = null
    private var imagedd: ImageDecoder.Source? = null
    private var imageUri: Uri?=null
    private var numberImageSelected =0
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_evidencias)

        var extra=intent.extras
        id=extra!!.getString("IDActi")!!

        textID.setText(textID.text.toString()+"${id}")
        buttonInsertar.setOnClickListener {
           insertarEvi()
        }
        buttonRegresarEvi.setOnClickListener {
            finish()
        }

        imageViewEVI.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,numberImageSelected)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode == numberImageSelected && resultCode == Activity.RESULT_OK->{
                imageUri=data!!.data

                try {
                    imageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            imagePicked = MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                imageUri
                            )
                            imageViewEVI.setImageBitmap(imagePicked)
                        } else {
                            val source = ImageDecoder.createSource(this.contentResolver, imageUri!!)
                            imagePicked = ImageDecoder.decodeBitmap(source)
                            imageViewEVI.setImageBitmap(imagePicked)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                //imageViewEVI.setImageURI(imageUri)
            }
        }
    }

    private fun insertarEvi() {
        try {
            var baos = ByteArrayOutputStream(20480)
            imagePicked?.compress(Bitmap.CompressFormat.PNG,0,baos)
            var blob = baos.toByteArray()
            var trans = BaseDatos.writableDatabase //permite leer y escribir
            var sql = "INSERT INTO  EVIDENCIAS(ID_ACTI,FOTO) VALUES (${id.toInt()},?)"
            var respuesta = trans.compileStatement(sql)
            respuesta.clearBindings()
            respuesta.bindBlob(1,blob)
            respuesta.executeInsert()
            mensaje("Se inserto Correctamente")
            trans.close()
            finish()
        }catch (e: SQLiteException){
            mensaje(e.message!!)
        }
    }


    private fun limpiarCampos() {
        imageViewEVI.setImageResource(R.drawable.ic_launcher_foreground)
    }
    private fun mensaje(s:String) {
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i-> d.dismiss()}.show()
    }

}