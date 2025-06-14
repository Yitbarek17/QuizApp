package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.DialogBiometricSetupBinding
import com.example.quizapp.utils.BiometricAuthManager
import com.example.quizapp.utils.PreferenceManager
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var biometricAuthManager: BiometricAuthManager
    private lateinit var preferenceManager: PreferenceManager
    private var isAuthenticationInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//finger
        // Initialize managers
        biometricAuthManager = BiometricAuthManager(this)
        preferenceManager = PreferenceManager(this)

        // Check if this is first launch
        if (preferenceManager.isFirstLaunch()) {
            showBiometricSetupDialog()
        } else if (preferenceManager.isAuthenticationRequired()) {
            // Show authentication if required
            showBiometricAuthentication()
        } else {
            // Show main UI
            showMainUI()
        }
    }

    override fun onResume() {
        super.onResume()
        // Check authentication when returning to app
        if (!preferenceManager.isFirstLaunch() &&
            preferenceManager.isAuthenticationRequired() &&
            !isAuthenticationInProgress) {
            showBiometricAuthentication()
        }
    }

    private fun showBiometricSetupDialog() {
        val biometricStatus = biometricAuthManager.isBiometricAvailable()

        if (biometricStatus != BiometricAuthManager.BiometricStatus.AVAILABLE) {
            // Biometric not available, skip setup
            preferenceManager.setFirstLaunch(false)
            showMainUI()
            return
        }

        val dialogBinding = DialogBiometricSetupBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        dialogBinding.btnSkip.setOnClickListener {
            preferenceManager.setFirstLaunch(false)
            preferenceManager.setBiometricEnabled(false)
            dialog.dismiss()
            showMainUI()
        }

        dialogBinding.btnEnable.setOnClickListener {
            preferenceManager.setFirstLaunch(false)
            preferenceManager.setBiometricEnabled(true)
            dialog.dismiss()
            showBiometricAuthentication()
        }

        dialog.show()
    }

    private fun showBiometricAuthentication() {
        if (isAuthenticationInProgress) return

        val biometricStatus = biometricAuthManager.isBiometricAvailable()

        when (biometricStatus) {
            BiometricAuthManager.BiometricStatus.AVAILABLE -> {
                isAuthenticationInProgress = true

                biometricAuthManager.setupBiometricPrompt(object : BiometricAuthManager.BiometricAuthCallback {
                    override fun onAuthenticationSucceeded() {
                        isAuthenticationInProgress = false
                        preferenceManager.setLastAuthTime(System.currentTimeMillis())
                        showMainUI()
                    }

                    override fun onAuthenticationFailed() {
                        isAuthenticationInProgress = false
                        Snackbar.make(
                            binding.root,
                            "Authentication failed. Please try again.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationError(errorCode: Int, errorMessage: String) {
                        isAuthenticationInProgress = false
                        when (errorCode) {
                            BiometricPrompt.ERROR_USER_CANCELED,
                            BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                                // User chose to use password or canceled
                                finish()
                            }
                            else -> {
                                Snackbar.make(
                                    binding.root,
                                    "Authentication error: $errorMessage",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                })

                biometricAuthManager.authenticate()
            }
            else -> {
                // Biometric not available, disable it and show main UI
                preferenceManager.setBiometricEnabled(false)
                showMainUI()
            }
        }
    }

    private fun showMainUI() {

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        binding.ivLogo.startAnimation(fadeIn)
        binding.tvWelcome.startAnimation(fadeIn)
        binding.tvSubtitle.startAnimation(fadeIn)
        binding.cardButtons.startAnimation(slideUp)


        binding.btnStartQuiz.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnViewHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        // Add long press on logo to toggle biometric settings
        binding.ivLogo.setOnLongClickListener {
            showBiometricSettings()
            true
        }
    }

    private fun showBiometricSettings() {
        val biometricStatus = biometricAuthManager.isBiometricAvailable()
        val isEnabled = preferenceManager.isBiometricEnabled()

        val message = when (biometricStatus) {
            BiometricAuthManager.BiometricStatus.AVAILABLE -> {
                if (isEnabled) "Biometric authentication is currently enabled."
                else "Biometric authentication is currently disabled."
            }
            BiometricAuthManager.BiometricStatus.NO_HARDWARE -> "No biometric hardware available."
            BiometricAuthManager.BiometricStatus.NONE_ENROLLED -> "No biometric credentials enrolled."
            else -> "Biometric authentication is not available."
        }

        val builder = AlertDialog.Builder(this)
            .setTitle("Biometric Settings")
            .setMessage(message)

        if (biometricStatus == BiometricAuthManager.BiometricStatus.AVAILABLE) {
            if (isEnabled) {
                builder.setPositiveButton("Disable") { _, _ ->
                    preferenceManager.setBiometricEnabled(false)
                    Snackbar.make(binding.root, "Biometric authentication disabled", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                builder.setPositiveButton("Enable") { _, _ ->
                    preferenceManager.setBiometricEnabled(true)
                    Snackbar.make(binding.root, "Biometric authentication enabled", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        builder.setNegativeButton("Cancel", null)
            .show()
    }
}