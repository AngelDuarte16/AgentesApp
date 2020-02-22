package com.angtek.agentesSGM.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.NetworkError
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.VolleyError
import com.angtek.agentesSGM.Activities.ServicioDetailActivity
import com.angtek.agentesSGM.Adapters.ServiciosRecyclerAdapter
import com.angtek.agentesSGM.Models.Servicio
import com.angtek.agentesSGM.Models.User
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.servicios_fragment_layout.*
import org.json.JSONArray

class Servicios_Fragment :Fragment(), ServiciosRecyclerAdapter.ServicioClickListener, Response.Listener<String>, Response.ErrorListener {



    override fun onErrorResponse(error: VolleyError?) {

        Log.d("App", "Error: ${error.toString()}")
        Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show()

        if (error is NetworkError){

        }
        if (error is ServerError){

        }
    }

    override fun onResponse(response: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var adapter : ServiciosRecyclerAdapter? = null
    var dataSource : ArrayList<Servicio> = ArrayList<Servicio>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.servicios_fragment_layout, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getServices()

        adapter = ServiciosRecyclerAdapter(activity, dataSource)
        serciviosRecyclerView?.adapter = adapter
        serciviosRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        adapter?.listener = this


        //  loadLocaServices("popular")

    }


    override fun onSelectedPosition(position: Int) {
        val servicio : Servicio = dataSource.get(position)

        val intent = Intent(activity, ServicioDetailActivity::class.java)
        User.myservicio = servicio

        startActivity(intent)
    }


    fun getServices(){


        val serivicio1 = Servicio("Salida - Terminado","11/12/20","SEDE : Encarnacio","PROVEEDOR : Elitaxi", "CONDUCTOR : Carlos Enrque", "Tesla Serie 1")
        val serivicio2 = Servicio("Salida - Terminado","14/15/16","SEDE : Terminal ADO","PROVEEDOR : ADO", "CONDUCTOR : Carlos Enrque", "Chimeco")

        dataSource.add(serivicio1)
        dataSource.add(serivicio2)

    }


    fun saveServices(key: String, response: String){
        val preferences = activity!!.getSharedPreferences("PREFS",
            Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putString(key, response)
        editor.commit()
    }

    fun readServices(key : String) : String?{
        val preferences
                = activity!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        return preferences.getString(key, null)
    }



    fun loadLocaServices(key : String){
        val json = readServices(key)
        if (json != null){
            try {
                val results = JSONArray(json)

                for (i in 0..(results.length() - 1)){
                    val jsonServices = results.getJSONObject(i)
                    // Convertimos el JsonObject a un objeto Movie
                    val service = Servicio(jsonServices)
                    dataSource.add(service)
                }
                adapter?.notifyDataSetChanged()

            }catch (e : Exception){

            }
        }
    }

}