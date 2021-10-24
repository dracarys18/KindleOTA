package com.example.kindleota.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KindleDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKindle(kindle: KindleData)

    @Query("DELETE FROM kindle_data where kindle_name=:name")
    fun deleteKindle(name: String)

    @Query("SELECT * FROM kindle_data")
    fun getallKindles(): Array<KindleData>

    @Query("SELECT kindle_name from kindle_data where sno=:uno")
    fun getName(uno: Int): LiveData<String>

    @Query("SELECT version from kindle_data where sno=:uno")
    fun getVersion(uno: Int): LiveData<String>
}
