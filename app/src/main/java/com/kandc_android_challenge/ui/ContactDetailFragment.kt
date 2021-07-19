package com.kandc_android_challenge.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.kandc_android_challenge.R
import com.example.kandc_android_challenge.databinding.FragmentContactDetailBinding
import com.kandc_android_challenge.domain.models.Contact
import com.kandc_android_challenge.domain.models.ElementInfoProfile
import com.kandc_android_challenge.utils.ARG_CONTACT
import com.kandc_android_challenge.utils.ErrorHandler
import com.kandc_android_challenge.utils.loadItemPicture
import com.kandc_android_challenge.utils.mustHaveParcelableExtra
import com.kandc_android_challenge.utils.setAsInvisible
import com.kandc_android_challenge.utils.viewBinding

class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail), ErrorHandler {

    private val binding by viewBinding(FragmentContactDetailBinding::bind)

    private val contact: Contact by lazy { requireArguments().mustHaveParcelableExtra(ARG_CONTACT) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContact()
    }

    private fun setContact() {
        contact.let {
            binding.run {
                tvContactName.text = it.name
                loadItemPicture(it.largeImageURL, ivContactPicture)

                it.phone.home?.let {
                    setElementInfoProfile(
                        ElementInfoProfile(
                            getString(R.string.label_phone),
                            it,
                            getString(R.string.label_phone_home)
                        ),
                        flPhoneHome
                    )
                } ?: flPhoneHome.setAsInvisible()

                it.phone.mobile?.let {
                    setElementInfoProfile(
                        ElementInfoProfile(
                            getString(R.string.label_phone),
                            it,
                            getString(R.string.label_phone_mobile)
                        ),
                        flPhoneHomeMobile
                    )
                } ?: flPhoneHomeMobile.setAsInvisible()

                it.address?.let {
                    it.run {
                        val value = "$street, $city, $zipCode, $state, $country"
                        setElementInfoProfile(
                            ElementInfoProfile(
                                getString(R.string.label_address),
                                value,
                                null
                            ),
                            flAddress
                        )
                    }
                } ?: flAddress.setAsInvisible()

                it.birthdate?.let {
                    setElementInfoProfile(
                        ElementInfoProfile(
                            getString(R.string.label_birthdate),
                            it,
                            null
                        ),
                        flBirthdate
                    )
                } ?: flBirthdate.setAsInvisible()

                it.emailAddress?.let {
                    setElementInfoProfile(
                        ElementInfoProfile(
                            getString(R.string.label_email),
                            it,
                            null
                        ),
                        flEmail
                    )
                } ?: flEmail.setAsInvisible()
            }
        }
    }

    companion object {
        fun newInstance(contact: Contact) =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONTACT, contact)
                }
            }
    }

    private fun setElementInfoProfile(elementInfoProfile: ElementInfoProfile, fl: FrameLayout) {
        requireActivity().supportFragmentManager.commit {
            replace(
                fl.id,
                ElementInfoProfileFragment.newInstance(elementInfoProfile)
            )
        }
    }
}
