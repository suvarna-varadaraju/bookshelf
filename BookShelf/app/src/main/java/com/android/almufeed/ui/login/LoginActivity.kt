package com.android.almufeed.ui.login

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.almufeed.R
import com.android.almufeed.business.domain.utils.collectLatestFlow2
import com.android.almufeed.business.domain.utils.exhaustive
import com.android.almufeed.business.domain.utils.toast
import com.android.almufeed.databinding.ActivityLoginBinding
import com.android.almufeed.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var navController: NavController
    private lateinit var snack: Snackbar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFrament =
            supportFragmentManager.findFragmentById(R.id.nav_login_fragment) as NavHostFragment
        navController = navHostFrament.findNavController()
        setupActionBarWithNavController(navController)

        binding.apply {
            snack = Snackbar.make(
                root,
                getString(R.string.text_network_not_available),
                Snackbar.LENGTH_INDEFINITE
            )
        }
        subscribeObservers()
    }

    private fun subscribeObservers() {
        collectLatestFlow2(loginViewModel.loginEventSharedFlow) { event ->
            when (event) {
                is LoginViewModel.LoginEvent.NavigateToLaunchpadActivity -> {
                        //startActivity(Intent(this@LoginActivity, LaunchpadActivity::class.java))
                    finish()
                }

                is LoginViewModel.LoginEvent.ErrorEvent -> {

                }
            }.exhaustive
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun showNetworkSnackBar(isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            if (snack.isShown) {
                this.toast(getString(R.string.text_network_connected))
            }
        } else {
            snack.show()
        }
    }
}