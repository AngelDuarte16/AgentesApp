package com.angtek.agentesSGM.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.angtek.agentesSGM.Adapters.TabsAdapter
import com.angtek.agentesSGM.R
import kotlinx.android.synthetic.main.activity_tabs.*
import kotlinx.android.synthetic.main.layout_tab.view.*

class TabsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        tabsViewPager.adapter = TabsAdapter(supportFragmentManager)
        tabsViewPager.setPageTransformer(true,
            com.ToxicBakery.viewpager.transforms.DefaultTransformer())




        tabLayout.setupWithViewPager(tabsViewPager)
        configureTab(0, R.drawable.ic_person, "1")
        configureTab(1, R.drawable.ic_map, "2")
        configureTab(2, R.drawable.ic_settings, "3")

        var a = 10
        var b = a + 10
        //ok
    }



    fun configureTab(position : Int, icon : Int, text : String){

        var tabView : View = View.inflate(this, R.layout.layout_tab_new, null)

        tabView.tabIcon.setImageResource(icon)

        tabLayout?.getTabAt(position)?.setCustomView(tabView)

    }
}
