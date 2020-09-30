package com.example.skinscanner.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skinscanner.model.dermatologist.DermatologistRecord

@Dao
interface DermatologistDAO {

    @Query("SELECT * FROM dermatologist")
    fun getAllDermatologistRecords(): LiveData<List<DermatologistRecord>>

    @Query("SELECT * FROM dermatologist WHERE id = :id")
    suspend fun getDermatologistRecord(id: Int): DermatologistRecord

    @Insert(entity = DermatologistRecord::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertDermatologistRecord(dermatologistRecord: DermatologistRecord)

    @Delete
    suspend fun removeDermatologistRecord(dermatologistRecord: DermatologistRecord)

    @Query("DELETE FROM dermatologist")
    suspend fun removeAllDermatologistRecords()
}