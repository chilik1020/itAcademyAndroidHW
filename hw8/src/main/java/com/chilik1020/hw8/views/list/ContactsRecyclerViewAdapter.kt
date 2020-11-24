package com.chilik1020.hw8.views.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.hw8.R
import com.chilik1020.hw8.databinding.ItemContactBinding
import com.chilik1020.hw8.model.entities.Contact
import com.chilik1020.hw8.util.LOG_TAG_APP

class ContactsRecyclerViewAdapter(private val listener: OnRecyclerViewItemClickListener) :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactViewHolder>() {

    private var list: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemContactBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_contact, parent, false)
        return ContactViewHolder(binding, listener)
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
        private val binding: ItemContactBinding,
        private val listener: OnRecyclerViewItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Contact) {
            binding.apply {
                contact = item
                executePendingBindings()
            }
            Log.d(LOG_TAG_APP, "Binded ${binding.contact?.fullname}")
        }
    }

//        init {
//            binding.root.setOnClickListener { listener.onClick(contact.id) }
//        }

//        fun bind(contact: Contact) {
//            this.contact = contact
//            when (contact.type) {
//                ContactType.PHONENUMBER -> {
//                    item.ivContactType.setImageResource(R.drawable.ic_contact_phone)
//                }
//                ContactType.EMAIL -> {
//                    item.ivContactType.setImageResource(R.drawable.ic_contact_email)
//                }
//            }
//            item.tvFullname.text = contact.fullname
//            item.tvPhonenumberOrEmail.text = contact.contactInfo
//        }
}