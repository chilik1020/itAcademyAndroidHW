package com.chilik1020.hw41.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw41.R
import com.chilik1020.hw41.model.entities.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsRecyclerViewAdapter :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder>() {

    private val list: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
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

    class ContactViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val ivContactType: ImageView = item.ivContactType
        private val tvFullname: TextView = item.tvFullname
        private val tvPhoneNumberOrEmail: TextView = item.tvPhonenumberOrEmail

        fun bind(contact: Contact) {
            if (contact.type) {
                ivContactType.setImageResource(R.drawable.ic_contact_phone)
                tvPhoneNumberOrEmail.text = contact.number
            } else {
                ivContactType.setImageResource(R.drawable.ic_contact_email)
                tvPhoneNumberOrEmail.text = contact.email
            }
            tvFullname.text = contact.fullname

        }
    }
}