package com.example.easyway.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="person")
data class Person(
    @PrimaryKey(autoGenerate=true)val pid:Int?,
    @ColumnInfo(name="name") val name: String?,
    @ColumnInfo(name="isDisabled") val isDisabled:Int?,
    @ColumnInfo(name="phoneNumber") val phoneNumber:String?,
    @ColumnInfo(name="idNumber") val idNumber:String?
)
