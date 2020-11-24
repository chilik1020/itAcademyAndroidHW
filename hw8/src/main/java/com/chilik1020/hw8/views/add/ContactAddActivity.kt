package com.chilik1020.hw8.views.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw8.R
import com.chilik1020.hw8.model.ContactRepository
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import kotlinx.android.synthetic.main.activity_contact_add.etContact
import kotlinx.android.synthetic.main.activity_contact_add.etName
import kotlinx.android.synthetic.main.activity_contact_add.rbEmail
import kotlinx.android.synthetic.main.activity_contact_add.rbPhoneNumber
import kotlinx.android.synthetic.main.activity_contact_add.toolbar
import org.koin.android.ext.android.inject
import java.util.*

class ContactAddActivity : AppCompatActivity() {

    private val viewModel: ContactAddViewModel by inject()
//    private val repository: ContactRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_add)

        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_add_act, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.miConfirmButton) {
            var contactType = ContactType.PHONENUMBER
            if (rbEmail.isChecked) {
                contactType = ContactType.EMAIL
            }
            val id = UUID.randomUUID().toString()
            val name = etName.text.toString()
            val contactInfo = etContact.text.toString()
            val contact = Contact(id, contactType, name, contactInfo)
            viewModel.addContact(contact)
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        toolbar.setNavigationOnClickListener { finish() }
        rbPhoneNumber.setOnClickListener {
            etContact.hint = getString(R.string.act_add_et_phone_number)
        }
        rbEmail.setOnClickListener { etContact.hint = getString(R.string.act_add_et_email) }
    }
}