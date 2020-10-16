package com.chilik1020.hw41.views.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw41.R
import kotlinx.android.synthetic.main.activity_contact_edit.*

class ContactEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_edit)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
  //      supportActionBar?.setHomeButtonEnabled(true)
    }
}