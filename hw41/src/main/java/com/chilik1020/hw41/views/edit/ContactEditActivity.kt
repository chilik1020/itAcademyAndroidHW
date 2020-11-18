package com.chilik1020.hw41.views.edit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw41.R
import com.chilik1020.hw41.model.ContactRepository
import com.chilik1020.hw41.model.entities.Contact
import com.chilik1020.hw41.model.entities.ContactType
import com.chilik1020.hw41.util.CONTACT_ID
import kotlinx.android.synthetic.main.activity_contact_edit.btnRemoveContact
import kotlinx.android.synthetic.main.activity_contact_edit.etContact
import kotlinx.android.synthetic.main.activity_contact_edit.etName
import kotlinx.android.synthetic.main.activity_contact_edit.toolbar
import org.koin.android.ext.android.inject
import java.util.*

class ContactEditActivity : AppCompatActivity() {

    private val repository: ContactRepository by inject()
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_edit)

        initViews()
        getIntentData()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.setNavigationOnClickListener {
            contact?.let {
                it.fullname = etName.text.toString()
                when (it.type) {
                    ContactType.PhoneNumber -> it.number = etContact.text.toString()
                    ContactType.Email -> it.email = etContact.text.toString()
                }
                repository.editContact(it)
            }
            finish()
        }

        btnRemoveContact.setOnClickListener {
            contact?.let { c -> repository.removeContact(c.id) }
            finish()
        }
    }

    private fun getIntentData() {
        contact = intent?.let { repository.getById(it.getSerializableExtra(CONTACT_ID) as UUID) }

        contact?.let {
            etName.setText(it.fullname, TextView.BufferType.EDITABLE)

            when (it.type) {
                ContactType.PhoneNumber -> {
                    setEtContactAttr(getString(R.string.act_edit_et_phone_number), it.number)
                }
                ContactType.Email -> {
                    setEtContactAttr(getString(R.string.act_edit_et_email), it.email)
                }
            }
        }
    }

    private fun setEtContactAttr(hint: String, text: String) {
        etContact.hint = hint
        etContact.setText(
            text,
            TextView.BufferType.EDITABLE
        )
    }
}