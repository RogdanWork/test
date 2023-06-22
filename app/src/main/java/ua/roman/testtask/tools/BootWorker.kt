package ua.roman.testtask.tools

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ua.roman.testtask.R
import ua.roman.testtask.data.dao.BootEventsDao
import ua.roman.testtask.data.enitty.BootEventEntity

@HiltWorker
class BootWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dao: BootEventsDao
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val events = dao.getOrderedByTimeEvents()
        val message = createNotificationMessage(events)

        BootNotificationsTool(applicationContext).showBootNotification(message)

        return Result.success()
    }

    private fun createNotificationMessage(events: List<BootEventEntity>) = when (events.size) {
        NO_EVENTS -> applicationContext.getString(R.string.boot_worker_notification_description_no_events)
        SINGLE_EVENT -> {
            val event = events.first()
            val format = applicationContext.getString(R.string.boot_worker_notification_description_single_event)
            String.format(format, event.time)
        }

        else -> {
            val lastEvent = events.last()
            val penultimateEvent = events[events.size - PENULTIMATE_EVENT_OFFSET]
            val timeDelta = lastEvent.time - penultimateEvent.time

            val format = applicationContext.getString(R.string.boot_worker_notification_description_multi_event)
            String.format(format, timeDelta)
        }
    }

    companion object {
        const val WORK_NAME = "Boot worker"
        private const val NO_EVENTS = 0
        private const val SINGLE_EVENT = 1
        private const val PENULTIMATE_EVENT_OFFSET = 2
    }
}