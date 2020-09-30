package com.example.skinscanner.ui.history

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.skinscanner.database.AppDatabase
import com.example.skinscanner.model.skin.SkinRecord
import com.example.skinscanner.model.skin.SkinRecordRepository
import com.example.skinscanner.ui.details.SkinRecordDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application): AndroidViewModel(application) {

    private val skinRecordRepository: SkinRecordRepository
    var skinRecordsLiveData: LiveData<List<SkinRecord>>

    init {
        val skinDAO = AppDatabase
            .getAppDatabase(application)
            .skinDAO()

        skinRecordRepository = SkinRecordRepository(skinDAO)
        skinRecordsLiveData = skinRecordRepository.skinRecords
    }

    fun insert (skinRecord: SkinRecord) = viewModelScope.launch(Dispatchers.IO) {
        skinRecordRepository.insert(skinRecord)
    }
}