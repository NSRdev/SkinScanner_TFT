package com.example.skinscanner.model.dermatologist

import androidx.lifecycle.LiveData
import com.example.skinscanner.database.DermatologistDAO
import com.example.skinscanner.model.skin.SkinRecord

class DermatologistRecordRepository(private val dermatologistDAO: DermatologistDAO) {

    val dermatologistRecords: LiveData<List<DermatologistRecord>> = dermatologistDAO.getAllDermatologistRecords()

    suspend fun insert(dermatologistRecord: DermatologistRecord) {
        dermatologistDAO.insertDermatologistRecord(dermatologistRecord)
    }

    suspend fun insertAll(list: List<DermatologistRecord>) {
        for (dermatologistRecord in list) {
            insert(dermatologistRecord)
        }
    }

    suspend fun removeAll() {
        dermatologistDAO.removeAllDermatologistRecords()
    }

}