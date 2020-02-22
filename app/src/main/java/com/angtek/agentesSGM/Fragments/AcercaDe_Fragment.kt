package com.angtek.agentesSGM.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            val openNumber = Intent(Intent.ACTION_CALL)
            openNumber.data = Uri.parse("tel:" + "01411611")
            startActivity(openNumber)
        }



    }

}
