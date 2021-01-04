package com.chilik1020.hw11.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw11.R
import com.chilik1020.hw11.databinding.ActivityContactsListBinding
import kotlinx.android.synthetic.main.activity_contacts_list.*
import org.koin.android.ext.android.inject


class ContactsListActivity : AppCompatActivity() {

    private val viewModelList: ContactsListViewModel by inject()
    private val contactAdapter = ContactsRecyclerViewAdapter()

    private var searchViewHint: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityContactsListBinding>(
            this,
            R.layout.activity_contacts_list
        ).run {
            lifecycleOwner = this@ContactsListActivity
            viewModel = viewModelList
            adapter = contactAdapter
        }

        viewModelList.contactsFiltered.observe(this) {
            contactAdapter.setData(it)
        }

        initViews()
    }

    override fun onRestart() {
        super.onRestart()
        viewModelList.fetchContacts()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)

        recyclerViewContacts.apply {
            layoutManager =
                if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@ContactsListActivity, 2)
                } else {
                    LinearLayoutManager(this@ContactsListActivity, RecyclerView.VERTICAL, false)
                }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewHint = query ?: ""
                updateFilteredData()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewHint = newText ?: ""
                updateFilteredData()
                return false
            }
        })
    }

    private fun updateFilteredData() {
        viewModelList.updateFilterHint(searchViewHint)
    }
}