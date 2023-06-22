package ua.roman.testtask.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import ua.roman.testtask.R
import ua.roman.testtask.core.platform.BaseActivity
import ua.roman.testtask.data.enitty.BootEventEntity
import ua.roman.testtask.databinding.ActivityMainBinding
import ua.roman.testtask.ui.adapters.BootEventAdapter

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val adapter = BootEventAdapter()

    override fun bind() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionLauncher()
        requestNotificationPermission()

        binding.bootEventRv.adapter = adapter

        viewModel.bootLiveData.observe(this, ::handleBootEvents)
    }

    private fun handleBootEvents(events: List<BootEventEntity>) {
        adapter.setEvents(events)
        binding.noBootEvents.isVisible = events.isEmpty()
    }

    private fun initPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                // ready to show notifications
            } else {
                //notification permission not granted
            }
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // ready to show notifications
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            Toast.makeText(
                this@MainActivity,
                R.string.notifications_not_allowed,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}