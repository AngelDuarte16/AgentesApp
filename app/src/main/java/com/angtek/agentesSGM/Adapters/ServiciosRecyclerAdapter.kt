package com.angtek.agentesSGM.Adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angtek.agentesSGM.Models.Servicio
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.layout_servicios.view.*

class ServiciosRecyclerAdapter : RecyclerView.Adapter<ServiciosRecyclerAdapter.MyViewHolder> {

    var listener : ServiciosRecyclerAdapter.ServicioClickListener? = null
    var myContext : Context? = null
    var dataSource = ArrayList<Servicio>()

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val myservicio : Servicio = dataSource.get(p1)

        p0?.tipoTV?.text = myservicio.categoria + " - " + myservicio.tipo
        p0?.horafechaTV?.text = myservicio.horafecha
        p0?.proveedorTV?.text = myservicio.proveedor
        p0?.sedeTV?.text = myservicio.sede
        p0?.VehiculoTV?.text = myservicio.Vehiculo
        p0?.choferTV?.text = myservicio.conductor
        p0?.placaTV?.text = myservicio.placa
        p0?.salidaTV?.text = myservicio.origen
    }

    override fun getItemCount(): Int {
        return dataSource.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        var myView : View = View.inflate(myContext, R.layout.layout_servicios, null)
        val holder : ServiciosRecyclerAdapter.MyViewHolder = ServiciosRecyclerAdapter.MyViewHolder(myView)
        myView.setOnClickListener {
            Log.d("App", "Position: ${holder.adapterPosition}")
            listener?.onSelectedPosition(holder.adapterPosition)
        }
        return  holder
    }

    class MyViewHolder : RecyclerView.ViewHolder{
        var tipoTV : TextView? = null
        var horafechaTV : TextView? = null
        var sedeTV : TextView? = null
        var proveedorTV : TextView? = null
        var choferTV : TextView? = null
        var VehiculoTV : TextView? = null
        var placaTV : TextView? = null
        var salidaTV : TextView? = null

        constructor(view : View) : super(view){
            tipoTV = view.tipo
            horafechaTV = view.horafecha
            sedeTV = view.sede
            proveedorTV = view.proveedor
            choferTV = view.chofer
            VehiculoTV = view.Vehiculo
           placaTV = view.placasTV
            salidaTV = view.SalidaTV
        }
    }

    interface ServicioClickListener{
        fun onSelectedPosition(position : Int)
    }

    constructor(context : Context?, dataSource : ArrayList<Servicio>){
        myContext = context
        this.dataSource = dataSource
    }
}