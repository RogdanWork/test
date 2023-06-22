package ua.roman.testtask.tools

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ua.roman.testtask.data.dao.BootEventsDao
import ua.roman.testtask.data.enitty.BootEventEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BootBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var dao: BootEventsDao

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return

        if (action == Intent.ACTION_BOOT_COMPLETED) {
            val eventTime = System.currentTimeMillis()
            onEventReceived(eventTime)
        }
    }

    private fun onEventReceived(time: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            val entity = BootEventEntity(time)
            dao.addEvent(entity)

            runWorker()
        }
    }

    private fun runWorker() {
        val workRequest = PeriodicWorkRequestBuilder<BootWorker>(WORKER_PERIODIC_TIME_MINUTES, TimeUnit.MINUTES)
            .setInputData(Data.Builder().build())
            .setConstraints(createWorkerConstraints())
            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS, TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(BootWorker.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    private fun createWorkerConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresBatteryNotLow(true)
        .setRequiresStorageNotLow(false)
        .build()

    companion object {
        private const val WORKER_PERIODIC_TIME_MINUTES = 15L
    }
}