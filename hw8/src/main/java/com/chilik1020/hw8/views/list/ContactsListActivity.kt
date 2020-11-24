package com.chilik1020.hw8.views.list

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw8.R
import com.chilik1020.hw8.model.ContactRepository
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.util.CONTACT_ID
import com.chilik1020.hw8.util.LOG_TAG_APP
import com.chilik1020.hw8.views.add.ContactAddActivity
import com.chilik1020.hw8.views.edit.ContactEditActivity
import kotlinx.android.synthetic.main.activity_contacts_list.fab
import kotlinx.android.synthetic.main.activity_contacts_list.recyclerViewContacts
import kotlinx.android.synthetic.main.activity_contacts_list.searchView
import kotlinx.android.synthetic.main.activity_contacts_list.toolbar
import kotlinx.android.synthetic.main.activity_contacts_list.tvNoContacts
import org.koin.android.ext.android.inject
import java.util.*

class ContactsListActivity : AppCompatActivity() {

    private val viewModel: ContactsListViewModel by inject()
//    private val repository: ContactRepository by inject()

    private val recyclerViewListener: OnRecyclerViewItemClickListener =
        OnRecyclerViewItemClickListener { id ->
            val intent = Intent(
                this@ContactsListActivity,
                ContactEditActivity::class.java
            ).apply {
                putExtra(CONTACT_ID, id)
            }
            startActivity(intent)
        }

    private val contactAdapter = ContactsRecyclerViewAdapter(recyclerViewListener)
    private var data = listOf<Contact>()
    private var dataFilteredWithHint = listOf<Contact>()
    private var searchViewHint: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        updateDataFromStorage()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)

        recyclerViewContacts.apply {
            adapter = contactAdapter
            layoutManager =
                if (this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@ContactsListActivity, 2)
                } else {
                    LinearLayoutManager(this@ContactsListActivity, RecyclerView.VERTICAL, false)
                }
        }

        fab.setOnClickListener {
            startActivity(Intent(this, ContactAddActivity::class.java))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(LOG_TAG, "onQueryTextSubmit: $query")
                searchViewHint = query ?: ""
                updateFilteredData()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(LOG_TAG, "onQueryTextChange: $newText")
                searchViewHint = newText ?: ""
                updateFilteredData()
                return false
            }
        })
    }

    private fun updateDataFromStorage() {
        data = viewModel.fetchContacts()
        if (data.isEmpty()) {
            tvNoContacts.visibility = View.VISIBLE
            setAdapterData(emptyList())
        } else {
            tvNoContacts.visibility = View.GONE
            updateFilteredData()
        }
    }

    private fun updateFilteredData() {
        filterDataWithHint()
        setAdapterData(dataFilteredWithHint)
    }

    private fun filterDataWithHint() {
        dataFilteredWithHint = if (searchViewHint.isNotEmpty()) {
            data.filter {
                it.fullname
                    .toLowerCase(Locale.getDefault())
                    .contains(searchViewHint.toLowerCase(Locale.getDefault()))
            }
        } else {
            data
        }
    }

    private fun setAdapterData(list: List<Contact>) {
        contactAdapter.setData(list)
    }

    companion object {
        private const val LOG_TAG = "$LOG_TAG_APP:ContactsList"
    }
}
