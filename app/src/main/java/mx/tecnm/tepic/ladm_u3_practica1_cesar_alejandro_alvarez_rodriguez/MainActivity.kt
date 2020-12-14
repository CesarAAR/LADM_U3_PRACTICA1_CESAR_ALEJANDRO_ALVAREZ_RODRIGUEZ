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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var BaseDatos = BasedeDatos(this, "basedatos1",null,1)
    var listaID = ArrayList<String>()
    var idSeleccionadoEnLista = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonAgregar.setOnClickListener {
            var intent= Intent(this,ActivityAgreggarActividad::class.java)
            startActivity(intent)
        }
        buttonBuscar.setOnClickListener {
            var intent = Intent(this,ActivityBuscar::class.java)
            startActivity(intent)
        }
        cargarContactos()
    }

    private fun cargarContactos() {
        try{
            var trans = BaseDatos.readableDatabase
            var actividades = ArrayList<String>()

            var respuesta = trans.query("ACTIVIDADES", arrayOf("*"),null,null,null,null,null)

            listaID.clear()
            if(respuesta.moveToFirst()){
                do{
                    var concatenacion = "ID: ${respuesta.getInt(0)}\nDESCRIPCION: " +
                            "${respuesta.getString(1)}\nFECHA CAPTURA: ${respuesta.getString(2)}\n" +
                            "FECHA ENTREGA: ${respuesta.getString(3)}"
                    actividades.add(concatenacion)
                    listaID.add(respuesta.getInt(0).toString())
                }while (respuesta.moveToNext())
            }else{
                actividades.add("NO HAY EVENTOS INSERTADAS")
            }
            list_actividades.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,actividades)
            this.registerForContextMenu(list_actividades)
            list_actividades.setOnItemClickListener { adapterView, view, i, id ->
                idSeleccionadoEnLista=i
                Toast.makeText(this, "Se selecciono elemento ${idSeleccionadoEnLista}", Toast.LENGTH_LONG).show()
            }
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
        inflaterOB.inflate(R.menu.menuppal,menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(idSeleccionadoEnLista==-1){
            mensaje("ERROR! debes dar clic primero en un item para ACTUALIZAR/BORRAR")
            return true
        }

        when(item.itemId){
            R.id.iteminsertevi->{
                var itent=Intent(this, ActivityAgregarEvidencias::class.java)
                itent.putExtra("IDActi",listaID.get(idSeleccionadoEnLista))
                startActivity(itent)
            }
            R.id.itemeliminar->{
                var idEliminar = listaID.get(idSeleccionadoEnLista)
                AlertDialog.Builder(this)
                    .setTitle("ATENCION")
                    .setMessage("ESTAS SEGURO DE ELIMINAR ID: ${idEliminar}?")
                    .setPositiveButton("Eliminar"){d,i->
                        eliminar(idEliminar)
                    }
                    .setNeutralButton("NO"){d,i->}
                    .show()
            }
            R.id.itemsalir->{
                finish()
            }
        }
        idSeleccionadoEnLista==-1
        return true
    }

    private fun eliminar(idEliminar: String) {
        try {
            var trans = BaseDatos.writableDatabase
            var resultados= trans.delete("ACTIVIDADES","ID_ACTIVIDAD =?",
                arrayOf(idEliminar))
            if(resultados==0){
                mensaje("ERROR! No se pudo eliminar")
            }else{
                mensaje("Se logro eliminar con exito el ID ${idEliminar}")
            }
            trans.close()
            cargarContactos()
        }catch (e:SQLiteException){
            mensaje(e.message!!)
        }
    }

}