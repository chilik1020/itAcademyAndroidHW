package com.chilik1020.hw41.views

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw41.R
import com.chilik1020.hw41.model.entities.Contact
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val contactAdapter = ContactsRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setListeners()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        recyclerViewContacts.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }
        val list = mutableListOf<Contact>()
        for (i in 0..19) {
            list.add(Contact(i%2==0, "Aaron Bennet", "+3752258734546", "email@gmail.com"))
        }
//        list.clear()
        if (list.isEmpty()){
            tvNoContacts.visibility = View.VISIBLE
        }
        contactAdapter.setData(list)
    }

    private fun setListeners() {
        fab.setOnClickListener {
            Toast.makeText(this, "Contact creating", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.BOTTOM, 0, 0)
                show()
            }
        }
    }
}
