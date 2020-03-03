package com.angtek.agentesSGM.Activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.angtek.agentesSGM.Models.Servicio
import com.angtek.agentesSGM.Models.User
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.activity_servicio_detail.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ServicioDetailActivity : AppCompatActivity(), AdapterView.OnItemClickListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicio_detail)

        getIncidenceCatalogo()

        var myservicio : Servicio? = User.myservicio



        if (myservicio!!.tipo == "TERMINADO" || myservicio!!.tipo == "NO ABORDO" || myservicio!!.tipo == "CANCELADO" || myservicio!!.tipo == "RECHAZADO" || myservicio!!.tipo == "AGENTE ANULADO"){
            CancelarServicio.isEnabled = false
            CancelarServicio.setBackgroundColor(Color.parseColor("#cccccc"))
        }




        //   myspinner.onItemClickListener = this





        CancelarServicio.setOnClickListener(){
            cancelar()
        }


    }



    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("App", "p0: ${p0}")

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

                        Log.d("App", "SIIIII CSNCELAAAR: ${dialog}")

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



    fun getIncidenceCatalogo(){


        var url = "https://5wbb09vkfi.execute-api.us-east-1.amazonaws.com/myCatalogosAPI/mycatalogos";

        val myjson = JSONObject()
        myjson.put("key1", "")
        myjson.put("key2", "")


        val request = JsonObjectRequest(Request.Method.POST,url,myjson,
            Response.Listener { response ->
                try {

                    var list = ArrayList<String>()
                    val json : JSONObject = JSONObject(response.toString())
                    var code  = json.getString("Code")
                    if(code == "0000"){
                        var results : JSONArray = json.getJSONArray("Data")
                        if (results.length() > 0){
                            Log.d("App", "resultsCATALOGO: ${results}")

                            val myData = results.getJSONObject(0)

                            Log.d("App", "results.length(): ${results.length()}")

                            for (i in 0..(results.length() - 1)){
                                val jsonService = results.getJSONObject(i)
                                var myDescrip = jsonService.getString("DESCRIPTION")
                                Log.d("jsonService", "myDescrip: ${myDescrip}")
                                list.add(myDescrip)

                            }
                            val aa = ArrayAdapter(this,android.R.layout.simple_spinner_item,list)
                            myspinner.adapter = aa
                            myspinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                                    // Display the selected item text on text view


                                    Log.d("jsonService", "Spinner selected : ${parent.getItemAtPosition(position).toString()}" )

                                }

                                override fun onNothingSelected(parent: AdapterView<*>){
                                    // Another interface callback
                                }

                            }

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

        val queue = Volley.newRequestQueue(this)
        queue.add(request)




    }









}
