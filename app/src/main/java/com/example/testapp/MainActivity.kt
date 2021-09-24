package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    var actionBar: ActionBar? = null
    private lateinit var toolbar: Toolbar
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        recyclerView = findViewById(R.id.recyclerView);
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        toolbar = findViewById(R.id.toolbar)
        actionBar = supportActionBar
        if (actionBar == null) {
            setSupportActionBar(toolbar)
            actionBar = supportActionBar
            actionBar!!.elevation = 4.0f
            actionBar!!.setDisplayHomeAsUpEnabled(false)
            actionBar!!.hide()
        }
        actionBar!!.hide()
        actionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar!!.setDisplayShowHomeEnabled(false)
    }
}