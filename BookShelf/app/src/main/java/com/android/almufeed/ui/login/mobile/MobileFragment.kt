package com.android.almufeed.ui.login.mobile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.almufeed.R
import com.android.almufeed.business.domain.utils.collectLatestFlow
import com.android.almufeed.business.domain.utils.errorListener
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.databinding.FragmentMobileBinding
import com.android.almufeed.ui.launchpad.DashboardActivity
import com.android.almufeed.ui.launchpad.LaunchpadActivity
import com.android.almufeed.ui.personalInfo.PersonalInfoActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern

@AndroidEntryPoint
class MobileFragment : Fragment(R.layout.fragment_mobile) {

    private val mobileViewModel: MobileViewModel by viewModels()
    private lateinit var binding: FragmentMobileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMobileBinding.bind(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.apply {

            cardMobileNumber.errorListener(binding.etMobile)
            progressBar.visibility = View.INVISIBLE

            etMobile.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int, p2: Int, p3: Int
                ) {
                }

                override fun onTextChanged(
                    p0: CharSequence?,
                    p1: Int, p2: Int, p3: Int
                ) {
                    // check inputted characters is a valid phone number or not
                    if (p0?.length!! < 5) {
                        mobileViewModel.valid = true
                        etMobile.error = null
                    } else {
                        mobileViewModel.valid = false
                        // etMobile.error = "Invalid phone number."
                    }
                    /*if (p0?.length!! < 11) {
                        mobileViewModel.valid = true
                        etMobile.error = null
                    } else {
                        mobileViewModel.valid = false
                        // etMobile.error = "Invalid phone number."
                    }*/
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
            btnSignIn.setOnClickListener {
                if(binding.progressBar.visibility != View.VISIBLE) {
                    if (mobileViewModel.valid) {
                        val pattern: Pattern = Pattern.compile("^[1-4]")
                        val matcher: Matcher = pattern.matcher(binding.etMobile.text.toString().first().toString())
                        if (binding.etMobile.text.toString() == "1234") {
                            mobileViewModel.updatePersonalInfo(binding.etMobile.text.toString())
                        } else {
                            binding.cardMobileNumber.error = "Please enter a valid user id"
                        }
                    } else {
                        binding.cardMobileNumber.error = "Please enter a valid user id"
                    }
                }
            }
        }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        collectLatestFlow(mobileViewModel.taskEvent) { event ->

            when (event) {
                is TaskEvent.NavigateToPersonalInfo -> {
                    gotoPersonalInfoPage()
                }
                is TaskEvent.NavigateToLaunchpad -> {
                    gotoLaunchpadPage()
                }
            }.exhaustive
        }
    }

    private fun gotoPersonalInfoPage() {
        Intent(requireActivity(), PersonalInfoActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun gotoLaunchpadPage() {
        Intent(requireActivity(), DashboardActivity::class.java).apply {
            startActivity(this)
        }
    }
}


