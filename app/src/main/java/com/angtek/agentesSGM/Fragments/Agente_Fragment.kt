package com.angtek.agentesSGM.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.angtek.agentesSGM.Activities.LoginActivity
import com.angtek.agentesSGM.Activities.ServicioDetailActivity
import com.angtek.agentesSGM.Activities.TabsActivity
import com.angtek.agentesSGM.Models.Servicio
import com.angtek.agentesSGM.Models.User
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.agente_fragment_layout.*
import org.json.JSONArray
import org.json.JSONObject

class Agente_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.agente_fragment_layout, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        UIStart()


        logoutButton.setOnClickListener {
            logout()
            TabsActivity().finish()
            activity!!.finish()

        }
    }

    fun UIStart(){
        setImage()
        if (User.INCIDENCES != null){
            NumberIncidencesTV.setText(User.INCIDENCES)
        }else{
            getIncidences()
        }
        nombreTV.setText(User.FIRST_NAME)
        if (User.ACTIVE == "1"){
            statusTV.setText("Activo")
        }else{
            statusTV.setText("Inactivo")
        }
    }

    fun getIncidences(){
        var url = "https://5wbb09vkfi.execute-api.us-east-1.amazonaws.com/incidenciasAPI/myincidence";
        val jsonObject = JSONObject()
        jsonObject.put("key1", (User.ID).toString())
        jsonObject.put("key2", "1")
        val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                try {
                    val json : JSONObject = JSONObject(response.toString())
                       var code  = json.getString("Code")
                        if(code == "0000"){
                            var results : JSONArray = json.getJSONArray("Data")
                            if (results.length() > 0){
                                val myData = results.getJSONObject(0)
                                var NumberIncidences  = myData.getString("count(ID)")
                                if(NumberIncidences != "0"){
                                    NumberIncidencesTV.setText(NumberIncidences)
                                    User.INCIDENCES = NumberIncidences
                                }
                            }else{
                                //No trajo respuesta
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

    fun setImage(){
        if(User.GENDER != null){
            if (User.GENDER == "Mujer"){
                imageViewUser.setImageResource(R.drawable.ic_girl)
            }else{
                imageViewUser.setImageResource(R.drawable.ic_boy)
            }
        }
    }


    fun logout(){
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)

        User.myservicio = null
        User.ID = null
        User.DNI = null
        User.CODE = null
        User.FIRST_NAME = null
        User.LAST_NAME = null
        User.GENDER = null
        User.ACTIVE = null
        User.INCIDENCES = null

    }

}







