package mx.tecnm.tepic.ladm_u3_practica1_cesar_alejandro_alvarez_rodriguez

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BasedeDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context,name,factory,version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE ACTIVIDADES(ID_ACTIVIDAD INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,DESCRIPCION VARCHAR(200),FECHACAPTURA DATE, FECHAENTREGA DATE)")
        db.execSQL("CREATE TABLE EVIDENCIAS(ID_EVIDENCIA INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ID_ACTI INTEGER, FOTO BLOB, FOREIGN KEY(ID_ACTI) REFERENCES ACTIVIDADES(ID_ACTIVIDAD))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}