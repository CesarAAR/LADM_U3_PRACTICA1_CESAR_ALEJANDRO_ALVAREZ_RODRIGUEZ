package mx.tecnm.tepic.ladm_u3_practica1_cesar_alejandro_alvarez_rodriguez

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_agreggar_actividad.view.*
import kotlinx.android.synthetic.main.item_actievi.view.*

class ActiEviAdapter(private val mContext:Context, private val listaActiEvi: List<ACTIEVI>) : ArrayAdapter<ACTIEVI>(mContext,0,listaActiEvi) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_actievi,parent,false)

        val ActiEvi = listaActiEvi[position]

        layout.actidesc.text = ActiEvi.DESCACT
        layout.idevi.text=ActiEvi.IDEVI
        layout.fecCap.text=ActiEvi.FCAP
        layout.fecEN.text= ActiEvi.FEN
        layout.imageView.setImageBitmap(ActiEvi.IMG)


        return layout
    }
}