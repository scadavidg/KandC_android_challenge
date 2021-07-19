package com.kandc_android_challenge.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.kandc_android_challenge.R
import com.example.kandc_android_challenge.databinding.ElementContactBinding
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.ui.ContactsListFragment.ContactsListListener
import com.kandc_android_challenge.utils.getDrawableCompat
import com.kandc_android_challenge.utils.loadItemPicture

class ContactsListViewHolder(private val binding: ElementContactBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(contact: Contact, listener: ContactsListListener, position: Int) {

        binding.run {
            root.setOnClickListener {
                listener.onContactSelected(contact)
            }
            loadItemPicture(contact.smallImageURL, ivContactPicture)
            loadItemPicture(
                getDrawableCompat(
                    when (contact.isFavorite) {
                        true -> R.drawable.favorite
                        false -> R.drawable.not_favorite
                    }
                ),
                icIsFavorite
            )
            tvContactName.text = contact.name
            tvCompanyName.text = contact.companyName
        }
    }
}
