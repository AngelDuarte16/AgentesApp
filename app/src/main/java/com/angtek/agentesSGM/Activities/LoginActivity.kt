package com.angtek.agentesSGM.Activities

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import android.os.Handler
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.angtek.agentesSGM.R



class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        emailET.setText("70426677")
        passwordET.setText("9765292")


        //credenciales

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE)
                ActivityCompat.requestPermissions(this, permissions, 0)
        } else {
            // Permission has already been granted
        }

        loginButton.setOnClickListener {
            loginButton.isEnabled = false
            loginPressed()
        }
    }



    fun loginPressed() {

        loginButton.setBackgroundColor(Color.parseColor("#666666"))

        val progressDilog = ProgressDialog(this)
        progressDilog.setMessage("Espere un momento")
        progressDilog.setCancelable(false)
        progressDilog.show()

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

            val request = JsonObjectRequest(Request.Method.POST,url,myjson,
                Response.Listener { response ->
                    try {
                        val json : JSONObject = JSONObject(response.toString())
                        var code  = json.getString("Code")
                        if(code == "0000"){
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

                                if(password == User.CODE){


                                    savesession("user",emailET.text.toString())
                                    savesession("pass",passwordET.text.toString())


                                    emailET.setText("")
                                    passwordET.setText("")

                                    loginButton.isEnabled = true

                                    loginButton.setBackgroundColor(Color.parseColor("#ffffff"))

                                    Handler().postDelayed({progressDilog.dismiss()},100)




                                    val intent = Intent(this, TabsActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                }else{
                                    loginButton.isEnabled = true
                                    loginButton.setBackgroundColor(Color.parseColor("#ffffff"))

                                    Handler().postDelayed({progressDilog.dismiss()},100)
                                    var mytoast = Toast.makeText(this, "Error en usuario o contraseña",
                                        Toast.LENGTH_LONG)
                                    mytoast.setGravity(Gravity.CENTER or Gravity.CENTER, 0, 0)
                                    mytoast.show()

                                }

                            }else{
                                loginButton.setBackgroundColor(Color.parseColor("#ffffff"))
                                Handler().postDelayed({progressDilog.dismiss()},100)
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
                        loginButton.setBackgroundColor(Color.parseColor("#ffffff"))

                    }

                }, Response.ErrorListener{
                    loginButton.isEnabled = true
                    loginButton.setBackgroundColor(Color.parseColor("#ffffff"))
                    Log.d("App", "Error: ${it}")
                    Handler().postDelayed({progressDilog.dismiss()},100)
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

        }else{
            loginButton.isEnabled = true
            loginButton.setBackgroundColor(Color.parseColor("#ffffff"))
            progressDilog.dismiss()
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

    }


    fun savesession(key: String, response: String){

        val preferences = this.getSharedPreferences("USUARIO",Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(key,response)
        editor.commit()

    }


}