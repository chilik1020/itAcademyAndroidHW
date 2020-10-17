package com.chilik1020.hw41.views.edit

import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw41.ContactApp
import com.chilik1020.hw41.R
import com.chilik1020.hw41.util.CONTACT_ID
import kotlinx.android.synthetic.main.activity_contact_edit.*

class ContactEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_edit)

        initViews()
        intent?.let {
            val id = it.getLongExtra(CONTACT_ID,0)
            val contact = ContactApp.contacts[id.toInt()]
            etName.setText(contact.fullname, TextView.BufferType.EDITABLE)

            if (contact.type) {
                etContact.setText(contact.number, TextView.BufferType.EDITABLE)
            } else {
                etContact.setText(contact.email, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}