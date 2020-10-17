package com.chilik1020.hw41.views.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw41.ContactApp
import com.chilik1020.hw41.R
import com.chilik1020.hw41.model.entities.Contact
import com.chilik1020.hw41.util.CONTACT_ID
import com.chilik1020.hw41.views.add.ContactAddActivity
import com.chilik1020.hw41.views.edit.ContactEditActivity
import kotlinx.android.synthetic.main.activity_contacts_list.*

class ContactsListActivity : AppCompatActivity() {

    private val recyclerViewListener: OnRecyclerViewItemClickListener =
        object : OnRecyclerViewItemClickListener {
            override fun onClick(id: Long) {
                val intent = Intent(this@ContactsListActivity, ContactEditActivity::class.java)
                intent.putExtra(CONTACT_ID, id)
                startActivity(intent)
            }
        }

    private val contactAdapter = ContactsRecyclerViewAdapter(recyclerViewListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        initViews()
        setListeners()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)

        recyclerViewContacts.apply {
            adapter = contactAdapter
            layoutManager =
                LinearLayoutManager(this@ContactsListActivity, RecyclerView.VERTICAL, false)
        }
        val list = ContactApp.contacts
        if (list.isEmpty()) {
            tvNoContacts.visibility = View.VISIBLE
        }
        contactAdapter.setData(list)
    }

    private fun setListeners() {
        fab.setOnClickListener {
            startActivity(Intent(this, ContactAddActivity::class.java))
        }
    }
}
