package com.angtek.agentesSGM.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.angtek.agentesSGM.Fragments.AcercaDe_Fragment
import com.angtek.agentesSGM.Fragments.Agente_Fragment
import com.angtek.agentesSGM.Fragments.Servicios_Fragment

class TabsAdapter : FragmentStatePagerAdapter {


    // Constructor
    constructor(fm : FragmentManager) : super(fm){

    }


    override fun getItem(p0: Int): Fragment {
        when (p0){
            0 -> return Agente_Fragment()
            1 -> return Servicios_Fragment()
            2 -> return AcercaDe_Fragment()
        }
        return Fragment()
    }


    override fun getCount(): Int {
        return 3
    }

}