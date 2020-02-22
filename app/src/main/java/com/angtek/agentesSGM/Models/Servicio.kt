package com.angtek.agentesSGM.Models

import org.json.JSONObject

class Servicio {

    var tipo: String = ""
    var horafecha: String = ""
    var sede: String = ""
    var proveedor: String = ""
    var conductor: String = ""
    var Vehiculo: String = ""


    constructor()


    constructor(
        tipo: String,
        horafecha: String,
        sede: String,
        proveedor: String,
        conductor: String,
        Vehiculo: String
    ) {
        this.tipo = tipo
        this.horafecha = horafecha
        this.sede = sede
        this.proveedor = proveedor
        this.conductor = conductor
        this.Vehiculo = Vehiculo
    }

    constructor(jsonObject: JSONObject) {


        tipo = jsonObject.getString("tipo")
        horafecha = jsonObject.getString("horafecha")
        proveedor = jsonObject.getString("proveedor")
        conductor = jsonObject.getString("conductor")
        Vehiculo = jsonObject.getString("Vehiculo")
    }

}