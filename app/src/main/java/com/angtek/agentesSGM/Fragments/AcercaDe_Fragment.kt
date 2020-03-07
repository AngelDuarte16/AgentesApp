package com.angtek.agentesSGM.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.acercade_fragment_layout.*


class AcercaDe_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.acercade_fragment_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        gglink.setOnClickListener(){
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("http://www.grupogss.com/")
            startActivity(openURL)
        }



        cadlink.setOnClickListener(){
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.cadlan.com/")
            startActivity(openURL)
        }


        numerocad.setOnClickListener(){
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "(55)91380799"))
            startActivity(intent)
        }

        numerogss.setOnClickListener(){
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "01411611"))
            startActivity(intent)
        }

        correogss.setOnClickListener(){

            val mIntent = Intent(Intent.ACTION_SEND)

            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL,"movilidadgss@grupogss.com" )
            mIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto desde App agentes")
            //put the message in the intent
            mIntent.putExtra(Intent.EXTRA_TEXT, "Para GSS...")


            try {
                //start email intent
                startActivity(Intent.createChooser(mIntent, "Seleccione su proveedor de email"))
            }
            catch (e: Exception){
                //if any thing goes wrong for example no email client application or any exception
                //get and show exception message
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }

        }


        //Hola a todos

        correocad.setOnClickListener(){

            val mIntent = Intent(Intent.ACTION_SEND)

            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_EMAIL,"apps.cadlan@gmail.com" )
            mIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto desde App agentes")
            //put the message in the intent
            mIntent.putExtra(Intent.EXTRA_TEXT, "Para Cad&Lan...")


            try {
                //start email intent
                startActivity(Intent.createChooser(mIntent, "Seleccione su proveedor de email"))
            }
            catch (e: Exception){
                //if any thing goes wrong for example no email client application or any exception
                //get and show exception message
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }

        }


    }

}
