package com.chilik1020.hw11.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw11.R
import com.chilik1020.hw11.data.Contact
import com.chilik1020.hw11.data.ContactType
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsRecyclerViewAdapter :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder>() {

    private var list: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(data: List<Contact>) {
        list = data
        notifyDataSetChanged()
    }

    class ContactViewHolder(
        private val item: View
    ) :
        RecyclerView.ViewHolder(item) {
        private lateinit var contact: Contact

        fun bind(contact: Contact) {
            this.contact = contact
            when (contact.type) {
                ContactType.PHONENUMBER -> {
                    item.ivContactType.setImageResource(R.drawable.ic_contact_phone)
                }
                ContactType.EMAIL -> {
                    item.ivContactType.setImageResource(R.drawable.ic_contact_email)
                }
            }
            item.tvFullname.text = contact.fullname
            item.tvPhonenumberOrEmail.text = contact.contactInfo
        }
    }
}