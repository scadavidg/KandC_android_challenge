package com.kandc_android_challenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.kandc_android_challenge.R
import com.example.kandc_android_challenge.databinding.FragmentElementInfoProfileBinding
import com.kandc_android_challenge.domain.models.ElementInfoProfile
import com.kandc_android_challenge.utils.ARG_ELEMENT_INFO_PROFILE
import com.kandc_android_challenge.utils.ErrorHandler
import com.kandc_android_challenge.utils.mustHaveParcelableExtra
import com.kandc_android_challenge.utils.setAsVisible
import com.kandc_android_challenge.utils.viewBinding

class ElementInfoProfileFragment : Fragment(R.layout.fragment_element_info_profile), ErrorHandler {

    private val binding by viewBinding(FragmentElementInfoProfileBinding::bind)

    private val elementInfoProfile: ElementInfoProfile by lazy {
        requireArguments().mustHaveParcelableExtra(
            ARG_ELEMENT_INFO_PROFILE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInfo()
    }

    private fun setInfo() {
        binding.run {
            elementInfoProfile.rightLabel?.let {
                tvLabelRight.apply {
                    setAsVisible()
                    text = it
                }
            }
            tvLabelTop.text = elementInfoProfile.topLabel
            tvProperty.text = elementInfoProfile.value
        }
    }

    companion object {
        fun newInstance(elementInfoProfile: ElementInfoProfile) =
            ElementInfoProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ELEMENT_INFO_PROFILE, elementInfoProfile)
                }
            }
    }
}
