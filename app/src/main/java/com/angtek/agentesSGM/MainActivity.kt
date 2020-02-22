package com.angtek.agentesSGM

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.angtek.agentesSGM.Activities.LoginActivity
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timer().schedule(timerTask {

            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()


        }, 3000)

    }


}
