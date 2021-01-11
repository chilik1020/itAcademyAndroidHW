package com.chilik1020.hw8.views.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chilik1020.hw8.R
import com.chilik1020.hw8.util.REPOSITORY_TYPE_KEY
import com.chilik1020.hw8.util.SHARED_PREF_NAME
import com.chilik1020.hw8.util.TYPE_COMPLETABLE_FUTURE
import com.chilik1020.hw8.util.TYPE_HANDLER
import com.chilik1020.hw8.util.TYPE_RX_JAVA
import com.chilik1020.hw8.views.list.ContactsListActivity
import kotlinx.android.synthetic.main.activity_settings.rbRepositoryCompletableFuture
import kotlinx.android.synthetic.main.activity_settings.rbRepositoryHandler
import kotlinx.android.synthetic.main.activity_settings.rbRepositoryRxJava

class SettingsActivity : AppCompatActivity() {
    private var currentRepositoryType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initViews()
    }

    override fun onPause() {
        super.onPause()
        currentRepositoryType?.let {
            if (it != getRepositoryTypeFromSharedPreferences()) {
                val intent = Intent(this, ContactsListActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                startActivity(intent)
            }
        }
    }

    private fun initViews() {
        currentRepositoryType = getRepositoryTypeFromSharedPreferences()
        when (currentRepositoryType) {
            TYPE_HANDLER -> rbRepositoryHandler.isChecked = true
            TYPE_COMPLETABLE_FUTURE -> rbRepositoryCompletableFuture.isChecked = true
            TYPE_RX_JAVA -> rbRepositoryRxJava.isChecked = true
        }

        rbRepositoryHandler.setOnClickListener {
            saveRepositoryTypeInSharedPreferences(
                TYPE_HANDLER
            )
        }

        rbRepositoryCompletableFuture.setOnClickListener {
            saveRepositoryTypeInSharedPreferences(
                TYPE_COMPLETABLE_FUTURE
            )
        }
        rbRepositoryRxJava.setOnClickListener {
            saveRepositoryTypeInSharedPreferences(
                TYPE_RX_JAVA
            )
        }
    }

    private fun saveRepositoryTypeInSharedPreferences(type: String) {
        getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
            .edit()
            .putString(REPOSITORY_TYPE_KEY, type)
            .apply()
    }

    private fun getRepositoryTypeFromSharedPreferences() =
        getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
            .getString(REPOSITORY_TYPE_KEY, TYPE_HANDLER)

}