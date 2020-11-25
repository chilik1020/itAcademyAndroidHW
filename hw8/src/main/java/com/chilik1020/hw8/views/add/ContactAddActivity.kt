package com.chilik1020.hw8.views.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chilik1020.hw8.R
import com.chilik1020.hw8.databinding.ActivityContactAddBinding
import kotlinx.android.synthetic.main.activity_contact_add.etContact
import kotlinx.android.synthetic.main.activity_contact_add.rbEmail
import kotlinx.android.synthetic.main.activity_contact_add.rbPhoneNumber
import kotlinx.android.synthetic.main.activity_contact_add.toolbar
import org.koin.android.ext.android.inject

class ContactAddActivity : AppCompatActivity() {

    private val viewModelAdd: ContactAddViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityContactAddBinding>(
            this,
            R.layout.activity_contact_add
        ).run {
            lifecycleOwner = this@ContactAddActivity
            viewModel = viewModelAdd
        }

        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_add_act, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.miConfirmButton) {
            viewModelAdd.saveContact()
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