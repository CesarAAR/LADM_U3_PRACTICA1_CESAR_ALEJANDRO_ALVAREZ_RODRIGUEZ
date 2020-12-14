package mx.tecnm.tepic.ladm_u3_practica1_cesar_alejandro_alvarez_rodriguez

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_agreggar_actividad.*

class ActivityAgreggarActividad : AppCompatActivity() {
    var BaseDatos = BasedeDatos(this, "basedatos1",null,1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreggar_actividad)

        buttonInsertarActividad.setOnClickListener {
            insertarActividad()
        }

        fechacap.setOnClickListener {
            showDatePickerDialog()
        }

        fechaent.setOnClickListener {
            showDatePickerDialog2()
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarActividad() {
        try {
            var trans = BaseDatos.writableDatabase //permite leer y escribir
            var variables= ContentValues()
            variables.put("DESCRIPCION",descrip.text.toString())
            variables.put("FECHACAPTURA",fechacap.text.toString())
            variables.put("FECHAENTREGA",fechaent.text.toString())

            var respuesta = trans.insert("ACTIVIDADES",null,variables)
            if(respuesta==-1L){
                mensaje("ERROR NO SE PUDO INSERTAR")
            }else{
                mensaje("SE INSERTO CON EXITO")
                limpiarCampos()
            }
            trans.close()
            finish()
        }catch (e: SQLiteException){
            mensaje(e.message!!)
        }
    }

    private fun limpiarCampos() {
        descrip.setText("")
        fechaent.setText("")
        fechacap.setText("")
    }

    private fun mensaje(s:String) {
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i-> d.dismiss()}.show()
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }


    private fun onDateSelected(day: Int, month: Int, year: Int) {
        fechacap.setText("$day/$month/$year")
    }
    private fun showDatePickerDialog2() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected2(day, month+1, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected2(day: Int, month: Int, year: Int) {
        fechaent.setText("$day/$month/$year")
    }
}