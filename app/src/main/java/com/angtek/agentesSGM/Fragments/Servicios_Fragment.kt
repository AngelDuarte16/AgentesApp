package com.angtek.agentesSGM.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import kotlinx.android.synthetic.main.activity_servicio_detail.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.math.log


class Servicios_Fragment :Fragment(), ServiciosRecyclerAdapter.ServicioClickListener {



    var adapter : ServiciosRecyclerAdapter? = null
    var dataSource : ArrayList<Servicio> = ArrayList<Servicio>()
    var counter : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.servicios_fragment_layout, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        dataSource.clear()
        getServices()


        adapter = ServiciosRecyclerAdapter(activity, dataSource)
        serciviosRecyclerView?.adapter = adapter
        serciviosRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        adapter?.listener = this

        swipeRefreshLayout?.setOnRefreshListener {
            dataSource.clear()
            getServices()
        }

    }

    override fun onResume() {
        super.onResume()
        if(counter>0){
            dataSource.clear()
            getServices()
        }
        counter += 1
        adapter?.notifyDataSetChanged()

    }



    override fun onSelectedPosition(position: Int) {
        val servicio : Servicio = dataSource.get(position)

        if (servicio!!.tipo == "TERMINADO" || servicio!!.tipo == "NO ABORDO" || servicio!!.tipo == "CANCELADO" || servicio!!.tipo == "RECHAZADO" || servicio!!.tipo == "AGENTE ANULADO"){
        }else{

            val currentTimestamp = System.currentTimeMillis()
            val timeStamph: String = SimpleDateFormat("HH").format(currentTimestamp)
            val timeStampm: String = SimpleDateFormat("mm").format(currentTimestamp)
            val timeStampd: String = SimpleDateFormat("dd").format(currentTimestamp)

            var horaservicio = servicio.horaservicio
            var fechaservicio = servicio.fecha

            horaservicio = horaservicio.replace(" ", ":")
            val strs = horaservicio.split(":").toTypedArray()
            val strd = fechaservicio.split("-").toTypedArray()


            Log.d("App","strd.get(0):${strd.get(0)}, timeStampd${ timeStampd}")
            val diamasuno = timeStampd.toInt() + 1
            if (strd.get(0) == timeStampd || strd.get(0) == diamasuno.toString()){

                    val horaact = timeStamph.toInt()
                    val horaser = strs.get(0).toInt()

                    val minutosact = timeStampm.toInt() + (horaact*60)
                    var minutosser = 0

                    if(strs.get(3) == "AM") {
                         minutosser = strs.get(1).toInt() + (horaser * 60)
                    }else{
                         minutosser = strs.get(1).toInt() + (horaser*60) + 720

                    }

                    if ((minutosser - minutosact)>60){
                        val intent = Intent(activity, ServicioDetailActivity::class.java)
                        User.myservicio = servicio
                        startActivity(intent)

                }



            }
        }
    }


    fun getServices(){

      /*

        val serivicio1 = Servicio("Programado","11/12/20","SEDE : Encarnacio","PROVEEDOR : Elitaxi", "CONDUCTOR : Carlos Enrque", "Tesla Serie 1","11:00:00 PM","10-03-20")
        val serivicio2 = Servicio("Programado","14/15/16","SEDE : Terminal ADO","PROVEEDOR : ADO", "CONDUCTOR : Pepe pasto", "Chimeco","08:00:00 AM","10-03-20")
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
                swipeRefreshLayout?.isRefreshing = false
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
                swipeRefreshLayout?.isRefreshing = false
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