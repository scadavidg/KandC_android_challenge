package com.kandc_android_challenge.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.kandc_android_challenge.R
import com.example.kandc_android_challenge.databinding.ActivityContactsBinding
import com.kandc_android_challenge.data.models.Result.Failure
import com.kandc_android_challenge.data.models.Result.Loading
import com.kandc_android_challenge.data.models.Result.Success
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.ui.ContactsListFragment.ContactsListListener
import com.kandc_android_challenge.utils.ErrorHandler
import com.kandc_android_challenge.utils.FRAG_CONTACTS_LIST
import com.kandc_android_challenge.utils.FRAG_CONTACT_DETAIL
import com.kandc_android_challenge.utils.getDrawableCompat
import com.kandc_android_challenge.utils.loadItemPicture
import com.kandc_android_challenge.utils.setAsInvisible
import com.kandc_android_challenge.utils.setAsVisible
import com.kandc_android_challenge.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsActivity : AppCompatActivity(), ErrorHandler, ContactsListListener {

    private val binding by viewBinding(ActivityContactsBinding::inflate)
    private val contactsViewModel: ContactsViewModel by viewModel()
    private lateinit var selectedContact: Contact
    private lateinit var contacts: ArrayList<Contact>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListeners()
        getContactsAndSetContactsGroups()
        setToolbarOptionsVisible(false)
        binding.toolbar.run {
            icBack.setAsInvisible()
            icBack.setAsInvisible()
        }
    }

    override fun onContactSelected(selectedContact: Contact) {
        this.selectedContact = selectedContact
        binding.toolbar.run {
            setToolbarOptionsVisible(true)
        }
        supportFragmentManager.commit {
            add(
                binding.flFragments.id,
                ContactDetailFragment.newInstance(selectedContact)
            )
            addToBackStack(FRAG_CONTACT_DETAIL)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        with(supportFragmentManager) {
            if ((
                fragments.last() is ContactDetailFragment ||
                    fragments.last() is ElementInfoProfileFragment
                ) &&
                fragments.size > 1
            ) {
                popBackStackImmediate()
                refreshGroups(contacts)
                setToolbarOptionsVisible(false)
            } else {
                finish()
            }
        }
    }

    private fun setListeners() {
        binding.toolbar.run {
            icBack.setOnClickListener {
                onBackPressed()
            }
            icIsFavorite.setOnClickListener {
                val index = contacts.indexOf(selectedContact)
                selectedContact.let {
                    selectedContact.run {
                        isFavorite = !isFavorite
                    }
                }
                contacts.set(index, selectedContact)
                loadItemPicture(
                    getDrawableCompat(
                        when (selectedContact.isFavorite) {
                            true -> R.drawable.favorite
                            false -> R.drawable.not_favorite
                        }
                    ),
                    icIsFavorite
                )
            }
        }
    }

    private fun setToolbarOptionsVisible(willBeVisible: Boolean) {
        binding.toolbar.run {
            if (willBeVisible) {
                icBack.setAsVisible()
                icIsFavorite.setAsVisible()
                selectedContact.let {
                    loadItemPicture(
                        getDrawableCompat(
                            when (it.isFavorite) {
                                true -> R.drawable.favorite
                                false -> R.drawable.not_favorite
                            }
                        ),
                        icIsFavorite
                    )
                }
            } else {
                icBack.setAsInvisible()
                icIsFavorite.setAsInvisible()
            }
        }
    }

    private fun getContactsAndSetContactsGroups() {
        contactsViewModel.getContacts().observe(
            this,
            { result ->
                when (result) {
                    is Failure -> handleError(result.t)
                    is Loading -> {
                    }
                    is Success -> setGroups(result.data)
                }
            }
        )
    }

    private fun setGroups(contactsList: List<Contact>) {
        contacts = ArrayList(contactsList)
        if (contactsList.isNotEmpty()) {
            supportFragmentManager.commit {
                add(
                    binding.flFragments.id,
                    ContactsListFragment.newInstance(contacts)
                )
                addToBackStack(FRAG_CONTACTS_LIST)
            }
        }
    }

    private fun refreshGroups(contactsUpdatedList: ArrayList<Contact>) {
        if (contactsUpdatedList.isNotEmpty()) {
            supportFragmentManager.popBackStackImmediate()
            supportFragmentManager.commit {
                add(
                    binding.flFragments.id,
                    ContactsListFragment.newInstance(contactsUpdatedList)
                )
                addToBackStack(FRAG_CONTACTS_LIST)
            }
        }
    }
}
