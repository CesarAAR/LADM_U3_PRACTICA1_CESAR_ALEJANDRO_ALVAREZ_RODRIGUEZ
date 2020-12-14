package mx.tecnm.tepic.ladm_u3_practica1_cesar_alejandro_alvarez_rodriguez

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_buscar.*
import kotlinx.android.synthetic.main.activity_main.*

class ActivityBuscar : AppCompatActivity() {
    var BaseDatos = BasedeDatos(this, "basedatos1",null,1)
    var listaID = ArrayList<String>()
    var idSeleccionadoEnLista = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        buttonBusqueda.setOnClickListener {
            if(radioButton.isChecked){
                cargarporDesc()
            }
            if(radioButton2.isChecked){
                cargarporID()
            }
            if(!radioButton.isChecked && !radioButton2.isChecked){
                mensaje("TIENES QUE SELECCIONAR POR CUAL BUSCAR")
            }
        }

    }
    private fun cargarContactos() {
        try{
            var trans = BaseDatos.readableDatabase
            var actividades = ArrayList<String>()

            var respuesta = trans.query("ACTIVIDADES", arrayOf("*"),null,null,null,null,null)
            listaID.clear()
            if(respuesta.moveToFirst()){

                    do {
                        var concatenacion = "ID: ${respuesta.getInt(0)}\nDESCRIPCION: " +
                                "${respuesta.getString(1)}\nFECHA CAPTURA: ${respuesta.getString(2)}\n" +
                                "FECHA ENTREGA: ${respuesta.getString(3)}"
                        actividades.add(concatenacion)
                        listaID.add(respuesta.getInt(0).toString())
                    } while (respuesta.moveToNext())
            }else{
                actividades.add("No hay registros")
            }
            listbuscar.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,actividades)
            this.registerForContextMenu(listbuscar)
            listbuscar.setOnItemClickListener { adapterView, view, i, id ->
                idSeleccionadoEnLista=i
                Toast.makeText(this, "Se selecciono elemento ${idSeleccionadoEnLista}", Toast.LENGTH_LONG).show()
            }
            trans.close()
        }catch (e: SQLiteException){
            mensaje("Error: "+e.message!!)
        }
    }
    private fun cargarporDesc() {
        try{
            var trans = BaseDatos.readableDatabase
            var actividades = ArrayList<String>()
            var respuesta = trans.rawQuery("SELECT * FROM ACTIVIDADES ORDER BY DESCRIPCION ASC", null)
            if(respuesta.moveToFirst()){
                do{
                    var concatenacion = "ID: ${respuesta.getInt(0)}\nDESCRIPCION: " +
                            "${respuesta.getString(1)}\nFECHA CAPTURA: ${respuesta.getString(2)}\n" +
                            "FECHA ENTREGA: ${respuesta.getString(3)}"
                    actividades.add(concatenacion)
                }while (respuesta.moveToNext())
            }else{
                actividades.add("NO HAY EVENTOS INSERTADAS")
            }
            listbuscar.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,actividades)
            trans.close()
        }catch (e: SQLiteException){
            mensaje("Error: "+e.message!!)
        }
    }
    private fun cargarporID() {
        try{
            var trans = BaseDatos.readableDatabase
            var actividades = ArrayList<String>()
            var respuesta = trans.rawQuery("SELECT * FROM ACTIVIDADES ORDER BY ID_ACTIVIDAD ASC", null)
            if(respuesta.moveToFirst()){
                do{
                    var concatenacion = "ID: ${respuesta.getInt(0)}\nDESCRIPCION: " +
                            "${respuesta.getString(1)}\nFECHA CAPTURA: ${respuesta.getString(2)}\n" +
                            "FECHA ENTREGA: ${respuesta.getString(3)}"
                    actividades.add(concatenacion)
                }while (respuesta.moveToNext())
            }else{
                actividades.add("NO HAY EVENTOS INSERTADAS")
            }
            listbuscar.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,actividades)
            trans.close()
        }catch (e: SQLiteException){
            mensaje("Error: "+e.message!!)
        }
    }

    override fun onResume(){
        super.onResume()
        cargarContactos()

    }
    private fun mensaje(s:String) {
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage(s)
            .setPositiveButton("OK"){d,i-> d.dismiss()}.show()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflaterOB = menuInflater
        inflaterOB.inflate(R.menu.menubusqueda,menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(idSeleccionadoEnLista==-1){
            mensaje("ERROR! debes dar clic primero en un item para ACTUALIZAR/BORRAR")
            return true
        }

        when(item.itemId){
            R.id.itemVer->{
                var itent= Intent(this, Main_VER::class.java)
                itent.putExtra("IDActiVER",listaID.get(idSeleccionadoEnLista))
                startActivity(itent)
            }

            R.id.itemsalir2->{
                finish()
            }
        }
        idSeleccionadoEnLista==-1
        return true
    }
}