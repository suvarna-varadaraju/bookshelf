package com.android.almufeed.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.android.almufeed.R
import com.android.bookself.ui.splash.SplashScreenViewModel
import com.android.almufeed.business.domain.utils.collectLatestFlow
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.business.domain.utils.toast
import com.android.almufeed.databinding.ActivitySplashScreenBinding
import com.android.almufeed.ui.base.BaseActivity
import com.android.almufeed.ui.launchpad.DashboardActivity
import com.android.almufeed.ui.launchpad.LaunchpadActivity
import com.android.almufeed.ui.login.LoginActivity
import com.android.almufeed.ui.personalInfo.PersonalInfoActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : BaseActivity() {
    val TIME_OUT : Long = 2000
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var snack: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            snack = Snackbar.make(
                root,
                getString(R.string.text_network_not_available),
                Snackbar.LENGTH_INDEFINITE
            )
        }

        binding.imgSplash.postDelayed({
            subscribeObservers()
            splashScreenViewModel.initViewModel()
        }, TIME_OUT)
    }

    private fun subscribeObservers() {
        collectLatestFlow(splashScreenViewModel.splashChannelFlow) { event ->
            when (event) {
                SplashScreenViewModel.SplashEvent.NavigateToLoginActivity -> {
                    gotoLoginPage()
                }
                SplashScreenViewModel.SplashEvent.NavigateToPersonalInfoActivity -> {
                    gotoPersonalInfoPage()
                }
                SplashScreenViewModel.SplashEvent.NavigateToLaunchpadActivity -> {
                    gotoLaunchpadPage()
                }
                else -> {}
            }.exhaustive
        }
    }

    private fun gotoLoginPage() {
        Intent(this@SplashScreen, LoginActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private fun gotoPersonalInfoPage() {
         Intent(this@SplashScreen, PersonalInfoActivity::class.java).apply {
             startActivity(this)
         }
         finish()
    }

    private fun gotoLaunchpadPage() {
         Intent(this@SplashScreen, DashboardActivity::class.java).apply {
             startActivity(this)
         }
         finish()
    }

    override fun showNetworkSnackBar(isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            if (snack.isShown) {
                this.toast(getString(R.string.text_network_connected))
                snack.dismiss()
            }
        } else {
            snack.show()
        }
    }
}