package com.angtek.agentesSGM.Activities

import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.angtek.agentesSGM.Models.User
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.activity_servicio_detail.*
import java.util.*
import kotlin.collections.ArrayList

class ServicioDetailActivity : AppCompatActivity(), AdapterView.OnItemClickListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicio_detail)

        tipo.text = User.myservicio!!.tipo
        horafecha.text = User.myservicio!!.horafecha
        sede.text = User.myservicio!!.sede
        proveedor.text = User.myservicio!!.proveedor
        conductor.text = User.myservicio!!.conductor
        Vehiculo.text = User.myservicio!!.Vehiculo



        var list = ArrayList<String>()
        list.add(" ")
        list.add("Carne asada")
        list.add("Pozole")
        list.add("Panques")
        list.add("Atun")
        list.add("Agua")


        val aa = ArrayAdapter(this,android.R.layout.simple_spinner_item,list)
        myspinner.adapter = aa
        //   myspinner.onItemClickListener = this

        var SDate = User.myservicio!!.horafecha

        Log.d("App", "MyDATE: ${SDate}")

        var MDate = Date(SDate)
        var MMont  = MDate.month
        var MYear = MDate.year
        var MDay = MDate.day


        Log.d("App", "REAL DATE: ${MDate}")



        CancelarServicio.setOnClickListener(){
            cancelar()
        }


    }



    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    fun cancelar(){

        val email : String = descripcion.text.toString()
        var errorMessage = ""

        if (email.isEmpty()){
            errorMessage = "Escribe una descripcion del incidente"
        }

        if (errorMessage.isEmpty()){


            val builder = AlertDialog.Builder(this)
            builder.setMessage("Deseas cancelar el servicio?")
                .setPositiveButton("Si cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Mandamos a cancelar
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        // No hacemos nada
                    })

            builder.create()
            builder.show()

        }else{
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }


    }



}
