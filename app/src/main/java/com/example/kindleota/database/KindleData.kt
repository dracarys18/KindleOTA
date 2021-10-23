package com.example.kindleota.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kindle_data")
data class KindleData(
    @PrimaryKey val sno: Int,
    @ColumnInfo(name = "kindle_name") val kindleName: String?,
    @ColumnInfo(name = "version") val version: String?
)
