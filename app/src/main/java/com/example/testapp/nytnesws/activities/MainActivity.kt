package com.example.testapp.nytnesws.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.fragments.TabFragment


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.container_inside, TabFragment())
        ft.addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        if(fragmentManager.backStackEntryCount>0){
            fragmentManager.popBackStack()
        } else
            super.onBackPressed()

    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

}
