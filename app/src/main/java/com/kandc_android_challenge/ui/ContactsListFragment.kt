package com.kandc_android_challenge.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kandc_android_challenge.R
import com.example.kandc_android_challenge.databinding.FragmentContactsListBinding
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.utils.ARG_CONTACTS_LIST
import com.kandc_android_challenge.utils.ErrorHandler
import com.kandc_android_challenge.utils.mustHaveSerializableExtra
import com.kandc_android_challenge.utils.setAsVisible
import com.kandc_android_challenge.utils.shouldImplement
import com.kandc_android_challenge.utils.viewBinding

class ContactsListFragment : Fragment(R.layout.fragment_contacts_list), ErrorHandler {

    private val binding by viewBinding(FragmentContactsListBinding::bind)

    private val contactsList: ArrayList<Contact> by lazy {
        requireArguments().mustHaveSerializableExtra(
            ARG_CONTACTS_LIST
        )
    }
    private var contactsListListener: ContactsListListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setContacts()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contactsListListener = context.shouldImplement(ContactsListListener::class.java)
    }

    override fun onDetach() {
        super.onDetach()
        contactsListListener = null
    }

    companion object {
        fun newInstance(contacts: ArrayList<Contact>) =
            ContactsListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_CONTACTS_LIST, contacts)
                }
            }
    }

    private fun setRecyclerView() {
        binding.run {
            rvFavoriteContacts.apply {
                adapter = ContactsListAdapter(contactsListListener!!)
                layoutManager = LinearLayoutManager(context)
            }
            rvOthersContacts.apply {
                adapter = ContactsListAdapter(contactsListListener!!)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun setContacts() {
        val favoritesContacts =
            contactsList.filter { contact -> contact.isFavorite }.sortedBy { it.name }
        val otherContacts =
            contactsList.filter { contact -> !contact.isFavorite }.sortedBy { it.name }

        binding.run {
            rvFavoriteContacts.apply {
                (adapter as ContactsListAdapter).setContacts(ArrayList(favoritesContacts))
                setAsVisible()
            }

            rvOthersContacts.apply {
                (adapter as ContactsListAdapter).setContacts(ArrayList(otherContacts))
                setAsVisible()
            }
        }
    }

    interface ContactsListListener {
        fun onContactSelected(selectedContact: Contact)
    }
}
