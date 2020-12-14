package mx.tecnm.tepic.ladm_u3_practica1_cesar_alejandro_alvarez_rodriguez

import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_agregar_evidencias.*
import kotlinx.android.synthetic.main.activity_main__v_e_r.*
import java.io.ByteArrayInputStream

class Main_VER : AppCompatActivity() {
    var BaseDatos = BasedeDatos(this, "basedatos1",null,1)
    var id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__v_e_r)
        var extra=intent.extras
        id=extra!!.getString("IDActiVER")!!

        idActiVer.setText(idActiVer.text.toString()+"${id}")
        mostrardatos()
        mostrarFoto()

        eliminarTodo.setOnClickListener {
            borrarActividad()
            finish()
        }
        eliminarEVI.setOnClickListener {
            borrarEVI()
        }
        salirVer.setOnClickListener {
            finish()
        }
    }

    private fun mostrarFoto() {
        try {
            var trans = BaseDatos.readableDatabase
            var respuesta2 = trans.query(
                "EVIDENCIAS",
                arrayOf("FOTO"),
                "ID_ACTI=?",
                arrayOf(id),
                null,
                null,
                null
            )
            var bitmap: Bitmap? = null
            if (respuesta2.moveToFirst()) {
                var blob = respuesta2.getBlob(0)
                var bais = ByteArrayInputStream(blob)
                bitmap = BitmapFactory.decodeStream(bais)
                imageEVIVER.setImageBitmap(bitmap)
            }
            trans.close()
        }catch (e:SQLiteException){
            mensaje("Error: "+e.message!!)
        }
    }

    private fun mostrardatos() {
        try {
            var trans = BaseDatos.readableDatabase
            var respuesta = trans.query("ACTIVIDADES", arrayOf("DESCRIPCION", "FECHACAPTURA", "FECHAENTREGA"),
                "ID_ACTIVIDAD=?",
                arrayOf(id),
                null,
                null,
                null
            )
            if (respuesta.moveToFirst()) {
                descVER.setText(descVER.text.toString() + respuesta.getString(0))
                fechaCAPV.setText(fechaCAPV.text.toString() + respuesta.getString(1))
                fechaENTV.setText(fechaENTV.text.toString() + respuesta.getString(2))
            }
            trans.close()
        }catch (e: SQLiteException){
            mensaje("Error: "+e.message!!)
        }
    }

    private fun borrarEVI(){
        try {
            var trans = BaseDatos.writableDatabase
            var resultados= trans.delete("EVIDENCIAS","ID_ACTI=?",
                arrayOf(id))
            if(resultados==0){
                mensaje("ERROR! No se pudo eliminar")
            }else{
                mensaje("Se logro eliminar con exito la evidencia del la actividad con ID: ${id}")
            }
            trans.close()
            imageViewEVI.setImageResource(R.drawable.ic_launcher_foreground)
        }catch (e:SQLiteException){
            mensaje(e.message!!)
        }
    }
    private fun borrarActividad(){
        try {
            var trans = BaseDatos.writableDatabase
            var resultados= trans.delete("ACTIVIDADES","ID_ACTIVIDAD=?",
                arrayOf(id))
            if(resultados==0){
                mensaje("ERROR! No se pudo eliminar")
            }else{
                mensaje("Se logro eliminar con exito la actividad con ID: ${id}")
            }
            trans.close()
        }catch (e:SQLiteException){
            mensaje(e.message!!)
        }
    }

    private fun mensaje(s:String) {
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i-> d.dismiss()}.show()
    }
}