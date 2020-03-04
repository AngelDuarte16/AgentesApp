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
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.angtek.agentesSGM.Activities.ServicioDetailActivity
import com.angtek.agentesSGM.Adapters.ServiciosRecyclerAdapter
import com.angtek.agentesSGM.Models.Servicio
import com.angtek.agentesSGM.Models.User
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.agente_fragment_layout.*
import kotlinx.android.synthetic.main.servicios_fragment_layout.*
import org.json.JSONArray
import org.json.JSONObject
import android.view.ViewManager



class Servicios_Fragment :Fragment(), ServiciosRecyclerAdapter.ServicioClickListener {



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


    }


    override fun onSelectedPosition(position: Int) {
        val servicio : Servicio = dataSource.get(position)
        val intent = Intent(activity, ServicioDetailActivity::class.java)
        User.myservicio = servicio
        startActivity(intent)
    }


    fun getServices(){

        /*
        val serivicio1 = Servicio("Salida - Terminado","11/12/20","SEDE : Encarnacio","PROVEEDOR : Elitaxi", "CONDUCTOR : Carlos Enrque", "Tesla Serie 1")
        val serivicio2 = Servicio("Salida - Terminado","14/15/16","SEDE : Terminal ADO","PROVEEDOR : ADO", "CONDUCTOR : Pepe pasto", "Chimeco")
        dataSource.add(serivicio1)
        dataSource.add(serivicio2)
*/


        var url = "https://5wbb09vkfi.execute-api.us-east-1.amazonaws.com/incidenciasAPI/myincidence";
        val jsonObject = JSONObject()
        jsonObject.put("key1", (User.ID).toString())
        jsonObject.put("key2", "2")
        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                try {
                    val json : JSONObject = JSONObject(response.toString())
                    var code  = json.getString("Code")
                    if(code == "0000"){
                        var results : JSONArray = json.getJSONArray("Data")
                        if (results.length() > 0){

                            val params: ViewGroup.LayoutParams = NoServicios.layoutParams
                            params.height = 0
                            NoServicios.layoutParams = params

                            for (i in 0..(results.length() - 1)){
                                val jsonService = results.getJSONObject(i)
                                val myServicio = Servicio(jsonService)

                                dataSource.add(myServicio)
                            }

                            adapter?.notifyDataSetChanged()


                        }else{
                           // Toast.makeText(activity, "No tienes servicios por ahora", Toast.LENGTH_LONG).show()
                        }
                    }
                }catch (e:Exception){
                    Log.d("App", "Exception: ${e}")
                }
            }, Response.ErrorListener{
                Log.d("App", "Error: ${it}")
            })
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            1, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val queue = Volley.newRequestQueue(activity)
        queue.add(request)

    }

}