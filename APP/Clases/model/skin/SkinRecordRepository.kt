package com.example.skinscanner.model.skin

import androidx.lifecycle.LiveData
import com.example.skinscanner.database.SkinDAO

class SkinRecordRepository(private val skinDAO: SkinDAO) {

    val skinRecords: LiveData<List<SkinRecord>> = skinDAO.getAllSkinRecords()

    suspend fun insert(skinRecord: SkinRecord) {
        skinDAO.insertSkinRecord(skinRecord)
    }

    suspend fun remove (skinRecord: SkinRecord) {
        skinDAO.removeSkinRecord(skinRecord)
    }
}