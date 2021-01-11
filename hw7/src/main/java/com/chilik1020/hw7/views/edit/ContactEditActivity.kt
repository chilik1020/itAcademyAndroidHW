package com.chilik1020.hw7.views.edit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw7.R
import com.chilik1020.hw7.model.ContactRepository
import com.chilik1020.hw7.model.entities.Contact
import com.chilik1020.hw7.model.entities.ContactType
import com.chilik1020.hw7.util.CONTACT_ID
import kotlinx.android.synthetic.main.activity_contact_edit.btnRemoveContact
import kotlinx.android.synthetic.main.activity_contact_edit.etContact
import kotlinx.android.synthetic.main.activity_contact_edit.etName
import kotlinx.android.synthetic.main.activity_contact_edit.toolbar
import org.koin.android.ext.android.inject

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
                it.apply {
                    fullname = etName.text.toString()
                    contactInfo = etContact.text.toString()
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
        contact = intent?.let { repository.getById(it.getSerializableExtra(CONTACT_ID) as String) }

        contact?.let {
            etName.setText(it.fullname, TextView.BufferType.EDITABLE)

            when (it.type) {
                ContactType.PHONENUMBER -> {
                    setEtContactAttr(getString(R.string.act_edit_et_phone_number), it.contactInfo)
                }
                ContactType.EMAIL -> {
                    setEtContactAttr(getString(R.string.act_edit_et_email), it.contactInfo)
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