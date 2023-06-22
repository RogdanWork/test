package ua.roman.testtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.roman.testtask.data.dao.BootEventsDao
import ua.roman.testtask.data.enitty.BootEventEntity
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dao: BootEventsDao,
) : ViewModel() {
    val bootLiveData: LiveData<List<BootEventEntity>> = dao.observeBootUpdates()
}