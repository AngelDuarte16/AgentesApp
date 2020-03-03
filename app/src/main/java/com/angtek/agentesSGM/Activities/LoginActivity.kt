package com.angtek.agentesSGM.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.angtek.agentesSGM.Models.User
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Gravity
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.agente_fragment_layout.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.angtek.agentesSGM.R.layout.activity_login)


        emailET.setText("45939316")
        passwordET.setText("9727302")



        loginButton.setOnClickListener {
            loginButton.isEnabled = false
            loginPressed()
        }


    }



    fun loginPressed() {


        val email : String = emailET.text.toString()
        val password = passwordET.text.toString()
        var errorMessage = ""


        if (password.isEmpty()){
            errorMessage = "Ingresa tu contrasena"
        }
        if (email.isEmpty()){
            errorMessage = "Ingresa tu usuario"
        }

        if (errorMessage.isEmpty()){

            var url = "https://5wbb09vkfi.execute-api.us-east-1.amazonaws.com/myApi/mylogin";


            val myjson = JSONObject()
            myjson.put("key1", email)
            myjson.put("key2", password)


            //val jsonObject = JSONObject(params)


            val request = JsonObjectRequest(Request.Method.POST,url,myjson,
                Response.Listener { response ->
                    try {


                        val json : JSONObject = JSONObject(response.toString())
                        var code  = json.getString("Code")
                        if(code == "0000"){
                            var results : JSONArray = json.getJSONArray("Data")
                            if (results.length() > 0){
                                Log.d("App", "results: ${results}")

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



                                if(password == User.CODE){

                                    loginButton.isEnabled = true

                                    val intent = Intent(this, TabsActivity::class.java)

                                    startActivity(intent)

                                }else{
                                    var mytoast = Toast.makeText(this, "Error en usuario o contraseña",
                                        Toast.LENGTH_LONG)
                                    mytoast.setGravity(Gravity.CENTER or Gravity.CENTER, 0, 0)
                                    mytoast.show()

                                }

                            }else{
                                var mytoast = Toast.makeText(this, "Error en usuario o contraseña",
                                    Toast.LENGTH_LONG)
                                mytoast.setGravity(Gravity.CENTER or Gravity.CENTER, 0, 0)
                                mytoast.show()

                                loginButton.isEnabled = true

                            }
                        }
                    }catch (e:Exception){
                        Log.d("App", "Exception: ${e}")
                        loginButton.isEnabled = true

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

        }else{
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

    }






}
