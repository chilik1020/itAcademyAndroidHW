package com.chilik1020.hw8.views.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chilik1020.hw8.R
import com.chilik1020.hw8.databinding.ActivityContactEditBinding
import com.chilik1020.hw8.util.CONTACT_ID
import kotlinx.android.synthetic.main.activity_contact_edit.btnRemoveContact
import kotlinx.android.synthetic.main.activity_contact_edit.toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactEditActivity : AppCompatActivity() {

    private val viewModelEdit: ContactEditViewModel by viewModel()
    private var contactId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityContactEditBinding>(
            this,
            R.layout.activity_contact_edit
        )
            .run {
                lifecycleOwner = this@ContactEditActivity
                viewModel = viewModelEdit
            }

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
            viewModelEdit.editContact()
            finish()
        }

        btnRemoveContact.setOnClickListener {
            viewModelEdit.deleteContact()
            finish()
        }
    }

    private fun getIntentData() {
        contactId = intent?.let { it.getSerializableExtra(CONTACT_ID) as String }
        contactId?.let { viewModelEdit.getContactById(it) }
    }
}