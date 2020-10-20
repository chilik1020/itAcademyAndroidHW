package com.chilik1020.hw41.views.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw41.R
import com.chilik1020.hw41.model.ContactRepository
import com.chilik1020.hw41.util.CONTACT_ID
import com.chilik1020.hw41.util.LOG_TAG_APP
import com.chilik1020.hw41.views.add.ContactAddActivity
import com.chilik1020.hw41.views.edit.ContactEditActivity
import kotlinx.android.synthetic.main.activity_contacts_list.*
import org.koin.android.ext.android.inject
import java.util.*

class ContactsListActivity : AppCompatActivity() {
    private val LOG_TAG = "$LOG_TAG_APP:ContactsList"

    private val repository: ContactRepository by inject()

    private val recyclerViewListener: OnRecyclerViewItemClickListener =
        object : OnRecyclerViewItemClickListener {
            override fun onClick(id: UUID) {
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

    override fun onResume() {
        super.onResume()
        val list = repository.getAllContacts()
        if (list.isEmpty()) {
            tvNoContacts.visibility = View.VISIBLE
        }
        contactAdapter.setData(list)
    }

    private fun initViews() {
        setSupportActionBar(toolbar)

        recyclerViewContacts.apply {
            adapter = contactAdapter
            layoutManager =
                LinearLayoutManager(this@ContactsListActivity, RecyclerView.VERTICAL, false)
        }
    }

    private fun setListeners() {
        fab.setOnClickListener {
            startActivity(Intent(this, ContactAddActivity::class.java))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(LOG_TAG, "onQueryTextSubmit: $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(LOG_TAG, "onQueryTextChange: $newText")
                return false
            }
        })
    }
}
