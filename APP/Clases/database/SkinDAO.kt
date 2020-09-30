package com.example.skinscanner.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skinscanner.model.skin.SkinRecord

@Dao
interface SkinDAO {

    @Query("SELECT * FROM skin ORDER BY date DESC")
    fun getAllSkinRecords(): LiveData<List<SkinRecord>>

    @Query("SELECT * FROM skin WHERE id = :id")
    suspend fun getSkinRecord(id: Int): SkinRecord

    @Insert(entity = SkinRecord::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertSkinRecord(skinRecord: SkinRecord)

    @Delete
    suspend fun removeSkinRecord(skinRecord: SkinRecord)

    @Query("DELETE FROM skin")
    suspend fun removeAllSkinRecords()

    @Update
    suspend fun update(skinRecord: SkinRecord)
}