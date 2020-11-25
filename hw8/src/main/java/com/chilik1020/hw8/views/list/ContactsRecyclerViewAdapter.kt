package com.chilik1020.hw8.views.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw8.R
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.model.entities.ContactType
import kotlinx.android.synthetic.main.item_contact.view.ivContactType
import kotlinx.android.synthetic.main.item_contact.view.tvFullname
import kotlinx.android.synthetic.main.item_contact.view.tvPhonenumberOrEmail

class ContactsRecyclerViewAdapter(private val listener: OnRecyclerViewItemClickListener) :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder>() {

    private var list: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view, listener)
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
        private val item: View,
        private val listener: OnRecyclerViewItemClickListener
    ) :
        RecyclerView.ViewHolder(item) {
        private lateinit var contact: Contact

        init {
            item.setOnClickListener { listener.onClick(contact.id) }
        }

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