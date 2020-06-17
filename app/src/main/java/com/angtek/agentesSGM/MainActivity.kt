package com.angtek.agentesSGM

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.angtek.agentesSGM.Activities.LoginActivity
import com.angtek.agentesSGM.Activities.TabsActivity
import com.angtek.agentesSGM.Models.User
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*
import kotlin.concurrent.timerTask
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                  //  Log.w("App", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
             /*   val msg =  token
                Log.d("App", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()*/
            })



        val preferences = this!!.getSharedPreferences("USUARIO", Context.MODE_PRIVATE)
        val myuser = preferences.getString("user", null)
        val mypass = preferences.getString("pass", null)



        if (myuser != null){
            if (mypass !=null){
                loginPressed(myuser,mypass!!)
            }
        }else{
            Timer().schedule(timerTask {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }





    }




    fun loginPressed(user : String, pass : String) {

        val email : String = user
        val password = pass


        var url = "http://192.168.0.11:3000/agent/login";

        val myjson = JSONObject()
        myjson.put("user", email)
        myjson.put("pass", password)

            val request = JsonObjectRequest(Request.Method.POST,url,myjson,
                Response.Listener { response ->
                    try {
                        val json : JSONObject = JSONObject(response.toString())

                            var results : JSONArray = json.getJSONArray("Data")
                            if (results.length() > 0){

                                val myData = results.getJSONObject(0)
                                User.ID = myData.getString("ID")
                                User.DNI = myData.getString("DNI")
                                User.CODE = myData.getString("CODE")
                                User.FIRST_NAME = myData.getString("FIRST_NAME")
                                User.LAST_NAME = myData.getString("LAST_NAME")
                                User.GENDER = myData.getString("GENDER")
                                var act  = myData.getJSONObject("ACTIVE")
                                var actst : JSONArray = act.getJSONArray("data")
                                User.ACTIVE = actst[0].toString()



                                Timer().schedule(timerTask {
                                    val intent = Intent(this@MainActivity, TabsActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }, 3000)

                            }

                    }catch (e:Exception){
                        Log.d("App", "Exception: ${e}")
                    }

                }, Response.ErrorListener{

                    Log.d("App", "Error: ${it}")
                    var mytoast = Toast.makeText(this, "Error de red, intente más tarde",
                        Toast.LENGTH_LONG)
                    mytoast.setGravity(Gravity.CENTER or Gravity.CENTER, 0, 0)
                    mytoast.show()

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
