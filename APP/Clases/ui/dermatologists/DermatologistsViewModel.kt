package com.example.skinscanner.ui.dermatologists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.skinscanner.database.AppDatabase
import com.example.skinscanner.model.dermatologist.DermatologistRecord
import com.example.skinscanner.model.dermatologist.DermatologistRecordRepository
import com.example.skinscanner.model.skin.SkinRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DermatologistsViewModel(application: Application) : AndroidViewModel(application) {

    private val dermatologistRecordRepository: DermatologistRecordRepository
    var dermatologistRecordsLiveData: LiveData<List<DermatologistRecord>>

    init {
        val dermatologistDAO = AppDatabase
            .getAppDatabase(application)
            .dermatologistDAO()

        dermatologistRecordRepository = DermatologistRecordRepository(dermatologistDAO)
        dermatologistRecordsLiveData = dermatologistRecordRepository.dermatologistRecords
    }


    fun insert (dermatologistRecord: DermatologistRecord) = viewModelScope.launch(Dispatchers.IO) {
        dermatologistRecordRepository.insert(dermatologistRecord)
    }

    fun insertAll(dermatologistRecords: List<DermatologistRecord>) = viewModelScope.launch(Dispatchers.IO) {
        dermatologistRecordRepository.insertAll(dermatologistRecords)
    }

    fun removeAll () = viewModelScope.launch(Dispatchers.IO) {
        dermatologistRecordRepository.removeAll()
    }
}