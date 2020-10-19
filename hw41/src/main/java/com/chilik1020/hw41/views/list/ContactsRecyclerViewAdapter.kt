package com.chilik1020.hw41.views.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw41.R
import com.chilik1020.hw41.model.entities.Contact
import com.chilik1020.hw41.model.entities.ContactType
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsRecyclerViewAdapter(private val listener: OnRecyclerViewItemClickListener) :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder>() {

    private val list: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view, listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(data: List<Contact>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    class ContactViewHolder(
        private val item: View,
        private val listener: OnRecyclerViewItemClickListener
    ) :
        RecyclerView.ViewHolder(item) {
        private lateinit var contact: Contact
        private val ivContactType: ImageView = item.ivContactType
        private val tvFullname: TextView = item.tvFullname
        private val tvPhoneNumberOrEmail: TextView = item.tvPhonenumberOrEmail

        init {
            item.setOnClickListener { listener.onClick(contact.id) }
        }

        fun bind(contact: Contact) {
            this.contact = contact
            when (contact.type) {
                ContactType.PhoneNumber -> {
                    ivContactType.setImageResource(R.drawable.ic_contact_phone)
                    tvPhoneNumberOrEmail.text = contact.number
                }
                ContactType.Email -> {
                    ivContactType.setImageResource(R.drawable.ic_contact_email)
                    tvPhoneNumberOrEmail.text = contact.email
                }
            }
            tvFullname.text = contact.fullname
        }
    }
}