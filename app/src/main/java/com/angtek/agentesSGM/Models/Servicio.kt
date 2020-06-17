package com.angtek.agentesSGM.Models

import android.util.Log
import org.json.JSONObject

class Servicio {

    var tipo: String = ""
    var horafecha: String = ""
    var sede: String = ""
    var proveedor: String = ""
    var conductor: String = ""
    var Vehiculo: String = ""

    var idS: String = ""
    var origen: String = ""
    var destino: String = ""
    var categoria: String = ""
    var placa:String = ""
    var horaservicio = ""
    var fecha = ""




    constructor()


   constructor(
        tipo: String,
        horafecha: String,
        sede: String,
        proveedor: String,
        conductor: String,
        Vehiculo: String,
        horaservicio : String,
        fecha : String
    ) {
        this.tipo = tipo
        this.horafecha = horafecha
        this.sede = sede
        this.proveedor = proveedor
        this.conductor = conductor
        this.Vehiculo = Vehiculo
       this.horaservicio = horaservicio
       this.fecha = fecha
    }

    constructor(jsonObject: JSONObject) {


        horaservicio =  jsonObject.getString("horaSer")

        tipo = jsonObject.getString("ESTATUS")
         fecha = jsonObject.getString("fechaSer")
        fecha = fecha.replace("T00:00:00.000Z","")
        horafecha = fecha + "  " + jsonObject.getString("horaSer")
        proveedor = jsonObject.getString("PROVEEDOR")
        conductor = jsonObject.getString("CONDUCTOR")
        Vehiculo = jsonObject.getString("VEHICULO")
        placa = jsonObject.getString("PLACAS")

        idS = jsonObject.getString("idServ")
        categoria = jsonObject.getString("tipoRuta")
        if (categoria == "INGRESO"){
            origen = jsonObject.getString("DESTINO B")
            destino = jsonObject.getString("DESTINO A")
        }else{
            origen = jsonObject.getString("DESTINO A")
            destino = jsonObject.getString("DESTINO B")
        }
        var mysede = destino
        mysede = mysede.replace("_"," ")
        mysede = mysede.replace("SALIDA DE ","")
        mysede = mysede.replace("SEDE","")
        sede =  mysede


        var myorigen = origen
        myorigen = myorigen.replace("_"," ")
        myorigen = myorigen.replace("SALIDA DE ","")
        myorigen = myorigen.replace("SEDE","")
        origen =  myorigen






    }

}