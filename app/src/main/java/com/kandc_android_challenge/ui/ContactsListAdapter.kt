package com.kandc_android_challenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kandc_android_challenge.databinding.ElementContactBinding
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.ui.ContactsListFragment.ContactsListListener
import com.kandc_android_challenge.utils.replaceItems

class ContactsListAdapter(private val listener: ContactsListListener) :
    RecyclerView.Adapter<ContactsListViewHolder>() {

    private var contactsList = ArrayList<Contact>()

    fun setContacts(contactsRequested: ArrayList<Contact>) {
        contactsList.replaceItems(contactsRequested)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsListViewHolder =
        ContactsListViewHolder(
            ElementContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = contactsList.size

    override fun onBindViewHolder(holder: ContactsListViewHolder, position: Int) {
        holder.bind(contactsList[position], listener, position)
    }
}
