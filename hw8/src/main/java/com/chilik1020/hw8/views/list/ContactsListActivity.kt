package com.chilik1020.hw8.views.list

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw8.R
import com.chilik1020.hw8.databinding.ActivityContactsListBinding
import com.chilik1020.hw8.util.CONTACT_ID
import com.chilik1020.hw8.views.add.ContactAddActivity
import com.chilik1020.hw8.views.edit.ContactEditActivity
import com.chilik1020.hw8.views.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_contacts_list.fab
import kotlinx.android.synthetic.main.activity_contacts_list.recyclerViewContacts
import kotlinx.android.synthetic.main.activity_contacts_list.searchView
import kotlinx.android.synthetic.main.activity_contacts_list.toolbar
import org.koin.android.ext.android.inject

class ContactsListActivity : AppCompatActivity() {

    private val viewModelList: ContactsListViewModel by inject()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_list_act, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.miSettings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
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

        fab.setOnClickListener {
            startActivity(Intent(this, ContactAddActivity::class.java))
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
