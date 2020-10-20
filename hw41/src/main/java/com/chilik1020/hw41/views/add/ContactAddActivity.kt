package com.chilik1020.hw41.views.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw41.R
import com.chilik1020.hw41.model.ContactRepository
import com.chilik1020.hw41.model.entities.Contact
import com.chilik1020.hw41.model.entities.ContactType
import kotlinx.android.synthetic.main.activity_contact_add.*
import org.koin.android.ext.android.inject

class ContactAddActivity : AppCompatActivity() {

    private val repository: ContactRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_add)

        initViews()
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_add_act, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.miConfirmButton) {
            var contactType: ContactType = ContactType.PhoneNumber
            if (rbEmail.isChecked) {
                contactType = ContactType.Email
            }
            val id = repository.getLargestId() + 1
            val name = etName.text.toString()
            val contactNumber = etContact.text.toString()
            val contact = when (contactType) {
                ContactType.PhoneNumber -> Contact(id, contactType, name, number = contactNumber)
                ContactType.Email -> Contact(id, contactType, name, email = contactNumber)
            }
            repository.addContact(contact)
            finish()
            true
        } else
            super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    private fun setListeners() {
        toolbar.setNavigationOnClickListener { finish() }
        rbPhoneNumber.setOnClickListener {
            etContact.hint = getString(R.string.act_add_et_phone_number)
        }
        rbEmail.setOnClickListener { etContact.hint = getString(R.string.act_add_et_email) }
    }
}